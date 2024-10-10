package adt;

public interface List<T> extends Iterable<T> {

    public void add(T element);

    public void add(int index, T element);

    public T get(int index);
    
    public void remove(int index);
    
    public boolean isEmpty();

    public int size();
        
}
