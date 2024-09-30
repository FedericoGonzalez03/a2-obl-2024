package impl;

import adt.List;
import adt.Iterator;

public class LinkedList<T extends Comparable<T>> implements List<T> {

    private class Node {
        public T data;
        public Node next = null;
    }

    private class NodeIterator implements Iterator<T>{
        private Node node;

        public NodeIterator(Node node) {
            this.node = node;
        }

        public boolean hasNext() {
            return this.node != null;
        }

        public T next() {
            T ret = this.node.data;
            this.node = this.node.next;
            return ret;
        }
    }

    int size = 0;
    Node start = null;
    Node end = null;

    public void add(T element) {
        if (this.isEmpty()) {
            this.start = new Node();
            this.start.data = element;
            this.end = this.start;
        } else {
            this.end.next = new Node();
            this.end.next.data = element;
            this.end = this.end.next;
        }
        this.size++;
    }

    public void add(int index, T element) {
        if (this.isEmpty()) {
            if (index == 0) {
                this.start = new Node();
                this.start.data = element;
                this.end = this.start;
            } else {
                throw new RuntimeException("Not reachable index");
            }
        } else {
            boolean wasAdded = false;
            Node currNode = this.start;
            int i = 0;
            while (currNode != null && !wasAdded) {
                if (i == index) {
                    Node tmp = currNode;
                    currNode.data = element;
                    currNode.next = tmp;
                    wasAdded = true;
                }
                currNode = currNode.next;
                i++;
            }
            if (i == index) {
                Node tmp = this.end;
                this.end.data = element;
                this.end.next = tmp;
                wasAdded = true;
            } else if (!wasAdded) {
                throw new RuntimeException("Not reachable index");
            }
        }
        this.size++;
    }

    public T get(int index) {
        if (this.isEmpty()) throw new RuntimeException("Not reachable index");
        Node currNode = this.start;
        int i = 0;
        while (currNode != null) {
            if (i == index) return currNode.data;
            currNode = currNode.next;
            i++;
        }
        throw new RuntimeException("Not reachable index");
        
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public Iterator<T> iterator() {
        return new NodeIterator(this.start);
    }
}
