package adt;

public interface PriorityQueue<T, P extends Comparable<P>> {

    public interface Node<T, P> extends Comparable<Node<T, P>> {
        
        public T getData();
        
        public P getPriority();
    }

    public void insert(T data, P priority);

    public T pop();

    public T peek();

    public boolean isEmpty();

    public boolean isFull();

}
