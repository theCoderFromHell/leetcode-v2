package medium;

import java.util.ArrayDeque;

// https://leetcode.com/problems/count-collisions-on-a-road/
public class CountCollisionsOnARoad {
    public int countCollisions(String directions) {
        int size = directions.length();
        ArrayDeque<Character> stack = new ArrayDeque<>();
        int result = 0;
        for (int i = 0; i < size; i++) {
            char car = directions.charAt(i);
            if (stack.isEmpty() || car == 'R' || stack.peek() == 'L')
                stack.push(car);
            else {
                if (car == 'S') {
                    while (!stack.isEmpty() && stack.peek() == 'R') {
                        result++;
                        stack.pop();
                    }
                } else if (car == 'L') {
                    if (stack.peek() == 'S')
                        result++;
                    else if (stack.peek() == 'R')
                        result += 2;
                    stack.pop();
                    while (!stack.isEmpty() && stack.peek() == 'R') {
                        result ++;
                        stack.pop();
                    }
                }
                stack.push('S');
            }
        }
        return result;
    }

    /*
     * Revision Note — Count Collisions on a Road (Medium)
     *
     * Pattern: Collision stack (735 Asteroid Collision), except a crash leaves a stationary WRECK
     * instead of a survivor — and every car already driving into a wreck is doomed.
     *
     * Key insight: settle the doomed cars the moment the wreck forms. When an S arrives, or an L
     * hits anything, every R on top of the stack will crash into that new S: +1 each (+2 only for
     * the one R the L meets head-on). With that invariant — no R ever sits directly under an S —
     * the stack is always L…L S…S R…R, and nothing is left to sweep at the end.
     *
     * Gotchas:
     *  - Popping only ONE R on an L-R crash leaves the R's beneath it uncounted. An end-of-input
     *    sweep finds them only if nothing was pushed on top afterwards: RRLS, RRLR and RRLRL all
     *    came out one short.
     *  - +2 is for the first R only — the R's behind it hit a stopped car, +1 each (RRRL is 4, not 6).
     *  - while (stack.peek() == 'S') with pops inside throws EmptyStackException once it drains.
     *  - Leading L's and trailing R's escape untouched; S never scores on its own.
     *  - Each car contributes at most 1 (head-on = 2 cars, 2 points), so the answer is <= n: int.
     *
     * Complexity: O(n) time (each car pushed and popped at most once), O(n) space.
     *
     * Template:
     *   for car in directions:
     *       empty / car == R / top == L  → push car            ← escaped L's never collide
     *       car == S → while top == R: pop, +1;  push S
     *       car == L → +2 if top == R else +1 (top S); pop;  while top == R: pop, +1;  push S
     *
     * Alternatives (both O(n) time, O(1) space):
     *  - The stack is always L*S*R*, so two counters replace it: pendingR, and whether a wreck/S
     *    exists to the left. R → pendingR++;  S → +pendingR;  L → +pendingR + 1 if pendingR > 0,
     *    else +1 if a wreck exists.
     *  - Closed form: trim leading L's and trailing R's; every remaining moving car crashes exactly
     *    once and scores 1 → count the non-S cars in the middle.
     */
    public static void main(String[] args) {
        CountCollisionsOnARoad C = new CountCollisionsOnARoad();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + C.countCollisions("RLRSLL") + " (Expected: 5)");

        // Test 2: LeetCode example 2 — everyone drives away
        System.out.println("Test 2: " + C.countCollisions("LLRR") + " (Expected: 0)");

        // Test 3: single car — nothing to hit
        System.out.println("Test 3: " + C.countCollisions("R") + " (Expected: 0)");

        // Test 4: all stationary — S never scores alone; draining this stack once threw EmptyStackException
        System.out.println("Test 4: " + C.countCollisions("SSSS") + " (Expected: 0)");

        // Test 5: only the first R meets the L head-on (+2), the other two hit the wreck — += 2 per R gave 6
        System.out.println("Test 5: " + C.countCollisions("RRRL") + " (Expected: 4)");

        // Test 6: leading L's and trailing R's escape
        System.out.println("Test 6: " + C.countCollisions("LLRSLRR") + " (Expected: 2)");

        // Test 7: an S pushed over the wreck hid the R left beneath it — gave 2
        System.out.println("Test 7: " + C.countCollisions("RRLS") + " (Expected: 3)");

        // Test 8: a trailing R hid the R left beneath the wreck — gave 2
        System.out.println("Test 8: " + C.countCollisions("RRLR") + " (Expected: 3)");

        // Test 9: two separate head-on crashes, each with an R behind the first — gave 4
        System.out.println("Test 9: " + C.countCollisions("RRLRL") + " (Expected: 5)");
    }

    private int highestDigit(Integer a) {
        int highest = -1;
        while (a > 0) {
            highest = Math.max(highest, a% 10);
            a /= 10;
        }
        return highest;
    }
}
