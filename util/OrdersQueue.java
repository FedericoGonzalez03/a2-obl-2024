package util;

import adt.ExtendedPriorityQueue;
import impl.ExtendedMinHeapPriorityQueue;

public class OrdersQueue {
    ExtendedPriorityQueue<Order, OrderPriority> queue;

    private class OrderHash implements DoubleHashFunction<Order> {
        public int hash(Order o) {
            return o.getId();
        }
        public int secondHash(Order o) {
            return o.getId() * o.getId();
        }
    }

    public OrdersQueue(int capacity) {
        this.queue = new ExtendedMinHeapPriorityQueue<>(capacity, new OrderHash());
    }

    public void addOrder(Order order) {
        this.queue.insert(order, order.getPriority());
    }

    public void deliver(Order order) {
        this.queue.remove(order);
    }

    public void toDelivery(Order order) {
        order = this.queue.get(order).getData();
        order.setIsForDelivery(true);
        this.queue.updatePriority(order, order.getPriority());
    }

    public Order deliverNext() {
        return this.queue.pop();
    }

    public boolean hasOrders() {
        return !this.queue.isEmpty();
    }

}
