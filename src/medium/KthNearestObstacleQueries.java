package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

// https://leetcode.com/problems/k-th-nearest-obstacle-queries/
public class KthNearestObstacleQueries {
    public int[] resultsArray(int[][] queries, int k) {
        int size = queries.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>(k, Comparator.reverseOrder());
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            int distance = Math.abs(queries[i][0]) + Math.abs(queries[i][1]);
            if (pq.size() == k && distance >= pq.peek()) {
                result[i] = pq.peek();
                continue;
            }
            if (pq.size() == k && distance < pq.peek())
                pq.poll();
            pq.add(distance);
            result[i] = (pq.size() < k) ? -1 : pq.peek();
        }
        return result;
    }

    /*
     * Revision Note — K-th Nearest Obstacle Queries (Medium)
     * Pattern: streaming k-th smallest — a max-heap capped at k elements, whose root IS the
     *          k-th nearest distance so far.
     * Key Insight: obstacles are only ever added, so the k-th nearest can only move closer. An
     *              obstacle farther than the current k-th can never become the answer later, which
     *              means the heap only has to hold the k nearest — and its root is the answer.
     * Gotchas:
     *   - The Wrong Answer submission evicted only when the new distance was closer, but ADDED it
     *     unconditionally. A farther obstacle then grew the heap past k, and peek() returned the
     *     farthest overall: Test 1 gave [10, 15, 15, 15, 15]. A bounded heap needs the add to be
     *     conditional too — reject when full and d >= root, or add and then poll while size > k.
     *   - -1 until k obstacles exist; with k > n every answer is -1.
     *   - Ties at the root are interchangeable, so `>=` rejecting them is fine.
     *   - Distance is Manhattan, |x| + |y|, and peaks at 2 * 10^9 < 2^31 - 1, so int holds it.
     *   - Re-sorting per query is O(n^2 log n); 2 * 10^5 queries need O(n log k).
     * Complexity: O(n log k) time — one offer / poll per query on a heap of at most k.
     *             O(k) space for the heap, plus the O(n) output.
     * Alternatives:
     *   - Add then trim: pq.add(d); if (pq.size() > k) pq.poll(); — same complexity, fewer branches.
     *   - Offline Fenwick tree over the compressed distances, finding the k-th by binary lifting:
     *     O(n log n), and still works if k changed per query, which a fixed-size heap cannot do.
     * Template:
     *   maxHeap
     *   for each query: d = |x| + |y|
     *       if heap.size() == k and d >= heap.peek(): answer = heap.peek(); continue
     *       if heap.size() == k: heap.poll()
     *       heap.add(d)
     *       answer = heap.size() < k ? -1 : heap.peek()
     */
    public static void main(String[] args) {
        KthNearestObstacleQueries K = new KthNearestObstacleQueries();

        // Test 1: the Wrong Answer case — the full heap must reject 15 (farther than its root 10);
        // adding it anyway grows the heap past k and peek() returns 15
        System.out.println("Test 1: " + Arrays.toString(K.resultsArray(new int[][]{{-6, 4}, {7, 8}, {-2, -1}, {1, -9}, {-9, 4}}, 1)) + " (Expected: [10, 10, 3, 3, 3])");

        // Test 2: LeetCode example 1
        System.out.println("Test 2: " + Arrays.toString(K.resultsArray(new int[][]{{1, 2}, {3, 4}, {2, 3}, {-3, 0}}, 2)) + " (Expected: [-1, 7, 5, 3])");

        // Test 3: LeetCode example 2
        System.out.println("Test 3: " + Arrays.toString(K.resultsArray(new int[][]{{5, 5}, {4, 4}, {3, 3}}, 1)) + " (Expected: [10, 8, 6])");

        // Test 4: fewer obstacles than k throughout — every answer is -1
        System.out.println("Test 4: " + Arrays.toString(K.resultsArray(new int[][]{{1, 1}, {2, 2}}, 3)) + " (Expected: [-1, -1])");

        // Test 5: ties at the k-th distance — the same unbounded-heap bug returned 4 at the last query
        System.out.println("Test 5: " + Arrays.toString(K.resultsArray(new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {2, 2}}, 2)) + " (Expected: [-1, 1, 1, 1, 1])");

        // Test 6: the maximum distance 2 * 10^9 still fits in int (2^31 - 1 is about 2.147 * 10^9)
        System.out.println("Test 6: " + Arrays.toString(K.resultsArray(new int[][]{{1000000000, 1000000000}, {-1000000000, -1000000000}, {1, 1}}, 2)) + " (Expected: [-1, 2000000000, 2000000000])");

        // Test 7: 200000 queries with strictly decreasing distances, k = 100000 — every query evicts,
        // so re-sorting per query would time out; O(n log k) is fine. Prints the last result only
        int[][] large = new int[200000][];
        for (int i = 0; i < large.length; i++)
            large[i] = new int[]{large.length - 1 - i, 0};
        int[] result = K.resultsArray(large, 100000);
        System.out.println("Test 7: " + result[result.length - 1] + " (Expected: 99999)");
    }
}
