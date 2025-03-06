package org.rajeshkurup.common.queue;

import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.NonNull;
import org.rajeshkurup.common.lambda.CopyFunction;
import org.rajeshkurup.common.list.Listable;
import org.rajeshkurup.common.model.Copyable;

public class MinMaxQueue<T extends Listable<T> & Comparable<T> & Copyable<T>> implements Queueable<T> {

    private T head;
    private T tail;
    private final SortedSet<T> nodeSet;
    private final CopyFunction<T> copyFunction;

    public MinMaxQueue() {
        this.nodeSet = new TreeSet<>();

        this.copyFunction = (final T node) -> {
            T newNode = node.deepCopy();
            newNode.setPrev(null);
            newNode.setNext(null);
            return newNode;
        };
    }

    @Override
    public void push(@NonNull final  T node) {
        T newNode = this.copyFunction.deepCopy(node);
        if(this.nodeSet.add(newNode)) {
            if(Objects.nonNull(this.tail)) {
                newNode.setPrev(this.tail);
                this.tail.setNext(newNode);
            } else {
                this.head = newNode;
            }
            this.tail = newNode;
        }
    }

    @Override
    public Optional<T> pop() {
        T result = null;
        if(Objects.nonNull(this.head)) {
            result = this.head;
            this.nodeSet.remove(result);
            detachNode(result);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<T> peek() {
        return Optional.ofNullable(Objects.isNull(this.head) ? null : this.copyFunction.deepCopy(this.head));
    }

    @Override
    public Optional<T> maxPop() {
        T result = null;
        if(!this.nodeSet.isEmpty()) {
            result = this.nodeSet.removeLast();
            detachNode(result);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<T> maxPeek() {
        return Optional.ofNullable(this.nodeSet.isEmpty() ? null : this.copyFunction.deepCopy(this.nodeSet.last()));
    }

    @Override
    public Optional<T> minPop() {
        T result = null;
        if(!this.nodeSet.isEmpty()) {
            result = this.nodeSet.removeFirst();
            detachNode(result);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<T> minPeek() {
        return Optional.ofNullable(this.nodeSet.isEmpty() ? null : this.copyFunction.deepCopy(this.nodeSet.first()));
    }

    @Override
    public int size() {
        return this.nodeSet.size();
    }

    private void detachNode(final T node) {
        if(node.hasPrev()) {
            node.getPrev().setNext(node.getNext());
            if(node.hasNext()) {
                node.getNext().setPrev(node.getPrev());
            } else {
                this.tail = node.getPrev();
            }
        } else {
            this.head = node.getNext();
            if(Objects.nonNull(this.head)) {
                this.head.setPrev(null);
            } else {
                this.tail = null;
            }
        }
        node.setPrev(null);
        node.setNext(null);
    }

}
