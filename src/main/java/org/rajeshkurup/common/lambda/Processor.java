package org.rajeshkurup.common.lambda;

import java.util.Optional;
import lombok.NonNull;

public class Processor {

    @FunctionalInterface
    public interface ProcessorInterface<T> {
        Optional<T> process(T proc);
    }

    public static <T> Optional<T> process(@NonNull final T proc, @NonNull final ProcessorInterface<T> processor) {
        return processor.process(proc);
    }

}
