package com.csc483.assignment1.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Course  : CSC 483.1 - Algorithms Analysis and Design
 * Student : ONYEMACHI BENEDICT
 * Matric  : U2022/5570139
 *
 * ProductSearch.java - Search and insertion operations for Product arrays.
 *
 * Complexity Summary:
 *   sequentialSearchById  — O(1) best / O(n) avg & worst
 *   binarySearchById      — O(1) best / O(log n) avg & worst
 *   searchByName          — O(n) all cases (array unsorted by name)
 *   hybridSearchByName    — O(1) expected (HashMap lookup)
 *   addProduct            — O(n) shift + O(log n) position find
 */
public class ProductSearch {

    // HashMap index for O(1) name lookups
    private final Map<String, Product> nameIndex = new HashMap<>();

    /** Index a single product by name (lowercase). */
    public void indexProduct(Product p) {
        if (p == null) throw new IllegalArgumentException("Cannot index null product.");
        nameIndex.put(p.getName().toLowerCase(), p);
    }

    /** Rebuild the name index from an entire array. */
    public void buildIndex(Product[] products) {
        nameIndex.clear();
        if (products == null) return;
        for (Product p : products) {
            if (p != null) indexProduct(p);
        }
    }

    // -----------------------------------------------------------------------
    // Sequential Search — O(n)
    // -----------------------------------------------------------------------
    public Product sequentialSearchById(Product[] products, int targetId) {
        if (products == null) return null;
        for (Product p : products) {
            if (p != null && p.getProductId() == targetId) return p;
        }
        return null;
    }

    // -----------------------------------------------------------------------
    // Binary Search — O(log n)  [array must be sorted by productId]
    // -----------------------------------------------------------------------
    public Product binarySearchById(Product[] products, int targetId) {
        if (products == null) return null;
        int low = 0, high = products.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (products[mid] == null) { high--; continue; }
            int cmp = Integer.compare(products[mid].getProductId(), targetId);
            if (cmp == 0)     return products[mid];
            else if (cmp < 0) low  = mid + 1;
            else              high = mid - 1;
        }
        return null;
    }

    // -----------------------------------------------------------------------
    // Name Search — O(n) linear scan
    // -----------------------------------------------------------------------
    public Product searchByName(Product[] products, String targetName) {
        if (products == null || targetName == null) return null;
        String lower = targetName.toLowerCase();
        for (Product p : products) {
            if (p != null && p.getName().toLowerCase().equals(lower)) return p;
        }
        return null;
    }

    // -----------------------------------------------------------------------
    // Hybrid Name Search — O(1) via HashMap
    // -----------------------------------------------------------------------
    public Product hybridSearchByName(String targetName) {
        if (targetName == null) return null;
        return nameIndex.get(targetName.toLowerCase());
    }

    // -----------------------------------------------------------------------
    // Insert maintaining sorted order — O(n)
    // -----------------------------------------------------------------------
    public Product[] addProduct(Product[] products, Product newProduct) {
        if (newProduct == null) throw new IllegalArgumentException("Cannot add null product.");
        if (products == null) products = new Product[0];

        // Binary search for correct insertion position
        int low = 0, high = products.length - 1, insertPos = products.length;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (products[mid] == null) { high--; continue; }
            if (products[mid].getProductId() < newProduct.getProductId()) {
                low = mid + 1;
            } else {
                insertPos = mid;
                high = mid - 1;
            }
        }

        Product[] result = Arrays.copyOf(products, products.length + 1);
        System.arraycopy(result, insertPos, result, insertPos + 1, products.length - insertPos);
        result[insertPos] = newProduct;
        indexProduct(newProduct); // keep HashMap in sync
        return result;
    }
}
