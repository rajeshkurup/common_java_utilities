package org.rajeshkurup.common.stack;

import java.util.Optional;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import lombok.NonNull;
import org.rajeshkurup.common.lambda.Processor;
import org.rajeshkurup.common.list.Listable;
import org.rajeshkurup.common.model.Copyable;

public class StackEx<T extends Listable<T> & Comparable<T> & Copyable<T>> implements Stackable<T> {

    private final Stack<T> nodeStack;
    private final SortedSet<T> nodeSet;
    private final Processor.ProcessorInterface<T> copier;
    private final Processor.ProcessorInterface<T> remover;

    public StackEx() {
        this.nodeStack = new Stack<>();
        this.nodeSet = new TreeSet<>();

        this.copier = (@NonNull final T obj) -> {
            T clone = obj.deepCopy();
            clone.setPrev(null);
            clone.setNext(null);
            return Optional.of(clone);
        };

        this.remover = (@NonNull final T node) -> {
            this.nodeStack.remove(node);
            node.setNext(null);
            node.setPrev(null);
            return Optional.of(node);
        };
    }

    @Override
    public boolean push(@NonNull final T node) {
        T newNode = this.copier.process(node).orElseThrow();
        if(this.nodeSet.add(newNode)) {
            this.nodeStack.add(newNode);
            return true;
        }
        return false;
    }

    @Override
    public Optional<T> pop() {
        if(this.nodeStack.isEmpty()) {
            return Optional.empty();
        }
        return Processor.process(this.nodeStack.pop(), (@NonNull final T node) -> {
            this.nodeSet.remove(node);
            node.setNext(null);
            node.setPrev(null);
            return Optional.of(node);
        });
    }

    @Override
    public Optional<T> peek() {
        return Optional.ofNullable(this.nodeStack.isEmpty() ? null : this.copier.process(this.nodeStack.peek()).orElseThrow());
    }

    @Override
    public Optional<T> maxPop() {
        if(this.nodeSet.isEmpty()) {
            return Optional.empty();
        }
        return this.remover.process(this.nodeSet.removeLast());
    }

    @Override
    public Optional<T> maxPeek() {
        return Optional.ofNullable(this.nodeSet.isEmpty() ? null : this.copier.process(this.nodeSet.last()).orElseThrow());
    }

    @Override
    public Optional<T> minPop() {
        if(this.nodeSet.isEmpty()) {
            return Optional.empty();
        }
        return this.remover.process(this.nodeSet.removeFirst());
    }

    @Override
    public Optional<T> minPeek() {
        return Optional.ofNullable(this.nodeSet.isEmpty() ? null : this.copier.process(this.nodeSet.first()).orElseThrow());
    }

    @Override
    public int size() {
        return this.nodeStack.size();
    }

}
