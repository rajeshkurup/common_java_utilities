package org.rajeshkurup.common.stack;

import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.NonNull;
import org.rajeshkurup.common.list.Listable;
import org.rajeshkurup.common.model.Copyable;

public class MinMaxStack<T extends Listable<T> & Comparable<T> & Copyable<T>> implements Stackable<T> {

    private T nodeList;
    private final SortedSet<T> nodeSet;

    public MinMaxStack() {
        this.nodeSet = new TreeSet<>();
    }

    @Override
    public void push(@NonNull final T node) {
        T newNode = node.deepCopy();
        newNode.setPrev(null);
        newNode.setNext(this.nodeList);
        if(Objects.nonNull(this.nodeList)) {
            this.nodeList.setPrev(newNode);
        }
        this.nodeList = newNode;
        this.nodeSet.add(newNode);
    }

    @Override
    public Optional<T> pop() {
        T result = null;
        if(Objects.nonNull(this.nodeList)) {
            result = this.nodeList;
            this.nodeList = result.getNext();
            if (Objects.nonNull(this.nodeList)) {
                this.nodeList.setPrev(null);
            }
            this.nodeSet.remove(result);
            result.setNext(null);
            result.setPrev(null);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<T> peek() {
        T result = null;
        if(Objects.nonNull(this.nodeList)) {
            result = this.nodeList.deepCopy();
            result.setNext(null);
            result.setPrev(null);

        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<T> maxPop() {
        T result = null;
        if(!this.nodeSet.isEmpty()) {
            result = this.nodeSet.removeLast();
            if(result.hasPrev()) {
                result.getPrev().setNext(result.getNext());
                if(result.hasNext()) {
                    result.getNext().setPrev(result.getPrev());
                }
            } else {
                this.nodeList = result.getNext();
                if(Objects.nonNull(this.nodeList)) {
                    this.nodeList.setPrev(null);
                }
            }
            result.setPrev(null);
            result.setNext(null);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<T> maxPeek() {
        T result = null;
        if(!this.nodeSet.isEmpty()) {
            result = this.nodeSet.last().deepCopy();
            result.setPrev(null);
            result.setNext(null);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<T> minPop() {
        T result = null;
        if(!this.nodeSet.isEmpty()) {
            result = this.nodeSet.removeFirst();
            if(result.hasPrev()) {
                result.getPrev().setNext(result.getNext());
                if(result.hasNext()) {
                    result.getNext().setPrev(result.getPrev());
                }
            } else {
                this.nodeList = result.getNext();
                if(Objects.nonNull(this.nodeList)) {
                    this.nodeList.setPrev(null);
                }
            }
            result.setPrev(null);
            result.setNext(null);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<T> minPeek() {
        T result = null;
        if(!this.nodeSet.isEmpty()) {
            result = this.nodeSet.first().deepCopy();
            result.setPrev(null);
            result.setNext(null);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public int size() {
        return this.nodeSet.size();
    }

}
