package titi.quiz.concurrency;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class ConcurrentQueue<T> {
    private AtomicReferenceArray<T> atomicReferenceArray;

    private static final int MAXIMUM_CAPACITY = 1 << 30;

    private final T EMPTY = null;

    volatile Integer head,tail;

    AtomicInteger count = new AtomicInteger(0);

    private static final int powerOfTwoSize(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public ConcurrentQueue(int size){
        atomicReferenceArray = new AtomicReferenceArray(new Object[powerOfTwoSize(size + 1)]);
        head = Integer.valueOf(0);
        tail = Integer.valueOf(0);
    }

    public boolean add(T element){
        int index = (tail + 1) & (atomicReferenceArray.length() - 1);
        if(index == (head & (atomicReferenceArray.length() -1))){
            System.out.println("The queue is full, could not enqueue "+ element);
            return false;
        }

        //TODO: A possible race condition here: how to make two CAS operations act like one CAS?
        while(!TAIL.compareAndSet(this, tail, tail + 1) || !atomicReferenceArray.compareAndSet(index, EMPTY, element)){
            return add(element);
        }
        count.incrementAndGet();
        System.out.println(element + " is added to queue in thread:!" + Thread.currentThread().getName());
        return true;
    }

    public T poll(){
        if(head == tail){
            System.out.println("Queue is emtpy when thread:" + Thread.currentThread().getName() + " tries to poll!");
            return null;
        }
        int index = (head + 1) % atomicReferenceArray.length();
        T ele = (T) atomicReferenceArray.get(index);
        if(ele == null){
            return poll();
        }
        while(!HEAD.compareAndSet(this, head, head + 1) || !atomicReferenceArray.compareAndSet(index,ele, EMPTY)){
            return poll();
        }
        count.decrementAndGet();
        System.out.println(ele + " is dequed by thread: " + Thread.currentThread().getName() + "!");
        return (T) ele;
    }

    public void print(){
        StringBuffer buffer = new StringBuffer("[");
        for(int i = 0; i < atomicReferenceArray.length() ; i++){
            if(i == head || atomicReferenceArray.get(i) == null){
                continue;
            }
            buffer.append(atomicReferenceArray.get(i) + ",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append("]");
        System.out.println("Elements in the queue:"    +buffer.toString());

    }

    public int size() {
        return count.get();
    }

    // VarHandle mechanics
    private static final VarHandle HEAD;
    private static final VarHandle TAIL;
    static {
        try {
            MethodHandles.Lookup l = MethodHandles.lookup();
            HEAD = l.findVarHandle(ConcurrentQueue.class, "head",
                    Integer.class);
            TAIL = l.findVarHandle(ConcurrentQueue.class, "tail",
                    Integer.class);
        } catch (ReflectiveOperationException e) {
            throw new Error(e);
        }
    }
}
