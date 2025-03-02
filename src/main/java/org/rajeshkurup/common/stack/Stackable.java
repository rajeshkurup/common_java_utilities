package org.rajeshkurup.common.stack;


import java.util.Optional;
import org.rajeshkurup.common.list.Listable;
import org.rajeshkurup.common.model.Copyable;

public interface Stackable<T extends Listable<T> & Comparable<T> & Copyable<T>> {

    public void push(T node);

    public Optional<T> pop();

    public Optional<T> peek();

    public Optional<T> maxPop();

    public Optional<T> maxPeek();

    public Optional<T> minPop();

    public Optional<T> minPeek();

    public int size();

}
