package hard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// https://leetcode.com/problems/count-of-smaller-numbers-after-self/
public class CountOfSmallerNumbersAfterSelf {
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> result = new ArrayList<>();
        int size = nums.length;
        int OFFSET = 10000;
        int[] bit = new int[2*OFFSET + 2];
        for (int i = size-1; i >= 0; i--) {
            int index = nums[i] + OFFSET;
            int count = query(bit, index);
            result.add(count);
            update(bit, 2*OFFSET + 2, 1, index);
        }
        Collections.reverse(result);
        return result;
    }

    private void update(int[] bit, int size, int value, int index) {
        index++;
        while (index < size) {
            bit[index] += value;
            index += (index & -index);
        }
    }

    private int query(int[] bit, int index) {
        int count = 0;
        while (index >= 1) {
            count += bit[index];
            index -= (index & -index);
        }
        return count;
    }

    /*
     * Revision Note — Count of Smaller Numbers After Self (Hard)
     * Pattern: Value-indexed BIT (order-statistics), swept right to left
     * Key Insight: A BIT only knows what has already been inserted. Sweeping right to
     *              left makes "already inserted" mean exactly "to my right", so the
     *              answer is just a prefix-count of values strictly below nums[i].
     *              The BIT is a frequency array over VALUES; the stored numbers are
     *              counts, not sums.
     * Gotchas:
     *   - Query BEFORE insert, or you count yourself.
     *   - Values are negative, so shift by OFFSET=10^4 to get 0..20000; a BIT is
     *     1-indexed, so update() does index++ (0 would hang: 0 & -0 == 0).
     *   - update() shifts but query() does NOT — that asymmetry is what makes the
     *     count STRICTLY smaller. Stored position of v is (v+OFFSET)+1, so summing
     *     positions 1..(v+OFFSET) covers exactly the values < v. Don't "fix" it.
     *   - Build the result right-to-left then reverse; result.add(0, x) would be O(n^2).
     * Template:
     *   for i = n-1 down to 0:
     *       m = nums[i] + OFFSET
     *       result.add(query(bit, m))     // prefix over 1..m  => values < nums[i]
     *       update(bit, m)                // stores at m+1
     *   reverse(result)
     */
    public static void main(String[] args) {
        CountOfSmallerNumbersAfterSelf C = new CountOfSmallerNumbersAfterSelf();
        System.out.println("Test 1: " + C.countSmaller(new int[]{5, 2, 6, 1}) + " (Expected: [2, 1, 1, 0])");
        System.out.println("Test 2: " + C.countSmaller(new int[]{-1}) + " (Expected: [0])");
        System.out.println("Test 3: " + C.countSmaller(new int[]{-1, -1}) + " (Expected: [0, 0])");
        System.out.println("Test 4: " + C.countSmaller(new int[]{5, 4, 3, 2, 1}) + " (Expected: [4, 3, 2, 1, 0])");
        System.out.println("Test 5: " + C.countSmaller(new int[]{1, 2, 3, 4, 5}) + " (Expected: [0, 0, 0, 0, 0])");
        System.out.println("Test 6: " + C.countSmaller(new int[]{2, 2, 1, 2}) + " (Expected: [1, 1, 0, 0])");
        // extremes of the allowed range: -10^4 maps to BIT index 0, the value that would hang update()
        System.out.println("Test 7: " + C.countSmaller(new int[]{10000, -10000}) + " (Expected: [1, 0])");
        System.out.println("Test 8: " + C.countSmaller(new int[]{-10000, 10000, -10000}) + " (Expected: [0, 1, 0])");
    }
}
