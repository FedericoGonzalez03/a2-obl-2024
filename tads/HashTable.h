#ifndef HASHTABLE_H
#define HASHTABLE_H

template <class K, class V>
class HashTable {
public:
    // pre:
    // post: inserts the key-value pair into the hash table
    void insert(const K key, const V value) = 0;

    // pre:
    // post: removes the key-value pair with the given key from the hash table
    void remove(const K key) = 0;

    // pre:
    // post: retrieves the value associated with the given key
    V get(const K key) = 0;

    // pre:
    // post: checks if the hash table contains the given key
    bool contains(const K key) = 0;

    // pre:
    // post: returns the number of key-value pairs in the hash table
    int size() = 0;
};

#endif // HASHTABLE_H