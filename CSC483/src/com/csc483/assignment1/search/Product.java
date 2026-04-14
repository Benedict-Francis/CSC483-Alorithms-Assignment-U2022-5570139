package com.csc483.assignment1.search;

/**
 * Course  : CSC 483.1 - Algorithms Analysis and Design
 * Student : ONYEMACHI BENEDICT
 * Matric  : U2022/5570139
 * Dept    : Computer Science, University of Port Harcourt
 * Session : 2025/2026
 *
 * Product.java - Represents a product in the TechMart store.
 * Implements Comparable so arrays can be sorted by productId
 * (required for binary search).
 */
public class Product implements Comparable<Product> {

    private int    productId;
    private String name;
    private String category;
    private double price;
    private int    stock;

    public Product(int productId, String name, String category, double price, int stock) {
        if (productId <= 0)                        throw new IllegalArgumentException("Product ID must be positive.");
        if (name == null || name.isBlank())        throw new IllegalArgumentException("Name cannot be empty.");
        if (price < 0)                             throw new IllegalArgumentException("Price cannot be negative.");
        if (stock < 0)                             throw new IllegalArgumentException("Stock cannot be negative.");
        this.productId = productId;
        this.name      = name;
        this.category  = category;
        this.price     = price;
        this.stock     = stock;
    }

    // Getters
    public int    getProductId() { return productId; }
    public String getName()      { return name;      }
    public String getCategory()  { return category;  }
    public double getPrice()     { return price;     }
    public int    getStock()     { return stock;     }

    // Setters
    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative.");
        this.price = price;
    }
    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("Stock cannot be negative.");
        this.stock = stock;
    }

    /** Natural ordering by productId — required for binary search. */
    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.productId, other.productId);
    }

    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', category='%s', price=%.2f, stock=%d}",
                productId, name, category, price, stock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        return this.productId == ((Product) o).productId;
    }

    @Override
    public int hashCode() { return Integer.hashCode(productId); }
}
