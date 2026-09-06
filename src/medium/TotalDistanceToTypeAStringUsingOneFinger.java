package medium;

// https://leetcode.com/problems/total-distance-to-type-a-string-using-one-finger/
public class TotalDistanceToTypeAStringUsingOneFinger {
    int[][] keyboard = {
            {1,0}, {2,4}, {2,2}, {1,2}, {0,2}, {1,3}, {1,4}, {1,5}, {0,7}, {1,6},
            {1,7}, {1,8}, {2,6}, {2,5}, {0,8}, {0,9}, {0,0}, {0,3}, {1,1}, {0,4},
            {0,6}, {2,3}, {0,1}, {2,1}, {0,5}, {2,0}
    };

    public int totalDistance(String s) {
        int size = s.length();
        int prevX = 1, prevY = 0;
        int result = 0;
        for (int i = 0; i < size; i++) {
            result += (Math.abs(keyboard[s.charAt(i) - 'a'][0] - prevX) +  Math.abs(keyboard[s.charAt(i) - 'a'][1] - prevY));
            prevX = keyboard[s.charAt(i) - 'a'][0];
            prevY = keyboard[s.charAt(i) - 'a'][1];
        }
        return result;
    }

    /*
     * Revision Note — Total Distance to Type a String Using One Finger (Medium)
     * Pattern: Greedy simulation — walk through string, accumulate Manhattan distances
     * Key Insight: Total cost = sum of Manhattan distances between consecutive key positions.
     *              Finger starts at 'a' [1,0]; for each character, move to its grid position.
     * Gotchas:
     *   - Must subtract 'a' when indexing: keyboard[s.charAt(i) - 'a'], not keyboard[s.charAt(i)].
     *     Raw char is ASCII (97-122) — direct indexing throws AIOOB on a 26-element array.
     *   - keyboard[c] lookup is repeated 3 times per iteration; extract to int[] pos for clarity.
     *   - Starting position is 'a' = [1,0] on this QWERTY layout (row 1, col 0).
     */
    public static void main(String[] args) {
        TotalDistanceToTypeAStringUsingOneFinger T = new TotalDistanceToTypeAStringUsingOneFinger();

        // Test 1: same character — no movement after reaching key
        System.out.println("Test 1: " + T.totalDistance("aaa") + " (Expected: 0)");

        // Test 2: adjacent keys — 'a' to 's' is 1 step right
        System.out.println("Test 2: " + T.totalDistance("as") + " (Expected: 1)");

        // Test 3: 'a' [1,0] to 'q' [0,0] — 1 step up
        System.out.println("Test 3: " + T.totalDistance("aq") + " (Expected: 1)");

        // Test 4: 'a'→'q'→'m': a→q=1, q[0,0]→m[2,6]=|2|+|6|=8, total=9
        System.out.println("Test 4: " + T.totalDistance("aqm") + " (Expected: 9)");

        // Test 5: single character — distance from 'a' to 'p' [0,9]: |0-1|+|9-0|=10
        System.out.println("Test 5: " + T.totalDistance("p") + " (Expected: 10)");

        // Test 6: empty string
        System.out.println("Test 6: " + T.totalDistance("") + " (Expected: 0)");
    }
}
