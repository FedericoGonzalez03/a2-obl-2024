package util;

import adt.ABB;
import impl.AVL;

public class AVLibrary implements Library{

    private ABB<Book> library = new AVL<>();
    private int availableCount = 0;
    private int notAvailableCount = 0;

    @Override
    public void addBook(Integer id, String title) {
        Book newBook = new Book(id, title, true);
        Book book = library.get(newBook);
        if (book != null && book.isAvailable()) this.availableCount--;
        if (book != null && !book.isAvailable()) this.notAvailableCount--;
        if (book != null) library.remove(book);
        library.insert(newBook);
        this.availableCount++;
    }

    @Override
    public Book searchBook(Integer id) {
        return library.get(new Book(id, null, false));
    }

    @Override
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

    @Override
    public Integer[] countBooksByAvailability() {
        return new Integer[] { availableCount, notAvailableCount };
    }

    public void print() {
        ((AVL<Book>) library).printGraph();
    }

}
