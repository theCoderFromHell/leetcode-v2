package medium;

import java.util.ArrayDeque;

// https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
public class MinimumCostTreeFromLeafValues {
    public int mctFromLeafValues(int[] arr) {
        int size = arr.length;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int result = 0;
        for (int i = 0; i < size; i++) {
            while (!stack.isEmpty() && stack.peek() <= arr[i]) {
                int top = stack.pop();
                result += (!stack.isEmpty() && stack.peek() < arr[i]) ? (stack.peek() * top) : (arr[i] * top);
            }
            stack.push(arr[i]);
        }
        while (!stack.isEmpty() && stack.size() > 1) {
            int smaller = stack.pop();
            int larger = stack.pop();
            result += (smaller * larger);
            stack.push(larger);
        }
        return result;
    }

    /*
     * Revision Note — Minimum Cost Tree From Leaf Values (Medium)
     *
     * Pattern: Monotonic decreasing stack. Each value is deleted the moment a value >= it arrives,
     * paying value × (smaller of its two neighbours).
     *
     * Key insight: an internal node merges two ADJACENT groups, and afterwards only the group's max
     * matters. So building the tree = repeatedly delete a value x next to something >= x, paying
     * x × that neighbour. Every value except the max is deleted exactly once. Neighbours only grow
     * over time (a deleted neighbour is replaced by something bigger), so the cheapest moment to
     * delete x is as soon as it's smaller than both sides: smallest-first greedy, done in one pass
     * by the stack.
     *
     * Gotchas:
     *  - A popped value has TWO neighbours: the incoming arr[i] AND the new stack top. Pay with the
     *    smaller. Always using arr[i] was this session's bug: [3, 2, 5] gave 25 instead of 21.
     *  - After the pop the stack may be empty, so arr[i] is the only neighbour.
     *  - Pop on <=: equal values merge for x·x whichever goes first.
     *  - Leftovers form a decreasing stack, so each pairs with the value below it (its only
     *    neighbour left).
     *  - Max cost is 39 merges × 15 × 15 = 8775, so int is fine.
     *
     * Complexity: O(n) time (each value pushed and popped once), O(n) space.
     *
     * Template:
     *   for x in arr:
     *       while stack not empty and top <= x:
     *           mid = pop(); result += mid * (stack empty ? x : min(top, x))
     *       push(x)
     *   while size > 1: result += pop() * top
     *
     * Alternatives:
     *  - O(n²) greedy: repeatedly remove the global min, paying min × its smaller neighbour.
     *  - O(n³) interval DP (n ≤ 40 is sized for it):
     *    dp[i][j] = min over k of dp[i][k] + dp[k+1][j] + max(i..k) · max(k+1..j)
     */
    public static void main(String[] args) {
        MinimumCostTreeFromLeafValues M = new MinimumCostTreeFromLeafValues();

        // Test 1: LeetCode example 1 — 2 merges with 4 (its smaller neighbour), not with 6
        System.out.println("Test 1: " + M.mctFromLeafValues(new int[]{6, 2, 4}) + " (Expected: 32)");

        // Test 2: LeetCode example 2 — n = 2, one merge
        System.out.println("Test 2: " + M.mctFromLeafValues(new int[]{4, 11}) + " (Expected: 44)");

        // Test 3: strictly increasing — every value pops immediately, the stack never holds more than one
        System.out.println("Test 3: " + M.mctFromLeafValues(new int[]{1, 2, 3, 4, 5}) + " (Expected: 40)");

        // Test 4: all values equal — exercises the <= pop; any merge order costs 3 × 49
        System.out.println("Test 4: " + M.mctFromLeafValues(new int[]{7, 7, 7, 7}) + " (Expected: 147)");

        // Test 5: valley shape — pops cascade when the right side climbs back up
        System.out.println("Test 5: " + M.mctFromLeafValues(new int[]{8, 4, 2, 4, 8}) + " (Expected: 120)");

        // Test 6: max appears twice — the 1 must pay 1 × 9 (stack top), not 1 × 15 (incoming)
        System.out.println("Test 6: " + M.mctFromLeafValues(new int[]{2, 15, 3, 9, 1, 15, 4}) + " (Expected: 486)");

        // Test 7: popped value's LEFT neighbour is cheaper — 2 pays 2 × 3, not 2 × 5 (the bug: 25)
        System.out.println("Test 7: " + M.mctFromLeafValues(new int[]{3, 2, 5}) + " (Expected: 21)");
    }
}
