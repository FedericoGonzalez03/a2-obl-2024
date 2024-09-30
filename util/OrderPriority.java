package util;

public class OrderPriority implements Comparable<OrderPriority> {
    
    private Integer id;
    private Integer prio;
    private boolean isForDelivery;

    public OrderPriority(Order o, Integer prio) {
        this.id = o.getId();
        this.prio = prio;
        this.isForDelivery = o.isForDelivery();
    }

    public Integer getPrio() {
        return this.prio;
    }

    public boolean isForDelivery() {
        return this.isForDelivery;
    }

    public Integer getId() {
        return this.id;
    }

    @Override
    public int compareTo(OrderPriority op) {
        if (this.prio.compareTo(op.getPrio()) == 0) {
            if (this.isForDelivery == op.isForDelivery()) {
                return this.id.compareTo(op.getId());
            }
            if (this.isForDelivery) return -1;
            return 1;
        }
        return this.prio.compareTo(op.getPrio());
    }
}
