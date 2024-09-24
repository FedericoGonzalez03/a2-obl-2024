package util;

public interface Library {
    
    public void addBook(Integer id, String title);

    public Book searchBook(Integer id);

    public boolean toggleBookAvailability(Integer id);

    public Integer[] countBooksByAvailability();
}
