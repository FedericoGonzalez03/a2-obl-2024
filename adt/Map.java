package adt;

public interface Map<K, V> {
    public void set(K key, V value);

    public void remove(K key);

    public boolean has(K key);

    public V get(K key);
    
    int size();
    
    boolean isEmpty();

    public Iterable<K> keys();

    public Iterable<V> values();
}

