import java.util.Scanner;

import util.Order;
import util.OrdersQueue;

public class Ejercicio4 {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int ordersCount = sc.nextInt();
            sc.nextLine();
            int ops = sc.nextInt();
            sc.nextLine();
            OrdersQueue queue = new OrdersQueue(ordersCount);
            for (int i = 0; i < ops; i++) {
                String[] op = sc.nextLine().split(" ");
                Integer id = Integer.parseInt(op[1]);
                String action = op[0];
                Order order = new Order(id);
                switch (action) {
                    case "I":
                        Integer prio = Integer.parseInt(op[2]);
                        boolean isForDelivery = Boolean.parseBoolean(op[3]);
                        String items = op[4];
                        order.setPriority(prio);
                        order.setIsForDelivery(isForDelivery);
                        order.setItems(items);
                        queue.addOrder(order);
                        break;
                    case "E":
                        queue.deliver(order);
                        break;
                    case "C":
                        queue.toDelivery(order);
                        break;
                    default:
                        break;
                }
            }
            while (queue.hasOrders()) {
                System.out.println(queue.deliverNext());
            }
        }
    }
}
