package adt;

public interface Queue<T> {

    public void insert(T data);

    public T pop();

    public T peek();

    public boolean isEmpty();

    public int size();

}
