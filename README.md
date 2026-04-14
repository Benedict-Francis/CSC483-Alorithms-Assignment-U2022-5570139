# CSC 483.1 – Algorithms Analysis and Design

**Student :** ONYEMACHI BENEDICT  
**Matric  :** U2022/5570139  
**University:** University of Port Harcourt  
**Session :** 2025/2026

---

## Project Structure

```
CSC483/
├── src/
│   ├── com/csc483/assignment1/search/
│   │   ├── Product.java            — Product entity with validation
│   │   ├── ProductSearch.java      — Sequential, binary & hybrid search
│   │   └── TechMartBenchmark.java  — Main benchmark (Question 1)
│   └── com/csc483/assignment2/sorting/
│       ├── SortingAlgorithms.java  — Insertion, Merge, Quick, Heap Sort
│       ├── DataGenerator.java      — Test dataset generator
│       └── SortingBenchmark.java   — Sorting benchmark (Question 2)
├── test/
│   ├── com/csc483/assignment1/ProductSearchTest.java
│   └── com/csc483/assignment2/SortingAlgorithmsTest.java
├── data/
│   └── sample_products.csv
└── pom.xml
```

---

## Compilation & Execution

### Without Maven

```bash
# Compile
find src -name "*.java" | xargs javac -d out/

# Run Question 1 — Search Benchmark
java -cp out com.csc483.assignment1.search.TechMartBenchmark

# Run Question 2 — Sorting Benchmark
java -cp out com.csc483.assignment2.sorting.SortingBenchmark
```

### With Maven

```bash
mvn compile          # Compile
mvn test             # Run tests
mvn package          # Build JAR
```

---

## Algorithm Complexity Reference

| Algorithm | Best | Average | Worst | Space | Stable |
|-----------|------|---------|-------|-------|--------|
| Sequential Search | O(1) | O(n) | O(n) | O(1) | ✓ |
| Binary Search | O(1) | O(log n) | O(log n) | O(1) | ✓ |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) | ✓ |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | ✓ |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) | ✗ |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) | ✗ |

> Insertion Sort is skipped for n=100,000 in the benchmark (too slow).  
> Java 11+ required.
