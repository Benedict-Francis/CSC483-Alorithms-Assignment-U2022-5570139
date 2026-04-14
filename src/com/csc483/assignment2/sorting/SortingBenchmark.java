package com.csc483.assignment2.sorting;

/**
 * Course  : CSC 483.1 - Algorithms Analysis and Design
 * Student : ONYEMACHI BENEDICT
 * Matric  : U2022/5570139
 *
 * SortingBenchmark.java (Question 2)
 * Compares Insertion, Merge, Quick, and Heap Sort across
 * multiple dataset types and sizes. Reports time, comparisons,
 * swaps, and standard deviation for each combination.
 */
public class SortingBenchmark {

    private static final int[] SIZES = {100, 1_000, 10_000, 100_000};
    private static final int   RUNS  = 5;

    public static void main(String[] args) {
        String[] dataTypes = {"Random", "Sorted", "ReverseSorted", "NearlySorted", "ManyDuplicates"};
        String[] algNames  = {"Insertion", "Merge", "Quick", "Heap"};

        for (String dtype : dataTypes) {
            System.out.println("\n" + "=".repeat(80));
            System.out.printf("SORTING ALGORITHMS — %s DATA%n", dtype.toUpperCase());
            System.out.println("=".repeat(80));
            System.out.printf("%-12s %-12s %12s %16s %12s %12s%n",
                    "Size", "Algorithm", "Time (ms)", "Comparisons", "Swaps", "StdDev(ms)");
            System.out.println("-".repeat(80));

            for (int size : SIZES) {
                int[] base = generateData(dtype, size);

                for (String alg : algNames) {
                    // Insertion Sort is too slow for n=100,000 — skip
                    if (alg.equals("Insertion") && size == 100_000) {
                        System.out.printf("%-12s %-12s %12s %16s %12s %12s%n",
                                fmt(size), alg, "SKIPPED", "-", "-", "-");
                        continue;
                    }

                    double[] times = new double[RUNS];
                    long totalComp = 0, totalSwap = 0;
                    SortingAlgorithms sorter = new SortingAlgorithms();

                    for (int r = 0; r < RUNS; r++) {
                        int[] arr = DataGenerator.copy(base);
                        long start = System.nanoTime();
                        runSort(sorter, alg, arr);
                        times[r] = (System.nanoTime() - start) / 1_000_000.0;
                        totalComp += sorter.comparisons;
                        totalSwap += sorter.swaps;
                        if (r == 0 && !SortingAlgorithms.isSorted(arr))
                            System.err.println("ERROR: " + alg + " failed to sort!");
                    }

                    double mean   = mean(times);
                    double stdDev = stdDev(times, mean);
                    System.out.printf("%-12s %-12s %12.3f %16s %12s %12.3f%n",
                            fmt(size), alg, mean,
                            fmtLong(totalComp / RUNS),
                            fmtLong(totalSwap / RUNS),
                            stdDev);
                }
                System.out.println();
            }
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("CONCLUSIONS:");
        System.out.println("  - Quick Sort is fastest on average for random data");
        System.out.println("  - Insertion Sort is only competitive for n <= 1,000");
        System.out.println("  - Merge Sort gives consistent O(n log n) regardless of input order");
        System.out.println("  - Heap Sort uses O(1) space but is slower than Quick Sort in practice");
        System.out.println("  - For nearly-sorted data, Insertion Sort outperforms Heap and Merge");
        System.out.println("=".repeat(80));
    }

    private static int[] generateData(String type, int size) {
        switch (type) {
            case "Sorted":         return DataGenerator.sorted(size);
            case "ReverseSorted":  return DataGenerator.reverseSorted(size);
            case "NearlySorted":   return DataGenerator.nearlySorted(size);
            case "ManyDuplicates": return DataGenerator.manyDuplicates(size);
            default:               return DataGenerator.random(size);
        }
    }

    private static void runSort(SortingAlgorithms s, String alg, int[] arr) {
        switch (alg) {
            case "Insertion": s.insertionSort(arr); break;
            case "Merge":     s.mergeSort(arr);     break;
            case "Quick":     s.quickSort(arr);     break;
            case "Heap":      s.heapSort(arr);      break;
        }
    }

    private static double mean(double[] v) {
        double sum = 0; for (double x : v) sum += x; return sum / v.length;
    }

    private static double stdDev(double[] v, double mean) {
        double sum = 0;
        for (double x : v) sum += (x - mean) * (x - mean);
        return Math.sqrt(sum / v.length);
    }

    private static String fmt(int n)     { return String.format("%,d", n); }
    private static String fmtLong(long n) { return String.format("%,d", n); }
}
