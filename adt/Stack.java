package adt;

public interface Stack<T> {
    
    public void push(T element);

    public T pop();

    public T peek();

    public boolean isEmpty();

    public int size();

    public Stack<T> getReverse();
}
