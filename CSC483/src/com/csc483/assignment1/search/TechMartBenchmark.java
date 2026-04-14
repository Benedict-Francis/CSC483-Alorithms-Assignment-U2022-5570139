package com.csc483.assignment1.search;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Course  : CSC 483.1 - Algorithms Analysis and Design
 * Student : ONYEMACHI BENEDICT
 * Matric  : U2022/5570139
 *
 * TechMartBenchmark.java (Question 1)
 * Generates 100,000 products and compares sequential search,
 * binary search, and hybrid name search performance.
 */
public class TechMartBenchmark {

    private static final int N          = 100_000;
    private static final int ID_RANGE   = 200_000;
    private static final int WARMUP     = 2;
    private static final int RUNS       = 5;

    private static final String[] CATEGORIES = {
        "Laptop", "Phone", "Tablet", "Monitor", "Keyboard",
        "Mouse", "Headset", "Webcam", "Speaker", "Charger"
    };

    public static void main(String[] args) {
        System.out.println("Generating " + N + " products...");
        Product[] products = generateProducts(N);

        // Sorted copy for binary search
        Product[] sorted = Arrays.copyOf(products, N);
        Arrays.sort(sorted);

        ProductSearch searcher = new ProductSearch();
        searcher.buildIndex(sorted);

        int bestId  = sorted[0].getProductId();       // first element
        int avgId   = sorted[N / 2].getProductId();   // middle element
        int missId  = -1;                              // guaranteed miss

        System.out.println("\n" + "=".repeat(64));
        System.out.printf("TECHMART SEARCH PERFORMANCE ANALYSIS (n = %,d products)%n", N);
        System.out.println("=".repeat(64));

        // Sequential Search
        System.out.println("\nSEQUENTIAL SEARCH:");
        System.out.printf("  Best  Case (ID at index 0) : %8.3f ms%n", timeSequential(products, bestId,  searcher));
        System.out.printf("  Avg   Case (middle ID)     : %8.3f ms%n", timeSequential(products, avgId,   searcher));
        System.out.printf("  Worst Case (ID not found)  : %8.3f ms%n", timeSequential(products, missId,  searcher));

        // Binary Search
        System.out.println("\nBINARY SEARCH:");
        System.out.printf("  Best  Case (ID at midpoint): %8.3f ms%n", timeBinary(sorted, sorted[N/2].getProductId(), searcher));
        System.out.printf("  Avg   Case (middle ID)     : %8.3f ms%n", timeBinary(sorted, avgId,   searcher));
        System.out.printf("  Worst Case (ID not found)  : %8.3f ms%n", timeBinary(sorted, missId,  searcher));

        double seqAvg = timeSequential(products, avgId, searcher);
        double binAvg = timeBinary(sorted, avgId, searcher);
        System.out.printf("%nPERFORMANCE IMPROVEMENT: Binary search is ~%.0fx faster on average%n",
                seqAvg / binAvg);

        // Hybrid Name Search
        System.out.println("\nHYBRID NAME SEARCH:");
        String targetName = sorted[N / 2].getName();
        System.out.printf("  Avg search time : %8.3f ms%n", timeHybridSearch(searcher, targetName));
        System.out.printf("  Avg insert time : %8.3f ms%n", timeHybridInsert(sorted, searcher));

        System.out.println("\n" + "=".repeat(64));
    }

    // --- Timing helpers ---

    private static double timeSequential(Product[] arr, int id, ProductSearch s) {
        for (int i = 0; i < WARMUP; i++) s.sequentialSearchById(arr, id);
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            long t = System.nanoTime();
            s.sequentialSearchById(arr, id);
            total += System.nanoTime() - t;
        }
        return (total / (double) RUNS) / 1_000_000.0;
    }

    private static double timeBinary(Product[] arr, int id, ProductSearch s) {
        for (int i = 0; i < WARMUP; i++) s.binarySearchById(arr, id);
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            long t = System.nanoTime();
            s.binarySearchById(arr, id);
            total += System.nanoTime() - t;
        }
        return (total / (double) RUNS) / 1_000_000.0;
    }

    private static double timeHybridSearch(ProductSearch s, String name) {
        for (int i = 0; i < WARMUP; i++) s.hybridSearchByName(name);
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            long t = System.nanoTime();
            s.hybridSearchByName(name);
            total += System.nanoTime() - t;
        }
        return (total / (double) RUNS) / 1_000_000.0;
    }

    private static double timeHybridInsert(Product[] sorted, ProductSearch s) {
        Random rng = new Random(999);
        long total = 0;
        Product[] arr = Arrays.copyOf(sorted, sorted.length);
        for (int i = 0; i < RUNS; i++) {
            Product p = new Product(ID_RANGE + i + 1, "NewProduct" + i, "Misc",
                    rng.nextDouble() * 500, 10);
            long t = System.nanoTime();
            arr = s.addProduct(arr, p);
            total += System.nanoTime() - t;
        }
        return (total / (double) RUNS) / 1_000_000.0;
    }

    // --- Dataset generation ---

    public static Product[] generateProducts(int count) {
        Random rng = new Random(42);
        Set<Integer> usedIds = new HashSet<>();
        Product[] products = new Product[count];
        for (int i = 0; i < count; i++) {
            int id;
            do { id = rng.nextInt(ID_RANGE) + 1; } while (!usedIds.add(id));
            String cat  = CATEGORIES[rng.nextInt(CATEGORIES.length)];
            String name = cat + "_" + id;
            products[i] = new Product(id, name, cat,
                    10.0 + rng.nextDouble() * 990.0, rng.nextInt(500));
        }
        return products;
    }
}
