package adt;

public interface Heap<T extends Comparable<T>> {

    public void insert(T element);

    public T pop();

    public T peek();

    public boolean isEmpty();

    public boolean isFull();

}
