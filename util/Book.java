package util;

public class Book implements Comparable<Book> {
    private Integer id;
    private String title;
    private boolean available;

    public Book(Integer id, String title, boolean available) {
        this.id = id;
        this.title = title;
        this.available = available;
    }

    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void toggleAvailability() {
        this.available = !this.available;
    }

    public boolean equals(Object o) {
    	if(o instanceof Book) return this.id.equals(((Book)o).id);
    	return false;
    }

    @Override
    public int compareTo(Book o) {
        return this.id.compareTo(o.getId());
    }
    
    @Override
    public String toString() {
    	return this.id + " - " + this.title;
    }
}