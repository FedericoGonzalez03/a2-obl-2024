package impl;

import adt.Map;
import util.DoubleHashFunction;
import util.HashFunction;

@SuppressWarnings("unchecked")
public class ClosedHashMap<K,V> implements Map<K,V> {

    public enum CollisionSolvingMethod {
        LINEAR,
        QUADRATIC,
        DOUBLE_HASH
    }

    private class KeyValue {
        public K key;
        public V value;
        boolean wasDeleted = false;
        public KeyValue(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private KeyValue[] map;
    private int size = 0;
    private int capacity;
    private HashFunction<K> hash;
    private CollisionSolvingMethod csm;

    public ClosedHashMap(int capacity, HashFunction<K> hash, CollisionSolvingMethod collisionSolvingMethod) {
        this.map = (KeyValue[]) new Object[capacity]; // https://stackoverflow.com/questions/2927391/whats-the-reason-i-cant-create-generic-array-types-in-java  ->  Answer by Peter Lawrey
        this.capacity = capacity;
        this.hash = hash;
        this.csm = collisionSolvingMethod;
    }

    private void dhSet(K key, V value) {

        // implementar resize

        DoubleHashFunction<K> dh = (DoubleHashFunction<K>) this.hash;
        boolean setted = false;
        for (int i = 0; !setted; i++) {
            int index = (dh.hash(key) + i * dh.secondHash(key)) % this.capacity;

            if (map[index] == null || map[index].wasDeleted) {
                map[index] = new KeyValue(key, value);
                setted = true;
                this.size++;                
            } else if (map[index].key.equals(key)) {
                map[index].value = value;
                setted = true;
            }
        }
    }

    @Override
    public void set(K key, V value) {
        switch(this.csm){
            default:
            case DOUBLE_HASH:
                dhSet(key, value);
            break;
        }
    }

    private V dhGet(K key) {
        DoubleHashFunction<K> dh = (DoubleHashFunction<K>) this.hash;
        int index = dh.hash(key) % this.capacity;
        for (int i = 1; i < this.capacity && map[index] != null; i++) {
            if (map[index].key.equals(key) && !map[index].wasDeleted) return map[index].value;
            index = (dh.hash(key) + i * dh.secondHash(key)) % this.capacity;
        }
        return null;
    }

    @Override
    public V get(K key) {
        switch(this.csm){
            default:
            case DOUBLE_HASH:
                return dhGet(key);
        }
    }

    private void dhRemove(K key) {
        DoubleHashFunction<K> dh = (DoubleHashFunction<K>) this.hash;
        int index = dh.hash(key) % this.capacity;
        for (int i = 1; i < this.capacity && map[index] != null; i++) {
            if (map[index].key.equals(key) && !map[index].wasDeleted) {
                map[index].wasDeleted = true;
                this.size--;
            }
            index = (dh.hash(key) + i * dh.secondHash(key)) % this.capacity;
        }
    }

    @Override
    public void remove(K key) {
        switch(this.csm){
            default:
            case DOUBLE_HASH:
                dhRemove(key);
            break;
        }  

    }

    private boolean dhHas(K key) {
        DoubleHashFunction<K> dh = (DoubleHashFunction<K>) this.hash;
        int index = dh.hash(key) % this.capacity;
        for (int i = 1; i < this.capacity && map[index] != null; i++) {
            if (map[index].key.equals(key) && !map[index].wasDeleted) return true;
            index = (dh.hash(key) + i * dh.secondHash(key)) % this.capacity;
        }
        return false;
    }

    @Override
    public boolean has(K key) {
        switch(this.csm){
            default:
            case DOUBLE_HASH:
                return dhHas(key);
        }        
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterable<K> keys() {
        throw new UnsupportedOperationException("Unimplemented method 'keys'");
    }

    @Override
    public Iterable<V> values() {
        throw new UnsupportedOperationException("Unimplemented method 'values'");
    }

}
