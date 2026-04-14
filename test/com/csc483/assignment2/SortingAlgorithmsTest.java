package com.csc483.assignment2;

import com.csc483.assignment2.sorting.DataGenerator;
import com.csc483.assignment2.sorting.SortingAlgorithms;
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
 * SortingAlgorithmsTest.java - JUnit 5 tests for all four sorting algorithms.
 * Each is tested against: empty, single, sorted, reverse, random,
 * duplicates, two elements, and all-same inputs.
 */
@DisplayName("Sorting Algorithm Tests")
class SortingAlgorithmsTest {

    private SortingAlgorithms sorter;

    @BeforeEach void setUp() { sorter = new SortingAlgorithms(); }

    interface SortMethod { void sort(int[] arr); }

    private void assertSortsCorrectly(SortMethod method, int[] input) {
        int[] expected = Arrays.copyOf(input, input.length);
        Arrays.sort(expected);
        method.sort(input);
        assertArrayEquals(expected, input);
    }

    // -----------------------------------------------------------------------
    // Insertion Sort
    // -----------------------------------------------------------------------
    @Nested @DisplayName("Insertion Sort")
    class InsertionSortTests {
        @Test void empty()         { int[] a = {};       sorter.insertionSort(a); assertArrayEquals(new int[]{}, a); }
        @Test void single()        { int[] a = {5};      sorter.insertionSort(a); assertArrayEquals(new int[]{5}, a); }
        @Test void twoElements()   { int[] a = {9, 1};   sorter.insertionSort(a); assertArrayEquals(new int[]{1, 9}, a); }
        @Test void allSame()       { int[] a = {3,3,3};  sorter.insertionSort(a); assertArrayEquals(new int[]{3,3,3}, a); }
        @Test void alreadySorted() { assertSortsCorrectly(sorter::insertionSort, DataGenerator.sorted(500)); }
        @Test void reverse()       { assertSortsCorrectly(sorter::insertionSort, DataGenerator.reverseSorted(500)); }
        @Test void random()        { assertSortsCorrectly(sorter::insertionSort, DataGenerator.random(1000)); }
        @Test void duplicates()    { assertSortsCorrectly(sorter::insertionSort, DataGenerator.manyDuplicates(1000)); }

        @Test @DisplayName("Nearly-sorted: few comparisons")
        void nearlySortedEfficient() {
            int[] arr = DataGenerator.nearlySorted(1000);
            sorter.insertionSort(arr);
            assertTrue(SortingAlgorithms.isSorted(arr));
            assertTrue(sorter.comparisons < 100_000L);
        }

        @ParameterizedTest(name = "size={0}")
        @ValueSource(ints = {1, 10, 100, 500})
        void variousSizes(int size) { assertSortsCorrectly(sorter::insertionSort, DataGenerator.random(size)); }
    }

    // -----------------------------------------------------------------------
    // Merge Sort
    // -----------------------------------------------------------------------
    @Nested @DisplayName("Merge Sort")
    class MergeSortTests {
        @Test void empty()         { int[] a = {};      sorter.mergeSort(a); assertArrayEquals(new int[]{}, a); }
        @Test void single()        { int[] a = {7};     sorter.mergeSort(a); assertArrayEquals(new int[]{7}, a); }
        @Test void twoElements()   { int[] a = {9, 1};  sorter.mergeSort(a); assertArrayEquals(new int[]{1, 9}, a); }
        @Test void allSame()       { int[] a = {5,5,5}; sorter.mergeSort(a); assertArrayEquals(new int[]{5,5,5}, a); }
        @Test void alreadySorted() { assertSortsCorrectly(sorter::mergeSort, DataGenerator.sorted(10_000)); }
        @Test void reverse()       { assertSortsCorrectly(sorter::mergeSort, DataGenerator.reverseSorted(10_000)); }
        @Test void random()        { assertSortsCorrectly(sorter::mergeSort, DataGenerator.random(10_000)); }
        @Test void duplicates()    { assertSortsCorrectly(sorter::mergeSort, DataGenerator.manyDuplicates(10_000)); }
        @Test void nearlySorted()  { assertSortsCorrectly(sorter::mergeSort, DataGenerator.nearlySorted(10_000)); }

