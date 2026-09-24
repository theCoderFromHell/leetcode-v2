package medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// https://leetcode.com/problems/valid-binary-strings-with-cost-limit/
public class ValidBinaryStringsWithCostLimit {
    public List<String> generateValidStrings(int n, int k) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        generate(sb, 0, n, k, result, 0, 0);
        return result;
    }

    private void generate(StringBuilder sb, int index, int n, int k, List<String> result,
                          int previous, int currCost) {
        if (currCost > k)
            return;
        if (index == n) {
            result.add(new String(sb));
            return;
        }
        sb.append("0");
        generate(sb, index + 1, n, k, result, 0, currCost);
        sb.deleteCharAt(sb.length() - 1);
        if (previous == 0 && currCost + index <= k) {
            sb.append("1");
            generate(sb, index + 1, n, k, result, 1, currCost + index);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /*
     * Pattern: backtracking over string positions, with an implicit "last char"
     * flag to enforce a no-two-consecutive-ones constraint.
     * Key Insight: the cost of a '1' at position `index` IS `index` — the loop
     * index you're already tracking doubles as the cost contribution, so no
     * separate cost table or bit-counting is needed.
     * Gotchas:
     * - Always try '0' first (unconditional); only try '1' when previous == 0
     *   AND currCost + index <= k — check the bound BEFORE recursing, not after,
     *   so invalid branches are pruned instead of explored and discarded.
     * - Must backtrack (deleteCharAt) after each branch to reuse the StringBuilder.
     * - The `currCost > k` guard at the top is unreachable given k >= 0 — every
     *   call site already guarantees currCost <= k before recursing.
     * Template:
     *   generate(sb, index, previous, cost):
     *     if index == n: emit sb; return
     *     sb += '0'; generate(index+1, 0, cost); sb.pop()
     *     if previous == 0 and cost+index <= k:
     *       sb += '1'; generate(index+1, 1, cost+index); sb.pop()
     */
    public static void main(String[] args) {
        ValidBinaryStringsWithCostLimit V = new ValidBinaryStringsWithCostLimit();
        List<String> t1 = V.generateValidStrings(3, 1);
        Collections.sort(t1);
        System.out.println("Test 1: " + t1 + " (Expected: [000, 010, 100])");

        List<String> t2 = V.generateValidStrings(1, 0);
        Collections.sort(t2);
        System.out.println("Test 2: " + t2 + " (Expected: [0, 1])");

        List<String> t3 = V.generateValidStrings(2, 0);
        Collections.sort(t3);
        System.out.println("Test 3: " + t3 + " (Expected: [00, 10])");

        List<String> t4 = V.generateValidStrings(3, 3);
        Collections.sort(t4);
        System.out.println("Test 4: " + t4 + " (Expected: [000, 001, 010, 100, 101])");

        int size = V.generateValidStrings(12, 66).size();
        System.out.println("Test 5: " + size + " (Expected: 377) -- n=12, k=max: unconstrained count = Fib(n+2)");
    }
}
