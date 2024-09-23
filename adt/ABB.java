package adt;

public interface ABB<T extends Comparable<T>> {

    abstract void insert(T data);

    abstract void remove(T data);

    abstract boolean contains(T data);

    abstract T get(T data);

    abstract T max();

    abstract T min();

    abstract int count();

    abstract void printInOrder();
}