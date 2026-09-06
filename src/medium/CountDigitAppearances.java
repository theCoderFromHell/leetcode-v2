package medium;

// https://leetcode.com/problems/count-digit-appearances/
public class CountDigitAppearances {
    public int countDigitOccurrences(int[] nums, int digit) {
        int size = nums.length;
        int result = 0;
        for (int i = 0; i < size; i++) {
            int number = nums[i];
            while (number > 0) {
                if (number % 10 == digit)
                    result++;
                number /= 10;
            }
        }
        return result;
    }

    /*
     * Revision Note — Count Digit Appearances (Medium)
     * Pattern: Digit extraction — repeated % 10 and /= 10
     * Key Insight: Extract digits one by one from the right; count matches against target digit.
     * Gotchas:
     *   - while (number > 0) skips the number 0 entirely — safe here because nums[i] >= 1.
     *     If 0 were a valid input, switch to do-while to guarantee at least one iteration.
     *   - digit=0 works correctly for internal zeros (e.g. 100 % 10 = 0 is caught before /= 10).
     *   - Negative numbers would be skipped; not an issue given constraints (nums[i] >= 1).
     */
    public static void main(String[] args) {
        CountDigitAppearances C = new CountDigitAppearances();

        // Test 1: digit appears once in each number
        System.out.println("Test 1: " + C.countDigitOccurrences(new int[]{11, 21, 31}, 1) + " (Expected: 4)");

        // Test 2: digit=0 with trailing and internal zeros
        System.out.println("Test 2: " + C.countDigitOccurrences(new int[]{10, 100, 1000}, 0) + " (Expected: 6)");

        // Test 3: digit not present in any number
        System.out.println("Test 3: " + C.countDigitOccurrences(new int[]{1, 2, 3, 4, 5}, 9) + " (Expected: 0)");

        // Test 4: single element, all digits match
        System.out.println("Test 4: " + C.countDigitOccurrences(new int[]{999999}, 9) + " (Expected: 6)");

        // Test 5: single element, digit appears once
        System.out.println("Test 5: " + C.countDigitOccurrences(new int[]{123456}, 3) + " (Expected: 1)");
    }
}
