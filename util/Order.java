package util;

public class Order implements Comparable<Order> {

    private Integer id;
    private Integer prio;
    private boolean isForDelivery;
    private String items;

    public Order(Integer id) {
        this.id = id;
    }

    public Order(Integer id, Integer prio, boolean isForDelivery, String items) {
        this.id = id;
        this.prio = prio;
        this.items = items;
        this.isForDelivery = isForDelivery;
    }

    public void setPriority(Integer prio) {
        this.prio = prio;
    }

    public void setIsForDelivery(boolean isForDelivery) {
        this.isForDelivery = isForDelivery;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public Integer getId() {
        return this.id;
    }

    public OrderPriority getPriority() {
        return new OrderPriority(this, this.prio);
    }

    public boolean isForDelivery() {
        return this.isForDelivery;
    }

    public String getItems() {
        return this.items;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Order) return this.id.equals(((Order) o).getId());
        return this == o;
    }

    @Override
    public int compareTo(Order o) {
        return this.prio.compareTo(o.prio);
    }

    @Override
    public String toString(){
        return this.id + " " + this.getPriority().getPrio() + " " + this.isForDelivery + " " + this.items;
    }
}
