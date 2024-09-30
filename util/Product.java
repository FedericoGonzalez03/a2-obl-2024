package util;

public class Product implements Comparable<Product> {
    
    public Integer id;
    public Integer price;

    public Product(Integer id, Integer price) {
        this.id = id;
        this.price = price;
    }
    
    @Override
    public int compareTo(Product o) {
        if (this.price.equals(o.price)) {
            return o.id.compareTo(this.id);
        }
        return this.price.compareTo(o.price);
    }    
}
