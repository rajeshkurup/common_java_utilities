package org.rajeshkurup.common.stack;


import java.util.Optional;
import org.rajeshkurup.common.list.Listable;
import org.rajeshkurup.common.model.Copyable;

public interface Stackable<T extends Listable<T> & Comparable<T> & Copyable<T>> {

    boolean push(T node);

    Optional<T> pop();

    Optional<T> peek();

    Optional<T> maxPop();

    Optional<T> maxPeek();

    Optional<T> minPop();

    Optional<T> minPeek();

    int size();

}
