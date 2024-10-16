package impl;

import adt.Heap;

@SuppressWarnings("unchecked")
public class MinHeap<T extends Comparable<T>> implements Heap<T> {

    private Object[] heapTable;
    private int capacity;
    private int size = 0;

    private int parent(int i) {
        return i / 2;
    }

    private int left(int i) {
        return i * 2;
    }

    private int right(int i) {
        return i * 2 + 1;
    }

    private void swap(int i, int j) {
        T tmp = (T) this.heapTable[j];
        this.heapTable[j] = this.heapTable[i];
        this.heapTable[i] = tmp;
    }

    private void goUp(int i) {
        if (i == 1) return;
        if (((T) this.heapTable[i]).compareTo((T) this.heapTable[parent(i)]) < 0) {
            swap(i, parent(i));
            goUp(parent(i));
        }
    }

    private void goDown(int i) {
        Integer smallestIndex = null;
        T smallestValue = (T) this.heapTable[i];
        T leftValue = left(i) > size ? null : (T) this.heapTable[left(i)];
        T rightValue = right(i) > size ? null : (T) this.heapTable[right(i)];
        
        if (leftValue != null && leftValue.compareTo(smallestValue) < 0) {
            smallestIndex = left(i);
            smallestValue = leftValue;
        }

        if (rightValue != null && rightValue.compareTo(smallestValue) < 0) {
            smallestIndex = right(i);
        }

        if (smallestIndex == null) return;
        
        swap(i, smallestIndex);
        goDown(smallestIndex);
    }

    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heapTable = new Object[capacity + 1];
    }

    @Override
    public void insert(T element) {
        if (this.size == this.capacity) throw new RuntimeException("Heap is full");
        this.heapTable[++this.size] = element;
        goUp(this.size);
    }
    
    @Override
    public T pop() {
        if (this.size == 0) throw new RuntimeException("Heap is empty");
        T ret = (T) this.heapTable[1];
        this.heapTable[1] = this.heapTable[this.size--];
        goDown(1);
        return ret;
    }
    
    @Override
    public T peek() {
        if (this.size == 0) throw new RuntimeException("Heap is empty");
        return (T) this.heapTable[1];
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean isFull() {
        return this.size == this.capacity;
    }

}