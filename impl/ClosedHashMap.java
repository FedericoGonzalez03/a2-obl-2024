package impl;

import adt.Map;

public class ClosedHashMap<K,V> implements Map<K,V> {

    private class KeyValue {
        K key;
        V value;
        boolean wasDeleted = false;
        public KeyValue(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    KeyValue[] map;
    int size = 0;
    int capacitiy;
    HashFunction hash;

    public ClosedHashMap(int capacity, HashFunction hash) {
        this.map = new KeyValue[capacity];
        this.capacity = capacity;
        this.hash = hash;
    }

    public void set(K key, V value) {
        
    }

    public void remove(K key);

    public boolean has(K key);

    public V get(K key);
    
    int size();
    
    boolean isEmpty();

    public Iterable<K> keys();

    public Iterable<V> values();


}
