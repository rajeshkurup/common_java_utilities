package org.rajeshkurup.common.lambda;

public interface CopyFunction<T> {

    T deepCopy(T node);

}
