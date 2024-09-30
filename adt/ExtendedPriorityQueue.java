package adt;

public interface ExtendedPriorityQueue<T, P extends Comparable<P>> extends PriorityQueue<T, P> {

    public void remove(T element);

    public void updatePriority(T element, P newPriority);

    public Node<T,P> get(T element);

}