        @ParameterizedTest(name = "size={0}")
        @ValueSource(ints = {1, 10, 100, 1_000, 10_000})
        void variousSizes(int size) { assertSortsCorrectly(sorter::mergeSort, DataGenerator.random(size)); }
    }

    // -----------------------------------------------------------------------
    // Quick Sort
    // -----------------------------------------------------------------------
    @Nested @DisplayName("Quick Sort")
    class QuickSortTests {
        @Test void empty()         { int[] a = {};      sorter.quickSort(a); assertArrayEquals(new int[]{}, a); }
        @Test void single()        { int[] a = {3};     sorter.quickSort(a); assertArrayEquals(new int[]{3}, a); }
        @Test void twoElements()   { int[] a = {9, 1};  sorter.quickSort(a); assertArrayEquals(new int[]{1, 9}, a); }
        @Test void alreadySorted() { assertSortsCorrectly(sorter::quickSort, DataGenerator.sorted(10_000)); }
        @Test void reverse()       { assertSortsCorrectly(sorter::quickSort, DataGenerator.reverseSorted(10_000)); }
        @Test void random()        { assertSortsCorrectly(sorter::quickSort, DataGenerator.random(10_000)); }
        @Test void duplicates()    { assertSortsCorrectly(sorter::quickSort, DataGenerator.manyDuplicates(10_000)); }
        @Test void nearlySorted()  { assertSortsCorrectly(sorter::quickSort, DataGenerator.nearlySorted(10_000)); }

        @ParameterizedTest(name = "size={0}")
        @ValueSource(ints = {1, 10, 100, 1_000, 10_000})
        void variousSizes(int size) { assertSortsCorrectly(sorter::quickSort, DataGenerator.random(size)); }
    }

    // -----------------------------------------------------------------------
    // Heap Sort
    // -----------------------------------------------------------------------
    @Nested @DisplayName("Heap Sort")
    class HeapSortTests {
        @Test void empty()         { int[] a = {};      sorter.heapSort(a); assertArrayEquals(new int[]{}, a); }
        @Test void single()        { int[] a = {1};     sorter.heapSort(a); assertArrayEquals(new int[]{1}, a); }
        @Test void twoElements()   { int[] a = {9, 1};  sorter.heapSort(a); assertArrayEquals(new int[]{1, 9}, a); }
        @Test void alreadySorted() { assertSortsCorrectly(sorter::heapSort, DataGenerator.sorted(10_000)); }
        @Test void reverse()       { assertSortsCorrectly(sorter::heapSort, DataGenerator.reverseSorted(10_000)); }
        @Test void random()        { assertSortsCorrectly(sorter::heapSort, DataGenerator.random(10_000)); }
        @Test void duplicates()    { assertSortsCorrectly(sorter::heapSort, DataGenerator.manyDuplicates(10_000)); }
        @Test void nearlySorted()  { assertSortsCorrectly(sorter::heapSort, DataGenerator.nearlySorted(10_000)); }

        @ParameterizedTest(name = "size={0}")
        @ValueSource(ints = {1, 10, 100, 1_000, 10_000})
        void variousSizes(int size) { assertSortsCorrectly(sorter::heapSort, DataGenerator.random(size)); }
    }

    // -----------------------------------------------------------------------
    // isSorted utility
    // -----------------------------------------------------------------------
    @Test void isSorted_true()      { assertTrue(SortingAlgorithms.isSorted(new int[]{1, 2, 3})); }
    @Test void isSorted_false()     { assertFalse(SortingAlgorithms.isSorted(new int[]{3, 1, 2})); }
    @Test void isSorted_edgeCases() {
        assertTrue(SortingAlgorithms.isSorted(null));
        assertTrue(SortingAlgorithms.isSorted(new int[]{}));
        assertTrue(SortingAlgorithms.isSorted(new int[]{42}));
    }
}
