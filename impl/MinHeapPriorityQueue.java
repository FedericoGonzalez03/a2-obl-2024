package impl;

import adt.Heap;
import adt.PriorityQueue;

public class MinHeapPriorityQueue<T, P extends Comparable<P>> implements PriorityQueue<T, P> {

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

    Heap<PriorityQueue.Node<T, P>> heap;

    public MinHeapPriorityQueue(int capacity) {
        this.heap = new MinHeap<>(capacity);
    }

    @Override
    public void insert(T data, P priority) {
        Node n = new Node(data, priority);
        this.heap.insert(n);
    }

    @Override
    public T pop() {
        return this.heap.pop().getData();
    }

    @Override
    public T peek() {
        return this.heap.peek().getData();
    }

    @Override
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    @Override
    public boolean isFull() {
        return this.heap.isFull();
    }
}
