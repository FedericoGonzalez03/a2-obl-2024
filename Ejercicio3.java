import java.util.Scanner;

import adt.Heap;
import impl.MinHeap;
import util.Product;


public class Ejercicio3 {
    
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int prodsCount = sc.nextInt();
            sc.nextLine();
            Integer[] prods = new Integer[prodsCount];
            for (int i = 0; i < prodsCount; i++) {
                String[] prodProps = sc.nextLine().split(" ");
                Integer id = Integer.parseInt(prodProps[0]);
                Integer price = Integer.parseInt(prodProps[1]);
                if (prods[id] == null) prods[id] = price;
                else if (prods[id].compareTo(price) > 0) prods[id] = price;
            }
            Heap<Product> prodsHeap = new MinHeap<>(prodsCount);
            for (int i = 0; i < prodsCount; i++) {
                if (prods[i] != null) {
                    Product prod = new Product(i, prods[i]);
                    prodsHeap.insert(prod);
                }
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
