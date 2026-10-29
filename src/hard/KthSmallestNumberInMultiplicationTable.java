package hard;

// https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/
public class KthSmallestNumberInMultiplicationTable {
    public int findKthNumber(int m, int n, int k) {
        int start = 1, end = m*n;
        int mid;
        while (start < end) {
            mid = start + (end - start)/2;
            if (valuesLessThanOrEqualTo(mid, m, n) >= k)
                end = mid;
            else
                start = mid+1;
        }
        return start;
    }

    private int valuesLessThanOrEqualTo(int value, int m, int n) {
        int count = 0;
        for (int i = 1; i <= m; i++)
            count += Math.min(value / i, n);
        return count;
    }

    /*
     * Revision Note — Kth Smallest Number in Multiplication Table (Hard)
     * Pattern: binary search on the VALUE + a counting subroutine — find the smallest v with
     *          count(entries <= v) >= k, without ever building or walking the table.
     * Key Insight: row i is i, 2i, ..., n*i, so exactly min(v / i, n) of its entries are <= v. That
     *              makes count(v) an O(m) formula, and since count never decreases as v grows,
     *              "count(v) >= k" is a false...true boundary that binary search finds in ~30 rounds.
     * Gotchas:
     *   - The first submission (a k-way heap merge over the m rows) was correct but TLE: k reaches
     *     m * n = 9 * 10^8 pops, about a minute for Test 6. Anything that produces the first k values
     *     one by one is Omega(k); with k this large, count instead of enumerate.
     *   - Never return early on count(v) == k. count is a staircase, flat between table values:
     *     in the 3 x 3 table count(4) = count(5) = 6, and 5 isn't in the table (Test 7). Only the
     *     LEFT end of a flat stretch is a real entry, so keep shrinking end = mid.
     *   - Duplicates can make count jump PAST k (count(1) = 1, count(2) = 3 for k = 2, Test 8), so
     *     the test must be >= k, not == k. Contrast 1300, where S(v) was strictly increasing and
     *     `sum == target` was a safe exit.
     *   - The answer is always a table entry: count(v - 1) < k <= count(v) means count went up at v,
     *     which only happens if some i * j == v.
     *   - int is safe: m * n <= 9 * 10^8 and count <= m * n.
     * Complexity: O(m log(mn)) time — ~30 rounds of an O(m) count. O(1) space.
     * Alternatives:
     *   - Staircase count from the bottom-left corner: O(m + n) per count, no per-row formula needed —
     *     the version for 378 (Kth Smallest Element in a Sorted Matrix).
     *   - k-way heap merge: O(k log m), fine only when k is small.
     *   - Same counting idea with two pointers instead of a formula: 719 (Find K-th Smallest Pair Distance).
     * Template:
     *   lo = 1, hi = m * n
     *   while lo < hi:
     *       mid = lo + (hi - lo) / 2
     *       if count(mid) >= k: hi = mid      // mid might be the answer — keep it
     *       else: lo = mid + 1
     *   return lo
     *   count(v) = sum over i in 1..m of min(v / i, n)
     */
    public static void main(String[] args) {
        KthSmallestNumberInMultiplicationTable K = new KthSmallestNumberInMultiplicationTable();

        // Test 1: LeetCode example 1 — sorted entries 1 2 2 3 3 4 6 6 9, the 5th is the second 3
        System.out.println("Test 1: " + K.findKthNumber(3, 3, 5) + " (Expected: 3)");

        // Test 2: LeetCode example 2 — k == m * n, the largest entry
        System.out.println("Test 2: " + K.findKthNumber(2, 3, 6) + " (Expected: 6)");

        // Test 3: 1 x 1 table — start == end == 1, the loop never runs
        System.out.println("Test 3: " + K.findKthNumber(1, 1, 1) + " (Expected: 1)");

        // Test 4: more rows than columns — the min(v / i, n) cap matters for the short rows
        System.out.println("Test 4: " + K.findKthNumber(5, 2, 7) + " (Expected: 5)");

        // Test 5: k == m * n — end = m * n must be inclusive
        System.out.println("Test 5: " + K.findKthNumber(4, 6, 24) + " (Expected: 24)");

        // Test 6: m = n = 3 * 10^4, k = m * n / 2 — the heap merge needed about a minute here (TLE);
        // the value binary search takes ~30 counts of O(m)
        System.out.println("Test 6: " + K.findKthNumber(30000, 30000, 450000000) + " (Expected: 168028638)");

        // Test 7: count(4) = count(5) = 6 — returning on count == k gives 5, which isn't in the table
        System.out.println("Test 7: " + K.findKthNumber(3, 3, 6) + " (Expected: 4)");

        // Test 8: count jumps from 1 to 3 at v = 2, never equal to k = 2 — the test must be >= k
        System.out.println("Test 8: " + K.findKthNumber(3, 3, 2) + " (Expected: 2)");
    }
}
