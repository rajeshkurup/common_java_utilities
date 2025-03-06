package org.rajeshkurup.common.stack;

import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.NonNull;
import org.rajeshkurup.common.lambda.CopyFunction;
import org.rajeshkurup.common.list.Listable;
import org.rajeshkurup.common.model.Copyable;

public class MinMaxStack<T extends Listable<T> & Comparable<T> & Copyable<T>> implements Stackable<T> {

    private T head;
    private final SortedSet<T> nodeSet;
    private final CopyFunction<T> copyFunction;

    public MinMaxStack() {
        this.nodeSet = new TreeSet<>();

        this.copyFunction = (final T node) -> {
            T newNode = node.deepCopy();
            newNode.setPrev(null);
            newNode.setNext(null);
            return newNode;
        };
    }

    @Override
    public void push(@NonNull final T node) {
        T newNode = this.copyFunction.deepCopy(node);
        if(this.nodeSet.add(newNode)) {
            newNode.setNext(this.head);
            if(Objects.nonNull(this.head)) {
                this.head.setPrev(newNode);
            }
            this.head = newNode;
            this.nodeSet.add(newNode);
        }
    }

    @Override
    public Optional<T> pop() {
        T result = null;
        if(Objects.nonNull(this.head)) {
            result = this.head;
            detachNode(result);
            this.nodeSet.remove(result);
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
            }
        } else {
            this.head = node.getNext();
            if(Objects.nonNull(this.head)) {
                this.head.setPrev(null);
            }
        }
        node.setPrev(null);
        node.setNext(null);
    }

}
