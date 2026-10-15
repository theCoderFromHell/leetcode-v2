package medium;

import java.util.Stack;

// https://leetcode.com/problems/min-stack/
public class MinStack {
    Stack<Integer> mainStack;
    Stack<Integer> helperStack;
    public MinStack() {
        mainStack =  new Stack<>();
        helperStack =  new Stack<>();
    }

    public void push(int val) {
        int current = helperStack.empty() ? val : val < helperStack.peek() ? val : helperStack.peek();
        helperStack.push(current);
        mainStack.push(val);
    }

    public void pop() {
        if(!mainStack.empty()) {
            mainStack.pop();
            helperStack.pop();
        }
    }

    public int top() {
        return !mainStack.empty() ? mainStack.peek() : Integer.valueOf(-1);
    }

    public int getMin() {
        return !mainStack.empty() ? helperStack.peek() : Integer.valueOf(-1);
    }

    /*
     * Revision Note — Min Stack (Medium)
     *
     * Pattern: Augmented stack — every entry carries the aggregate of everything BENEATH it.
     *
     * Key insight: the minimum can only change on push/pop, and a pop must restore the PREVIOUS
     * minimum. So store min-so-far alongside each value (here: a parallel helperStack pushed and
     * popped in lockstep) — getMin() is then just a peek, O(1) for every operation.
     *
     * Gotchas:
     *  - Push the running min on EVERY push, not only when it changes — a helper that skips
     *    non-minimum pushes must instead push on <= (not <), or popping one of two equal minimums
     *    loses the other.
     *  - Values span the full int range → never encode (val - min) in an int; it overflows.
     *  - Stack<Integer> is the legacy synchronized Vector; ArrayDeque is the modern choice.
     *
     * Complexity: O(1) per operation, O(n) space (one helper entry per element).
     *
     * Template:
     *   push(v): helper.push(helper.empty() ? v : min(v, helper.peek())); main.push(v)
     *   pop():   main.pop(); helper.pop()
     *   getMin(): helper.peek()
     *
     * Alternatives: one stack of int[]{val, minSoFar} pairs; or a single long-valued stack storing
     * 2*val - min when val < min (O(1) extra space, needs long to avoid overflow).
     */
    public static void main(String[] args) {
        MinStack M = new MinStack();
        M.push(-2);
        M.push(0);
        M.push(-3);
        System.out.println("Test 1: " + M.getMin() + " (Expected: -3)");
        M.pop();
        System.out.println("Test 2: " + M.top() + " (Expected: 0)");
        System.out.println("Test 3: " + M.getMin() + " (Expected: -2)");

        // duplicate minimum — popping one copy must keep the other
        M = new MinStack();
        M.push(0);
        M.push(1);
        M.push(0);
        M.pop();
        System.out.println("Test 4: " + M.getMin() + " (Expected: 0)");

        // minimum restored after popping a deeper minimum
        M = new MinStack();
        M.push(5);
        M.push(3);
        M.push(7);
        M.push(1);
        M.pop();
        System.out.println("Test 5: " + M.getMin() + " (Expected: 3)");
        M.pop();
        M.pop();
        System.out.println("Test 6: " + M.getMin() + " (Expected: 5)");

        // int extremes
        M = new MinStack();
        M.push(Integer.MAX_VALUE);
        M.push(Integer.MIN_VALUE);
        System.out.println("Test 7: " + M.getMin() + " (Expected: -2147483648)");
        M.pop();
        System.out.println("Test 8: " + M.getMin() + " (Expected: 2147483647)");
    }
}
