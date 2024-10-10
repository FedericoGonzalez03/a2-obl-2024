package impl;

import adt.ExtendedPriorityQueue;
import adt.Map;
import adt.PriorityQueue;
import impl.ClosedHashMap.CollisionSolvingMethod;
import util.DoubleHashFunction;

@SuppressWarnings("unchecked")
public class ExtendedMinHeapPriorityQueue<T, P extends Comparable<P>> implements ExtendedPriorityQueue<T, P> {

    private class Node implements PriorityQueue.Node<T, P> {
        private T data;
        private P priority;

        public T getData(){
            return this.data;
        }

        public P getPriority(){
            return this.priority;
        }
        
        public Node(T data, P priority) {
            this.data = data;
            this.priority = priority;
        }
        
        public int compareTo(PriorityQueue.Node<T, P> o) {
            return this.priority.compareTo(o.getPriority());
        }
    }

    private Map<T,Integer> positions;
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
        PriorityQueue.Node<T, P> tmp = (PriorityQueue.Node<T, P>) this.heapTable[j];
        this.heapTable[j] = this.heapTable[i];
        this.heapTable[i] = tmp;
        this.positions.set(((PriorityQueue.Node<T, P>) this.heapTable[j]).getData(), j);
        this.positions.set(((PriorityQueue.Node<T, P>) this.heapTable[i]).getData(), i);
    }

    private void goUp(int i) {
        if (i == 1) return;
        if (((PriorityQueue.Node<T, P>) this.heapTable[i]).compareTo((PriorityQueue.Node<T, P>) this.heapTable[parent(i)]) < 0) {
            swap(i, parent(i));
            goUp(parent(i));
        }
    }

    private void goDown(int i) {
        Integer smallestIndex = null;
        PriorityQueue.Node<T, P> smallestValue = (PriorityQueue.Node<T, P>) this.heapTable[i];
        PriorityQueue.Node<T, P> leftValue = left(i) > size ? null : (PriorityQueue.Node<T, P>) this.heapTable[left(i)];
        PriorityQueue.Node<T, P> rightValue = right(i) > size ? null : (PriorityQueue.Node<T, P>) this.heapTable[right(i)];
        
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

    public ExtendedMinHeapPriorityQueue(int capacity, DoubleHashFunction<T> dhf) {
        this.capacity = capacity;
        this.heapTable = new Object[capacity + 1];
        this.positions = new ClosedHashMap<>((int) (capacity/70d * 100), dhf, CollisionSolvingMethod.DOUBLE_HASH);
    }


    @Override
    public void insert(T element, P priority) {
        if (this.isFull()) throw new RuntimeException("PriorityQueue is full");
        this.heapTable[++this.size] = new Node(element, priority);
        this.positions.set(element, this.size);
        goUp(this.size);
    }

    @Override
    public void remove(T element) {
        Integer index = this.positions.get(element);
        if (index == null) throw new RuntimeException("Element not found in this PriorityQueue");
        this.positions.remove(element);
        if (index.equals(size)) {
            this.heapTable[index] = null;
            size--;
            return;
        }
        this.heapTable[index] = this.heapTable[this.size--];
        this.positions.set(((PriorityQueue.Node<T, P>) this.heapTable[index]).getData(), index);
        goDown(index);
        goUp(index);
    }

    @Override
    public PriorityQueue.Node<T, P> get(T element) {
        Integer index = this.positions.get(element);
        if (index == null) throw new RuntimeException("Element not found in this PriorityQueue");
        return (PriorityQueue.Node<T, P>) this.heapTable[index];
    }

    @Override
    public void updatePriority(T oldElement, P newPriority) {
        Integer index = this.positions.get(oldElement);
        if (index == null) throw new RuntimeException("Element not found in this PriorityQueue");
        this.heapTable[index] = new Node(oldElement, newPriority);
        this.positions.set(oldElement, index);
        goDown(index);
        goUp(index);
    }
    
    @Override
    public T pop() {
        if (this.isEmpty()) throw new RuntimeException("PriorityQueue is empty");
        T ret = ((PriorityQueue.Node<T, P>) this.heapTable[1]).getData();
        this.positions.remove(ret);
        this.heapTable[1] = this.heapTable[this.size--];
        this.positions.set(((PriorityQueue.Node<T, P>) this.heapTable[1]).getData(), 1);
        goDown(1);
        return ret;
    }
    
    @Override
    public T peek() {
        if (this.isEmpty()) throw new RuntimeException("PriorityQueue is empty");
        return ((PriorityQueue.Node<T, P>) this.heapTable[1]).getData();
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