package impl;

import adt.List;
import adt.Stack;

public class ListStack<T> implements Stack<T> {
     
    List<T> list;

    public ListStack() {
        this.list = new LinkedList<>();
    }
    
    public void push(T element) {
        this.list.add(0, element);
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

    public Stack<T> getReverse() {
        Stack<T> ret = new ListStack<>();
        for (int i = 0; i < this.size(); i++) ret.push(this.list.get(i));
        return ret;
    }

}
