package util;

import adt.Map;
import impl.ClosedHashMap;
import impl.ClosedHashMap.CollisionSolvingMethod;

public class HMLibrary implements Library {

    private class Hash implements DoubleHashFunction<Integer> {
        public int hash(Integer i) {
            return i;
        }
        public int secondHash(Integer i) {
            return i*i;
        }
    }


    private Map<Integer, Book> library;
    private int availableCount = 0;
    private int notAvailableCount = 0;

    public HMLibrary(int capacity) {
        this.library = new ClosedHashMap<>(capacity, new Hash(), CollisionSolvingMethod.DOUBLE_HASH);
    }

    @Override
    public void addBook(Integer id, String title) {
        Book newBook = new Book(id, title, true);
        Book book = library.get(id);
        if (book != null && book.isAvailable()) this.availableCount--;
        if (book != null && !book.isAvailable()) this.notAvailableCount--;
        library.set(id, newBook);
        this.availableCount++;
    }

    @Override
    public Book searchBook(Integer id) {
        return library.get(id);
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
        library.set(id, book);
        return true;
    }

    @Override
    public Integer[] countBooksByAvailability() {
        return new Integer[] { availableCount, notAvailableCount };
    }

    public void print() {
        throw new UnsupportedOperationException("Unimplemented method 'print'");
    }

}
