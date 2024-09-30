import java.util.Scanner;

import util.HMLibrary;
import util.Book;

public class Ejercicio2 {
    
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int ops = sc.nextInt();
            sc.nextLine();
            HMLibrary library = new HMLibrary((int) ((ops / 70d) * 100));
            for (int i = 0; i < ops; i++) {
                String op[] = sc.nextLine().split(" ");
                String action = op[0];
                switch (action) {
                case "ADD":
                    library.addBook(Integer.parseInt(op[1]), op[2]);
                    break;
                case "FIND":
                    Book book = library.searchBook(Integer.parseInt(op[1]));
                    if (book == null)
                        System.out.println("libro_no_encontrado");
                    else
                        System.out.println(book.getTitle() + " " + (book.isAvailable() ? "H" : "D"));
                    break;
                case "TOGGLE":
                    boolean found = library.toggleBookAvailability(Integer.parseInt(op[1]));
                    if (!found) System.out.println("libro_no_encontrado");
                    break;
                case "COUNT":
                    Integer[] counts = library.countBooksByAvailability();
                    System.out.println(counts[0] + counts[1] + " " + counts[0] + " " + counts[1]);
                    break;
                case "PRINT":
                    library.print();
                default:
                    break;
                }
            }
        }
    }
}
