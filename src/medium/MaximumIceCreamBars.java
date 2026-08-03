package medium;

// https://leetcode.com/problems/maximum-ice-cream-bars/

public class MaximumIceCreamBars {
    public int maxIceCream(int[] costs, int coins) {
        int low = Integer.MAX_VALUE, high = Integer.MIN_VALUE;
        int size = costs.length;
        for (int i = 0; i < size; i++) {
            low = Math.min(low, costs[i]);
            high = Math.max(high, costs[i]);
        }
        int[] frequency = new int[high - low + 1];
        for (int i = 0; i < size; i++) {
            frequency[costs[i] - low]++;
        }
        int index = 0;
        for (int i = 0; i < high - low + 1; i++) {
            int freq = frequency[i];
            while (freq-- > 0) {
                costs[index++] = i + low;
            }
        }
        index = 0;
        int count = 0;
        while (index < size && coins > 0) {
            if (costs[index] <= coins) {
                coins -= costs[index++];
                count++;
            } else
                break;
        }
        return count;
    }

    /*
     * Pattern: Greedy + Counting Sort
     * Key Insight: Buying cheapest bars first maximises count. Counting sort
     *              (offset by min value) reconstructs costs[] in ascending order
     *              in O(n + range) without Arrays.sort().
     * Gotchas:
     *   - While loop needs `index < size` guard — without it, crashes when all
     *     bars are affordable (index overshoots array length while coins > 0).
     *   - Offset trick (freq index = cost - low) shrinks the freq array when
     *     values are clustered high, but adds reconstruction complexity.
     *   - Alternative: iterate freq array directly with canBuy = min(freq[price],
     *     coins/price) — simpler and avoids mutating the input array.
     */
    public static void main(String[] args) {
        MaximumIceCreamBars M = new MaximumIceCreamBars();
        System.out.println("Test 1: " + M.maxIceCream(new int[]{1, 3, 2, 4, 1}, 7) + " (Expected: 4)");
        System.out.println("Test 2: " + M.maxIceCream(new int[]{10, 6, 8, 7, 7, 8}, 5) + " (Expected: 0)");
        System.out.println("Test 3: " + M.maxIceCream(new int[]{1, 6, 3, 1, 2, 5}, 20) + " (Expected: 6)");
        System.out.println("Test 4: " + M.maxIceCream(new int[]{1}, 1) + " (Expected: 1)");
        System.out.println("Test 5: " + M.maxIceCream(new int[]{5}, 4) + " (Expected: 0)");
        System.out.println("Test 6: " + M.maxIceCream(new int[]{1, 1, 1, 1}, 2) + " (Expected: 2)");
    }
}
