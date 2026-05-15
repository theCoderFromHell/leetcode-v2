package medium;

import java.util.Comparator;
import java.util.PriorityQueue;

// https://leetcode.com/problems/find-polygon-with-the-largest-perimeter/
public class FindPolygonWithTheLargestPerimeter {
    public long largestPerimeter(int[] nums) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        long sum = 0;
        for (int number : nums) {
            sum += number;
            pq.add(number);
        }
        while (!pq.isEmpty()) {
            int largest = pq.poll();
            sum -= largest;
            if (largest < sum)
                return (largest + sum);
        }
        return -1;
    }

    /*
     * Revision Note — Find Polygon With The Largest Perimeter (Medium)
     *
     * Pattern: Greedy — sort descending, try largest side first
     *
     * Key Insight: A valid polygon requires the largest side to be strictly less
     * than the sum of all remaining sides. Sort descending and greedily try the
     * largest candidate — the first valid one gives the maximum perimeter.
     *
     * Gotchas:
     * - Return type is long — sum of sides can overflow int
     * - Must be strictly less than (not <=)
     * - If no valid polygon exists, return -1
     *
     * Template:
     *   sort descending (or use prefix sums ascending)
     *   prefixSum = totalSum
     *   for each largest candidate (high to low):
     *       prefixSum -= candidate
     *       if candidate < prefixSum: return candidate + prefixSum
     *   return -1
     */

    public static void main(String[] args) {
        FindPolygonWithTheLargestPerimeter F = new FindPolygonWithTheLargestPerimeter();
        System.out.println(F.largestPerimeter(new int[]{5, 5, 5}));              // 15
        System.out.println(F.largestPerimeter(new int[]{1, 12, 1, 2, 5, 50, 3})); // 12
        System.out.println(F.largestPerimeter(new int[]{5, 5, 50}));             // -1 — no valid polygon
        System.out.println(F.largestPerimeter(new int[]{1, 1, 1}));              // 3
        System.out.println(F.largestPerimeter(new int[]{1, 2, 3, 4, 5, 6}));    // 21
    }
}
