package util;

import adt.ABB;
import impl.AVLImpl;

public class AVLibrary {

    private ABB<Book> library = new AVLImpl<>();
    private int availableCount = 0;
    private int notAvailableCount = 0;

    public void addBook(Integer id, String title) {
        Book newBook = new Book(id, title, true);
        Book book = library.get(newBook);
        if (book != null && book.isAvailable()) this.availableCount--;
        if (book != null && !book.isAvailable()) this.notAvailableCount--;
        if (book != null) library.remove(book);
        library.insert(newBook);
        this.availableCount++;
    }

    public Book searchBook(Integer id) {
        return library.get(new Book(id, null, false));
    }

    public boolean toggleBookAvailability(Integer id) {
        Book book = this.searchBook(id);
        if (book == null) return false;
        if (book.isAvailable()) {
            this.availableCount--;
            this.notAvailableCount++;
        } else {
            this.notAvailableCount--;
            this.availableCount++;
        }
        book.toggleAvailability();
        library.remove(book);
        library.insert(book);
        return true;
    }

    public Integer[] countBooksByAvailability() {
        return new Integer[] { availableCount, notAvailableCount };
    }

    public void print() {
        ((AVLImpl<Book>) library).printGraph();
    }

}
