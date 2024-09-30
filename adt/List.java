package adt;

public interface List<T extends Comparable<T>> extends Iterable<T> {

    public void add(T element);

    public void add(int index, T element);

    public T get(int index);
    
    public boolean isEmpty();

    public int size();
        
}
