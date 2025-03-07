package org.rajeshkurup.common.queue;

import java.util.*;
import lombok.NonNull;
import org.rajeshkurup.common.lambda.Processor;
import org.rajeshkurup.common.list.Listable;
import org.rajeshkurup.common.model.Copyable;

public class QueueEx<T extends Listable<T> & Copyable<T> & Comparable<T> & Cloneable> implements Queueable<T> {

    private final Queue<T> nodeQueue;
    private final SortedSet<T> nodeSet;
    private final Processor.ProcessorInterface<T> copier;
    private final Processor.ProcessorInterface<T> remover;

    public QueueEx() {
        this.nodeQueue = new LinkedList<>();
        this.nodeSet = new TreeSet<>();

        this.copier = (@NonNull final T node) -> {
            T newNode = node.deepCopy();
            newNode.setNext(null);
            newNode.setPrev(null);
            return Optional.of(newNode);
        };

        this.remover = (@NonNull final T node) -> {
            this.nodeQueue.remove(node);
            node.setNext(null);
            node.setPrev(null);
            return Optional.of(node);
        };
    }

    @Override
    public boolean push(@NonNull final T node) {
        T newNode = this.copier.process(node).orElseThrow();
        if(this.nodeSet.add(newNode)) {
            this.nodeQueue.add(newNode);
            return true;
        }
        return false;
    }

    @Override
    public Optional<T> pop() {
        if (this.nodeQueue.isEmpty()) {
            return Optional.empty();
        }
        return Processor.process(this.nodeQueue.remove(), (@NonNull final T node) -> {
            this.nodeSet.remove(node);
            node.setNext(null);
            node.setPrev(null);
            return Optional.of(node);
        });
    }

    @Override
    public Optional<T> peek() {
        return Optional.ofNullable(this.nodeQueue.isEmpty() ? null : this.copier.process(this.nodeQueue.peek()).orElseThrow());
    }

    @Override
    public Optional<T> maxPop() {
        if (this.nodeSet.isEmpty()) {
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
        if (this.nodeSet.isEmpty()) {
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
        return this.nodeQueue.size();
    }

}
