package adt;

public interface ABB<T extends Comparable<T>> {

    public void insert(T element);

    public void remove(T element);

    public boolean contains(T element);

    public T get(T element);

    public T max();

    public T min();

    public int count();

    public void printInOrder();
}