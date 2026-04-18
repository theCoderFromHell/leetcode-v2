package medium;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

// https://leetcode.com/problems/lexicographically-smallest-string-after-applying-operations/
public class LexicographicallySmallestStringAfterApplyingOperations {
    public String findLexSmallestString(String s, int a, int b) {
        int size = s.length();
        Queue<String> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        String result = s;
        queue.add(s);
        visited.add(s);
        while (!queue.isEmpty()) {
            String str = queue.poll();
            String afterAdd = applyAdd(str, size, a);
            String afterRotate = applyRotate(str, size, b);
            if (afterAdd.compareTo(result) < 0)
                result = afterAdd;
            if (afterRotate.compareTo(result) < 0)
                result = afterRotate;
            if (!visited.contains(afterAdd)) {
                visited.add(afterAdd);
                queue.add(afterAdd);
            }
            if (!visited.contains(afterRotate)) {
                visited.add(afterRotate);
                queue.add(afterRotate);
            }
        }
        return result;
    }

    private String applyRotate(String str, int size, int b) {
        int diff = size - b;
        StringBuilder sb = new StringBuilder(str);
        reverse(sb, 0, diff-1);
        reverse(sb, diff, size-1);
        reverse(sb, 0, size-1);
        return sb.toString();
    }

    private void reverse(StringBuilder sb, int start, int end) {
        while (start < end) {
            char c = sb.charAt(start);
            sb.setCharAt(start, sb.charAt(end));
            sb.setCharAt(end, c);
            start++;
            end--;
        }
    }

    private String applyAdd(String str, int size, int a) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 1; i < size; i += 2) {
            int value = ((sb.charAt(i) - '0') + a) % 10;
            sb.setCharAt(i, (char) (value + '0'));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LexicographicallySmallestStringAfterApplyingOperations L = new LexicographicallySmallestStringAfterApplyingOperations();
        System.out.println(L.findLexSmallestString("5525", 9, 2));    // "2050"
        System.out.println(L.findLexSmallestString("74", 5, 1));       // "24"
        System.out.println(L.findLexSmallestString("0011", 4, 2));     // "0011"
        System.out.println(L.findLexSmallestString("43987654", 7, 3)); // "00553311"
    }
}

/*
 * Revision Note — Lexicographically Smallest String After Applying Operations (Medium)
 *
 * Pattern: BFS on implicit graph of string states
 *
 * Key Insight: The two operations (add to odd indices, rotate right) form a finite cyclic
 * state space — at most (n/gcd(n,b)) * (10/gcd(a,10)) distinct strings are reachable.
 * BFS + visited set explores all of them; track the lexicographic minimum throughout.
 *
 * Gotchas:
 * - StringBuilder must be initialised with the source string — new StringBuilder() gives
 *   empty string and silently produces wrong results (no compile/runtime error)
 * - Add visited.add(s) before the loop to prevent re-enqueuing the start state
 * - Three-reverse trick for rotate: reverse[0,n-b-1] + reverse[n-b,n-1] + reverse[0,n-1]
 *
 * Template:
 *   queue.add(s); visited.add(s);
 *   while (!queue.isEmpty()) {
 *       String curr = queue.poll();
 *       for (String next : { applyAdd(curr), applyRotate(curr) }) {
 *           if (next.compareTo(result) < 0) result = next;
 *           if (visited.add(next)) queue.add(next);
 *       }
 *   }
 */
