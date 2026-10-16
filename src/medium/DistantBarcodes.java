package medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

// https://leetcode.com/problems/distant-barcodes/
public class DistantBarcodes {
    public int[] rearrangeBarcodes(int[] barcodes) {
        int size = barcodes.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o2[1], o1[1]));
        HashMap<Integer,Integer> codes = new HashMap<>();
        for (int i = 0; i < size; i++) {
            codes.put(barcodes[i], codes.getOrDefault(barcodes[i], 0) + 1);
        }
        for (int code : codes.keySet())
            pq.add(new int[]{code, codes.get(code)});
        int[] result = new int[size];
        int index = 0;
        while (index < size) {
            int[] first = pq.poll();
            result[index++] = first[0];
            if (index == size)
                break;
            int[] second = pq.poll();
            result[index++] = second[0];

            if (first[1] > 1)
                pq.add(new int[]{first[0], first[1] - 1});
            if (second[1] > 1)
                pq.add(new int[]{second[0], second[1] - 1});
        }
        return result;
    }

    /*
     * Revision Note — Distant Barcodes (Medium)
     *
     * Pattern: Max-heap by frequency, emit two most frequent per step
     *
     * Key Insight: Poll the two most FREQUENT remaining codes, emit both, decrement and
     * re-push whatever still has count left. Taking two at a time guarantees the pair placed
     * adjacently are different codes, and always taking the largest two preserves the
     * invariant maxCount <= ceil(remaining/2) — which is exactly the condition that makes an
     * answer possible, so the heap can never run dry early.
     *
     * Gotchas:
     * - The heap holds int[]{code, count}. Ordering must use [1] (count), NOT [0] (code).
     *   Both slots are ints so the compiler cannot help; sorting by code starves the most
     *   frequent value until nothing else remains, then poll() returns null -> NPE
     * - Comparator.comparingInt(o -> o[0]).reversed() does NOT compile for int[]: chaining
     *   .reversed() breaks target-type inference and o falls back to Object. Either write
     *   (int[] o) explicitly, or use a plain lambda with Integer.compare
     * - Integer.compare over b - a: no overflow risk here (values <= 1e4) but the right habit
     * - The `if (index == size) break;` after the FIRST emit is what handles odd lengths;
     *   without it the second poll runs past the end
     * - Same problem as 767 Reorganize String with ints instead of chars
     *
     * Template:
     *   count frequencies into a map
     *   push {code, count} into a max-heap ordered by count
     *   while index < n:
     *     a = poll(); result[index++] = a.code
     *     if index == n: break
     *     b = poll(); result[index++] = b.code
     *     if a.count > 1: push {a.code, a.count-1}
     *     if b.count > 1: push {b.code, b.count-1}
     *
     * O(n) alternative: values are bounded by 1e4, so count into an int[10001], then fill
     * even indices 0,2,4,... in descending-count order and wrap to odd indices 1,3,5,...
     * The most frequent value fits in the even slots alone precisely because of the
     * maxCount <= ceil(n/2) guarantee. No heap, no boxing.
     */
    // any arrangement is accepted, so validate the properties rather than a fixed array
    private static boolean isValid(int[] original, int[] result) {
        if (result == null || result.length != original.length) return false;
        for (int i = 1; i < result.length; i++)
            if (result[i] == result[i - 1]) return false;           // no two adjacent equal
        int[] a = original.clone(), b = result.clone();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);                        // same multiset
    }

    public static void main(String[] args) {
        DistantBarcodes D = new DistantBarcodes();

        int[] t1 = {1, 1, 1, 2, 2, 2};
        System.out.println("Test 1: " + isValid(t1, D.rearrangeBarcodes(t1)) + " (Expected: true)");

        int[] t2 = {1, 1, 1, 1, 2, 2, 3, 3};
        System.out.println("Test 2: " + isValid(t2, D.rearrangeBarcodes(t2)) + " (Expected: true)");

        int[] t3 = {1};
        System.out.println("Test 3: " + isValid(t3, D.rearrangeBarcodes(t3)) + " (Expected: true)"); // single element

        int[] t4 = {1, 2};
        System.out.println("Test 4: " + isValid(t4, D.rearrangeBarcodes(t4)) + " (Expected: true)");

        int[] t5 = {1, 1, 2};
        System.out.println("Test 5: " + isValid(t5, D.rearrangeBarcodes(t5)) + " (Expected: true)"); // odd length, max count = ceil(n/2)

        int[] t6 = {2, 2, 2, 2, 2, 1, 3, 4, 5};
        System.out.println("Test 6: " + isValid(t6, D.rearrangeBarcodes(t6)) + " (Expected: true)"); // one value at exactly ceil(n/2)
    }
}
