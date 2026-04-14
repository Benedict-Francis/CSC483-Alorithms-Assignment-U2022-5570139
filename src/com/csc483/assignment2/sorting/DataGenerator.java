package com.csc483.assignment2.sorting;

import java.util.Arrays;
import java.util.Random;

/**
 * Course  : CSC 483.1 - Algorithms Analysis and Design
 * Student : ONYEMACHI BENEDICT
 * Matric  : U2022/5570139
 *
 * DataGenerator.java - Generates int[] test datasets for sorting benchmarks.
 */
public class DataGenerator {

    private static final Random RNG      = new Random(12345);
    private static final int    DISTINCT = 10; // for manyDuplicates

    /** Random array of given size. */
    public static int[] random(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = RNG.nextInt(size * 10);
        return arr;
    }

    /** Already sorted ascending [0, 1, ..., size-1]. */
    public static int[] sorted(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = i;
        return arr;
    }

    /** Reverse sorted descending [size-1, ..., 0]. */
    public static int[] reverseSorted(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = size - 1 - i;
        return arr;
    }

    /** 90% sorted, 10% randomly swapped. */
    public static int[] nearlySorted(int size) {
        int[] arr = sorted(size);
        int swaps = Math.max(1, size / 10);
        for (int i = 0; i < swaps; i++) {
            int a = RNG.nextInt(size), b = RNG.nextInt(size);
            int tmp = arr[a]; arr[a] = arr[b]; arr[b] = tmp;
        }
        return arr;
    }

    /** Only DISTINCT unique values — high duplicate density. */
    public static int[] manyDuplicates(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = RNG.nextInt(DISTINCT);
        return arr;
    }

    /** Deep copy of an array. */
    public static int[] copy(int[] arr) {
        return Arrays.copyOf(arr, arr.length);
    }
}
