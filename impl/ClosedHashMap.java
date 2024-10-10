package impl;

import adt.List;
import adt.Map;
import util.DoubleHashFunction;
import util.HashFunction;
import adt.Iterable;

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
    
    private Object[] map;
    private int size = 0;
    private int capacity;
    private HashFunction<K> hash;
    private CollisionSolvingMethod csm;
    private final float maxLoadFactor = 0.7f;

    public ClosedHashMap(int capacity, HashFunction<K> hash, CollisionSolvingMethod collisionSolvingMethod) {
        this.map = new Object[capacity];
        this.capacity = capacity;
        this.hash = hash;
        this.csm = collisionSolvingMethod;
    }

    private void resize() {       
        Object[] oldMap = this.map;
        this.capacity = this.capacity * 2;
        this.map = new Object[this.capacity];
        this.size = 0;
        for (int i = 0; i < oldMap.length; i++) {
            if (oldMap[i] != null) {
                KeyValue kv = (KeyValue) oldMap[i];
                if (!kv.wasDeleted) {
                    this.set(kv.key, kv.value);
                }
            }
        }
    }

    private void lSet(K key, V value) {

        boolean setted = false;
        for (int i = 0; !setted; i++) {
            int index = Math.abs(this.hash.hash(key) + i);

            if (map[index] == null || ((KeyValue) map[index]).wasDeleted) {
                map[index] = new KeyValue(key, value);
                setted = true;
                this.size++;                
            } else if (((KeyValue) map[index]).key.equals(key)) {
                ((KeyValue) map[index]).value = value;
                setted = true;
            }
        }
    }

    private void dhSet(K key, V value) {

        DoubleHashFunction<K> dh = (DoubleHashFunction<K>) this.hash;
        boolean setted = false;
        for (int i = 0; !setted; i++) {
            int index = Math.abs((dh.hash(key) + i * dh.secondHash(key)) % this.capacity);

            if (map[index] == null || ((KeyValue) map[index]).wasDeleted) {
                map[index] = new KeyValue(key, value);
                setted = true;
                this.size++;                
            } else if (((KeyValue) map[index]).key.equals(key)) {
                ((KeyValue) map[index]).value = value;
                setted = true;
            }
        }
    }

    @Override
    public void set(K key, V value) {

        if ((float) this.size / this.capacity > this.maxLoadFactor) {
            this.resize();
        }

        switch(this.csm){
            default:
            case DOUBLE_HASH:
                dhSet(key, value);
            break;
            case LINEAR:
                lSet(key, value);
            break;
        }
    }

    private V lGet(K key) {
        int index = Math.abs(this.hash.hash(key) % this.capacity);
        for (int i = 1; i < this.capacity && map[index] != null; i++) {
            if (((KeyValue) map[index]).key.equals(key) && !((KeyValue) map[index]).wasDeleted) return ((KeyValue) map[index]).value;
            index = Math.abs((this.hash.hash(key) + i) % this.capacity);
        }
        return null;
    }

    private V dhGet(K key) {
        DoubleHashFunction<K> dh = (DoubleHashFunction<K>) this.hash;
        int index = Math.abs(dh.hash(key) % this.capacity);
        for (int i = 1; i < this.capacity && map[index] != null; i++) {
            if (((KeyValue) map[index]).key.equals(key) && !((KeyValue) map[index]).wasDeleted) return ((KeyValue) map[index]).value;
            index = Math.abs((dh.hash(key) + i * dh.secondHash(key)) % this.capacity);
        }
        return null;
    }

    @Override
    public V get(K key) {
        switch(this.csm){
            default:
            case DOUBLE_HASH:
                return dhGet(key);
            case LINEAR:
                return lGet(key);
        }
    }

    private void lRemove(K key) {
        int index = Math.abs(this.hash.hash(key) % this.capacity);
        for (int i = 1; i < this.capacity && map[index] != null; i++) {
            if (((KeyValue) map[index]).key.equals(key) && !((KeyValue) map[index]).wasDeleted) {
                ((KeyValue) map[index]).wasDeleted = true;
                this.size--;
            }
            index = Math.abs((this.hash.hash(key) + i) % this.capacity);
        }
    }

    private void dhRemove(K key) {
        DoubleHashFunction<K> dh = (DoubleHashFunction<K>) this.hash;
        int index = Math.abs(dh.hash(key) % this.capacity);
        for (int i = 1; i < this.capacity && map[index] != null; i++) {
            if (((KeyValue) map[index]).key.equals(key) && !((KeyValue) map[index]).wasDeleted) {
                ((KeyValue) map[index]).wasDeleted = true;
                this.size--;
            }
            index = Math.abs((dh.hash(key) + i * dh.secondHash(key)) % this.capacity);
        }
    }

    @Override
    public void remove(K key) {
        switch(this.csm){
            default:
            case DOUBLE_HASH:
                dhRemove(key);
            break;
            case LINEAR:
                lRemove(key);
            break;
        }  

    }

    private boolean lHas(K key) {
        int index = Math.abs(this.hash.hash(key) % this.capacity);
        for (int i = 1; i < this.capacity && map[index] != null; i++) {
            if (((KeyValue) map[index]).key.equals(key) && !((KeyValue) map[index]).wasDeleted) return true;
            index = Math.abs((this.hash.hash(key) + i) % this.capacity);
        }
        return false;
    }

    private boolean dhHas(K key) {
        DoubleHashFunction<K> dh = (DoubleHashFunction<K>) this.hash;
        int index = Math.abs(dh.hash(key) % this.capacity);
        for (int i = 1; i < this.capacity && map[index] != null; i++) {
            if (((KeyValue) map[index]).key.equals(key) && !((KeyValue) map[index]).wasDeleted) return true;
            index = Math.abs((dh.hash(key) + i * dh.secondHash(key)) % this.capacity);
        }
        return false;
    }

    @Override
    public boolean has(K key) {
        switch(this.csm){
            default:
            case DOUBLE_HASH:
                return dhHas(key);
            case LINEAR:
                return lHas(key);
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
        List<K> list = new LinkedList<>();
        for (int i = 0; i < this.map.length; i++) {
            if(this.map[i] != null) list.add(((KeyValue) this.map[i]).key);
        }
        return list;
    }

    @Override
    public Iterable<V> values() {
        List<V> list = new LinkedList<>();
        for (int i = 0; i < this.map.length; i++) {
            if(this.map[i] != null) list.add(((KeyValue) this.map[i]).value);
        }
        return list;
    }

}
