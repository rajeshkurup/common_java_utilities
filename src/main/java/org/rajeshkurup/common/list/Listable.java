package org.rajeshkurup.common.list;

public interface Listable<T> {

    T getNext();

    void setNext(T next);

    T getPrev();

    void setPrev(T prev);

    boolean hasNext();

    boolean hasPrev();

}
