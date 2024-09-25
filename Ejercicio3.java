import java.util.Scanner;

import adt.Heap;
import impl.MinHeap;
import util.Product;


public class Ejercicio3 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int prodsCount = sc.nextInt();
            sc.nextLine();
            Heap<Product> prodsHeap = new MinHeap<>(prodsCount);
            for (int i = 0; i < prodsCount; i++) {
                String prodProps[] = sc.nextLine().split(" ");
                Integer id = Integer.parseInt(prodProps[0]);
                Integer precio = Integer.parseInt(prodProps[1]);
                Product prod = new Product(id, precio);
                prodsHeap.insert(prod);
            }
            int showCount = sc.nextInt();
            sc.nextLine();
            for (int i = 0; i < showCount; i++) {
                Product prod = prodsHeap.pop();
                System.out.println(prod.id);
            }
        }
    }
}
