#include <cassert>
#include <string>
#include <iostream>
#include <limits>

#ifndef HASHTABLE_H
#define HASHTABLE_H

using namespace std;

template <class K, class V>
class HashTable {
private:
    int tableSize;

    struct Nodo {
        K key;
        V value;
        Nodo* next;

        Nodo(const K& key, const V& value) : key(key), value(value), next(nullptr) {}
    };

    Nodo** table;
    int (*hashFunction)(K);

public:
    HashTable(int __size, int (*__hashFunction)(K key)) : tableSize(__size), hashFunction(__hashFunction) {
        table = new Nodo*[tableSize];
        for (int i = 0; i < tableSize; ++i) {
            table[i] = nullptr;
        }
    }

    ~HashTable() {
        for (int i = 0; i < tableSize; ++i) {
            Nodo* current = table[i];
            while (current != nullptr) {
                Nodo* next = current->next;
                delete current;
                current = next;
            }
        }
        delete[] table;
    }

    void insert(const K key, const V value) {
        unsigned long hashValue = hashFunction(key);
        int index = hashValue % tableSize;

        Nodo* newNode = new Nodo(key, value);
        if (table[index] == nullptr) {
            table[index] = newNode;
        } else {
            newNode->next = table[index];
            table[index] = newNode;
        }
    }

    void remove(const K key) {
        unsigned long hashValue = hashFunction(key);
        int index = hashValue % tableSize;

        Nodo* current = table[index];
        Nodo* prev = nullptr;
        while (current != nullptr && current->key != key) {
            prev = current;
            current = current->next;
        }

        if (current == nullptr) return; // Key not found

        if (prev == nullptr) {
            table[index] = current->next; // Remove the first node in the chain
        } else {
            prev->next = current->next; // Remove from middle or end of the chain
        }

        delete current;
    }

    V& get(const K key) {
        unsigned long hashValue = hashFunction(key);
        int index = hashValue % tableSize;

        Nodo* current = table[index];
        while (current != nullptr) {
            if (current->key == key) {
                return current->value;
            }
            current = current->next;
        }
        throw out_of_range("No se encontró");
    }

    bool contains(const K key) {
        unsigned long hashValue = hashFunction(key);
        int index = hashValue % tableSize;

        Nodo* current = table[index];
        while (current != nullptr) {
            if (current->key == key) {
                return true;
            }
            current = current->next;
        }

        return false;
    }

    int getSize() const {
        int count = 0;
        for (int i = 0; i < tableSize; ++i) {
            Nodo* current = table[i];
            while (current != nullptr) {
                ++count;
                current = current->next;
            }
        }
        return count;
    }
};

#endif