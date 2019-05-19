package titi.quiz.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import titi.quiz.concurrency.ConcurrentQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ConcurrentQueueTest {
    ConcurrentQueue<Integer> queue = new ConcurrentQueue<>(100);
    @Test
    public void testUsingForkJoinPool() {
        List<Integer> producedElements = new ArrayList<>();
        List<Integer> consumedElements = new ArrayList<>();
        IntStream.rangeClosed(0, 100).parallel().forEach(
                i -> {
                    Integer e;
                    if (i % 2 == 0) {
                        if(queue.add(i)) producedElements.add(i);
                    } else {
                        if((e = queue.poll()) != null) consumedElements.add(e);
                    }
                }
        );
        Assertions.assertEquals(producedElements.size(), consumedElements.size() + queue.size());
    }
}
