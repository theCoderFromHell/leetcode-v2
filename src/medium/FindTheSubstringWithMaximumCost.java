package medium;

// https://leetcode.com/problems/find-the-substring-with-maximum-cost/
public class FindTheSubstringWithMaximumCost {
    public int maximumCostSubstring(String s, String chars, int[] vals) {
        int[] charValues = new int[26];
        for (int i = 0; i < 26; i++)
            charValues[i] = i + 1;
        char[] charArray = chars.toCharArray();
        int size = charArray.length;
        for (int i = 0; i < size; i++)
            charValues[charArray[i] - 'a'] = vals[i];
        int maxSum = 0;
        int curr = 0;
        for (char c : s.toCharArray()) {
            curr += charValues[c - 'a'];
            if (curr < 0)
                curr = 0;
            else maxSum = Math.max(maxSum, curr);
        }
        return maxSum;
    }

    /*
     * Revision Note — Find the Substring With Maximum Cost (Medium)
     *
     * Pattern: Kadane's algorithm on a derived numeric sequence
     *
     * Key Insight: Map each character to its integer value (override if in chars,
     * else 1-indexed alphabet position), then find the maximum subarray sum —
     * maxSum starts at 0 to allow the empty substring (cost 0) as a valid answer.
     *
     * Gotchas:
     * - Initialize maxSum = 0, not Integer.MIN_VALUE — empty substring is valid
     * - Reset curr = 0 (not min) when it goes negative — standard Kadane's
     * - No need to materialise the value array; inline the lookup directly into Kadane's loop
     *
     * Template:
     *   int[] charValues = new int[26];
     *   for (int i = 0; i < 26; i++) charValues[i] = i + 1;
     *   for (int i = 0; i < chars.length(); i++) charValues[chars.charAt(i)-'a'] = vals[i];
     *   int maxSum = 0, curr = 0;
     *   for (char c : s.toCharArray()) {
     *       curr += charValues[c - 'a'];
     *       if (curr < 0) curr = 0;
     *       else maxSum = Math.max(maxSum, curr);
     *   }
     *   return maxSum;
     */
    public static void main(String[] args) {
        FindTheSubstringWithMaximumCost F = new FindTheSubstringWithMaximumCost();
        System.out.println(F.maximumCostSubstring("adaa", "d", new int[]{-1000}));          // 2  ("aa": 1+1)
        System.out.println(F.maximumCostSubstring("abc", "abc", new int[]{-1, -1, -1}));    // 0  (empty substring)
        System.out.println(F.maximumCostSubstring("abc", "", new int[]{}));                  // 6  (1+2+3)
        System.out.println(F.maximumCostSubstring("z", "z", new int[]{100}));               // 100 (single char override)
        System.out.println(F.maximumCostSubstring("a", "a", new int[]{-5}));                // 0  (negative, take empty)
    }
}
