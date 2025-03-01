package org.rajeshkurup.common.stack;


public interface Stackable<T> {

    public void push(T node);

    public T pop();

    public T peek();

    public T maxPop();

    public T maxPeek();

    public T minPop();

    public T minPeek();

}
