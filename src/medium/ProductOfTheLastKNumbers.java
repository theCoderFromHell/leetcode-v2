package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// https://leetcode.com/problems/product-of-the-last-k-numbers/
public class ProductOfTheLastKNumbers {
    static class ProductOfNumbers {
        int count;
        int lastZeroIndex;
        HashMap<Integer,Integer> productAtIndex;
        public ProductOfNumbers() {
            this.count = 0;
            this.lastZeroIndex = -1;
            this.productAtIndex = new HashMap<>();
            productAtIndex.put(0, 1);
        }

        public void add(int num) {
            int index = ++count;
            if (num == 0) {
                lastZeroIndex = index;
                productAtIndex.put(index, 1);
            }
            else
                productAtIndex.put(index, productAtIndex.get(index-1) * num);
        }

        public int getProduct(int k) {
            if (lastZeroIndex > 0 && lastZeroIndex > count - k)
                return 0;
            int index = count;
            return productAtIndex.get(index) / productAtIndex.get(index - k);
        }
    }

    /*
     * Revision Note — Product of the Last K Numbers (Medium)
     *
     * Pattern: Prefix products over a data stream (the Min Stack idea — each entry stores the
     * aggregate of everything before it), so a query is one division: prefix[n] / prefix[n-k].
     *
     * Key insight: a 0 poisons every later prefix (all become 0 → 0/0). So on a 0, RESET the
     * running product to 1 and remember where the zero sits. A window that reaches back to the
     * last zero is 0 without computing; a window after it never needs anything before the zero.
     *
     * Gotchas:
     *  - Window covers the zero iff lastZeroIndex > count - k (1-based); == means the window
     *    starts right after it, so it is a normal division against the reset value 1.
     *  - Overflow: safe only because the reset bounds each prefix to one zero-free run, and
     *    the problem guarantees any contiguous product fits in an int.
     *  - Keys are 0..count — an ArrayList<Integer> does the job of the HashMap; a common variant
     *    clears the list on a 0 (re-seeded with 1) and returns 0 when k >= list.size().
     *
     * Complexity: add O(1), getProduct O(1); O(n) space.
     *
     * Template:
     *   add(x):  if x == 0 → lastZero = ++count; prefix[count] = 1
     *            else      → prefix[++count] = prefix[count-1] * x
     *   get(k):  lastZero > count - k ? 0 : prefix[count] / prefix[count-k]
     */
    public static void main(String[] args) {
        ProductOfNumbers P = new ProductOfNumbers();
        P.add(3);
        P.add(0);
        P.add(2);
        P.add(5);
        P.add(4);
        System.out.println("Test 1: " + P.getProduct(2) + " (Expected: 20)");
        System.out.println("Test 2: " + P.getProduct(3) + " (Expected: 40)");
        System.out.println("Test 3: " + P.getProduct(4) + " (Expected: 0)");
        P.add(8);
        System.out.println("Test 4: " + P.getProduct(2) + " (Expected: 32)");

        // window after a zero — was 0 / 0 before the reset
        P = new ProductOfNumbers();
        P.add(0);
        P.add(2);
        System.out.println("Test 5: " + P.getProduct(1) + " (Expected: 2)");

        // window starting right after the zero vs. one step further back onto it
        P = new ProductOfNumbers();
        P.add(2);
        P.add(0);
        P.add(3);
        P.add(4);
        System.out.println("Test 6: " + P.getProduct(2) + " (Expected: 12)");
        System.out.println("Test 7: " + P.getProduct(3) + " (Expected: 0)");

        // zero is the only number
        P = new ProductOfNumbers();
        P.add(0);
        System.out.println("Test 8: " + P.getProduct(1) + " (Expected: 0)");

        // several zeros — only the most recent one matters
        P = new ProductOfNumbers();
        P.add(0);
        P.add(5);
        P.add(0);
        P.add(7);
        System.out.println("Test 9: " + P.getProduct(1) + " (Expected: 7)");
        System.out.println("Test 10: " + P.getProduct(2) + " (Expected: 0)");

        // no zeros, k = all numbers, product near Integer.MAX_VALUE
        P = new ProductOfNumbers();
        P.add(100);
        P.add(100);
        P.add(100);
        P.add(100);
        P.add(21);
        System.out.println("Test 11: " + P.getProduct(5) + " (Expected: 2100000000)");
        System.out.println("Test 12: " + P.getProduct(1) + " (Expected: 21)");
    }
}
