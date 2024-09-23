import java.util.Scanner;

import util.AVLibrary;
import util.Book;

public class Ejercicio1 {
    public static void main(String[] args) {
        System.setProperty("line.separator", "\n");
        AVLibrary library = new AVLibrary();
        try (Scanner sc = new Scanner(System.in)) {
            int ops = sc.nextInt();
            sc.nextLine();
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
                        System.out.print("libro_no_encontrado\n");
                    else
                        System.out.print(book.getTitle() + " " + (book.isAvailable() ? "H" : "D") + "\n");
                    break;
                case "TOGGLE":
                    boolean found = library.toggleBookAvailability(Integer.parseInt(op[1]));
                    if (!found) System.out.print("libro_no_encontrado\n");
                    break;

                case "COUNT":
                    Integer[] counts = library.countBooksByAvailability();
                    System.out.print(counts[0] + counts[1] + " " + counts[0] + " " + counts[1] + "\n");
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
