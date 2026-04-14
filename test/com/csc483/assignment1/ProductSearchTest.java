package com.csc483.assignment1;

import com.csc483.assignment1.search.Product;
import com.csc483.assignment1.search.ProductSearch;
import com.csc483.assignment1.search.TechMartBenchmark;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Course  : CSC 483.1 - Algorithms Analysis and Design
 * Student : ONYEMACHI BENEDICT
 * Matric  : U2022/5570139
 *
 * ProductSearchTest.java - JUnit 5 tests for ProductSearch.
 */
@DisplayName("ProductSearch Tests")
class ProductSearchTest {

    private ProductSearch searcher;
    private Product[]     smallArray;   // unsorted, 5 elements
    private Product[]     sortedArray;  // sorted by ID, 5 elements

    @BeforeEach
    void setUp() {
        searcher = new ProductSearch();
        smallArray = new Product[]{
            new Product(30, "Monitor",  "Display",    299.99, 50),
            new Product(10, "Keyboard", "Peripheral",  49.99, 200),
            new Product(50, "Laptop",   "Laptop",     999.99, 30),
            new Product(20, "Mouse",    "Peripheral",  19.99, 300),
            new Product(40, "Headset",  "Audio",       79.99, 100),
        };
        sortedArray = Arrays.copyOf(smallArray, smallArray.length);
        Arrays.sort(sortedArray);
        searcher.buildIndex(sortedArray);
    }

    // Product validation
    @Test void product_invalidId()    { assertThrows(IllegalArgumentException.class, () -> new Product(0, "X", "Y", 1.0, 1)); }
    @Test void product_blankName()    { assertThrows(IllegalArgumentException.class, () -> new Product(1, " ", "Y", 1.0, 1)); }
    @Test void product_negativePrice(){ assertThrows(IllegalArgumentException.class, () -> new Product(1, "X", "Y", -0.01, 1)); }

    // Sequential Search
    @Test void sequential_bestCase()  { assertEquals(30, searcher.sequentialSearchById(smallArray, 30).getProductId()); }
    @Test void sequential_found()     { assertEquals(50, searcher.sequentialSearchById(smallArray, 50).getProductId()); }
    @Test void sequential_notFound()  { assertNull(searcher.sequentialSearchById(smallArray, 999)); }
    @Test void sequential_nullArray() { assertNull(searcher.sequentialSearchById(null, 10)); }
    @Test void sequential_emptyArray(){ assertNull(searcher.sequentialSearchById(new Product[0], 10)); }

    // Binary Search
    @Test void binary_findsAll() {
        for (Product p : sortedArray)
            assertEquals(p.getProductId(), searcher.binarySearchById(sortedArray, p.getProductId()).getProductId());
    }
    @Test void binary_notFound()  { assertNull(searcher.binarySearchById(sortedArray, 999)); }
    @Test void binary_nullArray() { assertNull(searcher.binarySearchById(null, 10)); }

    @ParameterizedTest(name = "Binary search finds ID {0}")
    @ValueSource(ints = {10, 20, 30, 40, 50})
    void binary_parameterized(int id) { assertNotNull(searcher.binarySearchById(sortedArray, id)); }

    // Name Search
    @Test void nameSearch_found()           { assertEquals("Laptop", searcher.searchByName(smallArray, "Laptop").getName()); }
    @Test void nameSearch_caseInsensitive() { assertNotNull(searcher.searchByName(smallArray, "KEYBOARD")); }
    @Test void nameSearch_notFound()        { assertNull(searcher.searchByName(smallArray, "Tablet")); }

    // Hybrid Search
    @Test void hybrid_found()           { assertEquals(50, searcher.hybridSearchByName("Laptop").getProductId()); }
    @Test void hybrid_caseInsensitive() { assertNotNull(searcher.hybridSearchByName("MONITOR")); }
    @Test void hybrid_notFound()        { assertNull(searcher.hybridSearchByName("Tablet")); }
    @Test void hybrid_nullInput()       { assertNull(searcher.hybridSearchByName(null)); }

    // addProduct
    @Test void addProduct_maintainsSorted() {
        Product[] result = searcher.addProduct(sortedArray, new Product(25, "WebCam", "Peripheral", 39.99, 80));
        assertEquals(sortedArray.length + 1, result.length);
        assertTrue(isSortedById(result));
    }
    @Test void addProduct_updatesIndex() {
        searcher.addProduct(sortedArray, new Product(25, "WebCam", "Peripheral", 39.99, 80));
        assertNotNull(searcher.hybridSearchByName("WebCam"));
    }
    @Test void addProduct_insertAtBeginning() {
        Product[] result = searcher.addProduct(sortedArray, new Product(1, "AProduct", "Misc", 5.0, 10));
        assertEquals(1, result[0].getProductId());
    }
    @Test void addProduct_insertAtEnd() {
        Product[] result = searcher.addProduct(sortedArray, new Product(100, "ZProduct", "Misc", 5.0, 10));
        assertEquals(100, result[result.length - 1].getProductId());
    }
    @Test void addProduct_nullThrows() {
        assertThrows(IllegalArgumentException.class, () -> searcher.addProduct(sortedArray, null));
    }

    // Large dataset smoke test
    @Test @Timeout(30)
    void largeDataset_smokeTest() {
        Product[] products = TechMartBenchmark.generateProducts(10_000);
        Arrays.sort(products);
        Product target = products[5000];
        assertEquals(target.getProductId(),
                searcher.binarySearchById(products, target.getProductId()).getProductId());
    }

    // Helper
    private static boolean isSortedById(Product[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i].getProductId() > arr[i + 1].getProductId()) return false;
        return true;
    }
}
