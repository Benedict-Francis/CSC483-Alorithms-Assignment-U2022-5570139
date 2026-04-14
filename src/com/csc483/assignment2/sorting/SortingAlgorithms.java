package com.csc483.assignment2.sorting;

/**
 * Course  : CSC 483.1 - Algorithms Analysis and Design
 * Student : ONYEMACHI BENEDICT
 * Matric  : U2022/5570139
 *
 * SortingAlgorithms.java - Four classic sorting algorithms.
 * All sort int[] arrays in ascending order in-place
 * (except Merge Sort which needs O(n) auxiliary space).
 *
 * Complexity Reference:
 *   Insertion Sort : Best O(n)        | Avg O(n²)      | Worst O(n²)      | Space O(1) | Stable
 *   Merge Sort     : Best O(n log n)  | Avg O(n log n) | Worst O(n log n) | Space O(n) | Stable
 *   Quick Sort     : Best O(n log n)  | Avg O(n log n) | Worst O(n²)      | Space O(log n) | Not stable
 *   Heap Sort      : Best O(n log n)  | Avg O(n log n) | Worst O(n log n) | Space O(1) | Not stable
 */
public class SortingAlgorithms {

    /** Counters reset at the start of each sort call. */
    public long comparisons;
    public long swaps;

    // -----------------------------------------------------------------------
    // 1. Insertion Sort — good for small or nearly-sorted data
    // -----------------------------------------------------------------------
    public void insertionSort(int[] arr) {
        comparisons = 0; swaps = 0;
        if (arr == null || arr.length < 2) return;
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j   = i - 1;
            while (j >= 0 && arr[j] > key) {
                comparisons++;
                arr[j + 1] = arr[j];
                swaps++;
                j--;
            }
            comparisons++; // the comparison that ended the loop
            arr[j + 1] = key;
        }
    }

    // -----------------------------------------------------------------------
    // 2. Merge Sort — guaranteed O(n log n), stable
    // -----------------------------------------------------------------------
    public void mergeSort(int[] arr) {
        comparisons = 0; swaps = 0;
        if (arr == null || arr.length < 2) return;
        mergeSort(arr, 0, arr.length - 1);
    }

    private void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int[] L = new int[mid - left + 1];
        int[] R = new int[right - mid];
        System.arraycopy(arr, left,    L, 0, L.length);
        System.arraycopy(arr, mid + 1, R, 0, R.length);
        int i = 0, j = 0, k = left;
        while (i < L.length && j < R.length) {
            comparisons++;
            if (L[i] <= R[j]) arr[k++] = L[i++];
            else { arr[k++] = R[j++]; swaps++; }
        }
        while (i < L.length) arr[k++] = L[i++];
        while (j < R.length) arr[k++] = R[j++];
    }

    // -----------------------------------------------------------------------
    // 3. Quick Sort — fastest in practice on random data
    // -----------------------------------------------------------------------
    public void quickSort(int[] arr) {
        comparisons = 0; swaps = 0;
        if (arr == null || arr.length < 2) return;
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        // Median-of-three pivot to reduce worst-case likelihood
        int mid = low + (high - low) / 2;
        if (arr[mid]  < arr[low])  swap(arr, low, mid);
        if (arr[high] < arr[low])  swap(arr, low, high);
        if (arr[mid]  < arr[high]) swap(arr, mid, high);
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            comparisons++;
            if (arr[j] <= pivot) swap(arr, ++i, j);
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // -----------------------------------------------------------------------
    // 4. Heap Sort — O(n log n) worst case, O(1) space
    // -----------------------------------------------------------------------
    public void heapSort(int[] arr) {
        comparisons = 0; swaps = 0;
        if (arr == null || arr.length < 2) return;
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i);
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private void heapify(int[] arr, int n, int i) {
        int largest = i, left = 2*i+1, right = 2*i+2;
        if (left  < n) { comparisons++; if (arr[left]  > arr[largest]) largest = left;  }
        if (right < n) { comparisons++; if (arr[right] > arr[largest]) largest = right; }
        if (largest != i) { swap(arr, i, largest); heapify(arr, n, largest); }
    }

    // -----------------------------------------------------------------------
    // Utility
    // -----------------------------------------------------------------------
    private void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
        swaps++;
    }

    /** Returns true if arr is sorted in non-decreasing order. */
    public static boolean isSorted(int[] arr) {
        if (arr == null || arr.length < 2) return true;
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i] > arr[i + 1]) return false;
        return true;
    }
}
