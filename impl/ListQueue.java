package impl;

import adt.List;
import adt.Queue;

public class ListQueue<T> implements Queue<T> {

    List<T> list;

    public ListQueue() {
        this.list = new LinkedList<>();
    }

    public void insert(T data) {
        list.add(data);
    }

    public T pop() {
        T ret = list.get(0);
        list.remove(0);
        return ret;
    }

    public T peek() {
        return list.get(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }
}
