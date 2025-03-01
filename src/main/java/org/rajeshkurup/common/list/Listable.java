package org.rajeshkurup.common.list;

public interface Listable<T> {

    public T getNext();

    public void setNext(T next);

    public T getPrev();

    public void setPrev(T prev);

    public boolean hasNext();

    public boolean hasPrev();

}
