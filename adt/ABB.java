package adt;

public interface ABB<T extends Comparable<T>> {

    abstract void insert(T element);

    abstract void remove(T element);

    abstract boolean contains(T element);

    abstract T get(T element);

    abstract T max();

    abstract T min();

    abstract int count();

    abstract void printInOrder();
}