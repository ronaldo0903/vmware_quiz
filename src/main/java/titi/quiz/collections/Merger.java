package titi.quiz.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class Merger<T extends Comparable> {
    private Comparator<T> _comparator;
    public Merger(Comparator comparator) {
        this._comparator = comparator;
    }
    public Collection<T> merge(Collection<T> c1, Collection<T> c2) {
        Iterator<T> iter1 = c1.iterator();
        Iterator<T> iter2 = c2.iterator();
        Collection<T> resultCollection = new ArrayList<T>();
        T t1 = iter1.hasNext()? iter1.next() : null;
        T t2 = iter2.hasNext()? iter2.next() : null;
        while(t1 != null && t2!= null) {
            if(((_comparator!=null)?_comparator.compare(t1, t2) : t1.compareTo(t2) ) <= 0)  {
                resultCollection.add(t1);
                t1 = iter1.hasNext()? iter1.next() : null;

            }
            else {
                resultCollection.add(t2);
                t2 = iter2.hasNext()? iter2.next() : null;
            }
        }
        while(t1 != null) {
            resultCollection.add(t1);
            t1 = iter1.hasNext()? iter1.next() : null;
        }
        while(t2 != null) {
            resultCollection.add(t2);
            t2 = iter2.hasNext()? iter2.next() : null;
        }
        return resultCollection;
    }
}
