package medium;

// https://leetcode.com/problems/clumsy-factorial/
public class ClumsyFactorial {
    public int clumsy(int n) {
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;
        if (n == 3)
            return 6;
        if (n == 4)
            return 7;
        int value = n * (n-1);
        value /= (n-2);
        value += (n-3);
        return value - clumsyHelper(n-4);
    }
    public int clumsyHelper(int n) {
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;
        if (n == 3)
            return 6;
        if (n == 4)
            return 5;
        int value = n * (n-1);
        value /= (n-2);
        value -= (n-3);
        return value + clumsyHelper(n-4);
    }

    /*
     * Revision Note — Clumsy Factorial (Medium)
     *
     * Pattern: Expression evaluation with precedence — `*` `/` bind each group of four
     * (m*(m-1)/(m-2)), so the expression is a sum of signed groups. Recursion over groups here;
     * the stack version (150's concept) needs no sign reasoning at all.
     *
     * Key insight: only the FIRST group starts positive. Every later group reads
     * "- m*(m-1)/(m-2) + (m-3)", so the tail is NOT -clumsy(m) — the "+ (m-3)" inside it must
     * stay positive. Give the tail its own function that returns the tail's magnitude:
     *   tail(m) = m*(m-1)/(m-2) - (m-3) + tail(m-4)      ← negating the tail flips BOTH signs
     *
     * Gotchas:
     *  - value - clumsy(n-4) negated the whole tail: wrong from n = 8 (4*3/2 + 1 became -7).
     *  - Inside the helper the recursive call is ADDED — subtracting it was wrong from n = 9.
     *  - Base cases: clumsy(1..4) = 1, 2, 6, 7; tail(1..4) = 1, 2, 6, 5 (4*3/2 - 1).
     *  - n*(n-1) <= 1e8 fits an int; recursion depth n/4 <= 2500 is safe.
     *  - Floor division == Java '/' here because every operand is positive.
     *
     * Complexity: O(n) time, O(n) recursion stack (n/4 frames).
     *
     * Template (stack, no sign bookkeeping):
     *   push n; for x = n-1 down to 1, op cycles * / + -:
     *       '*' → push(pop() * x)   '/' → push(pop() / x)   '+' → push(x)   '-' → push(-x)
     *   return sum(stack)
     *
     * Alternative: O(1) closed form for n > 4 — n % 4 == 0 → n + 1; 1 or 2 → n + 2; 3 → n - 1
     * (each "+ (m-3) - m*(m-1)/(m-2)" pair telescopes because m*(m-1)/(m-2) == m + 1 for m > 4).
     */
    public static void main(String[] args) {
        ClumsyFactorial C = new ClumsyFactorial();

        System.out.println("Test 1: " + C.clumsy(4) + " (Expected: 7)");
        System.out.println("Test 2: " + C.clumsy(10) + " (Expected: 12)");
        // base cases
        System.out.println("Test 3: " + C.clumsy(1) + " (Expected: 1)");
        System.out.println("Test 4: " + C.clumsy(2) + " (Expected: 2)");
        System.out.println("Test 5: " + C.clumsy(3) + " (Expected: 6)");
        // tail of 1 and of 3 numbers — a single negated product
        System.out.println("Test 6: " + C.clumsy(5) + " (Expected: 7)");
        System.out.println("Test 7: " + C.clumsy(7) + " (Expected: 6)");
        // tail of 4 numbers — guards "value - clumsy(n-4)" (gave 7)
        System.out.println("Test 8: " + C.clumsy(8) + " (Expected: 9)");
        // tail longer than one group — guards subtracting the helper's recursive call (gave 13)
        System.out.println("Test 9: " + C.clumsy(9) + " (Expected: 11)");
        // two full tail groups deep
        System.out.println("Test 10: " + C.clumsy(13) + " (Expected: 15)");
        // max n — n*(n-1) fits an int, recursion depth 2500
        System.out.println("Test 11: " + C.clumsy(10000) + " (Expected: 10001)");
    }
}
