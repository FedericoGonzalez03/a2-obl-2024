package impl;

import java.util.Enumeration;

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
        DoubleHashFunction<K> dh = (DoubleHashFunction<K>) this.hash;
        boolean setted = false;
        for (int i = 0; !setted; i++) {
            int index = (dh.hash(key) + i * dh.secondHash(key)) % this.capacity;

            if (map[index] == null) {
                map[index] = new KeyValue(key, value);
                setted = true;
                this.size++;                
            } else if (map[index].wasDeleted) {
                map[index] = new KeyValue(key, value);
                setted = true;
                this.size++;
            } else if (map[index].key.equals(key)) {
                map[index].value = value;
                setted = true;
                this.size++;
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

}
