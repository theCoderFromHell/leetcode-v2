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

    public static void main(String[] args) {
        FindTheSubstringWithMaximumCost F = new FindTheSubstringWithMaximumCost();
        System.out.println(F.maximumCostSubstring("adaa", "d", new int[]{-1000}));          // 2  ("aa": 1+1)
        System.out.println(F.maximumCostSubstring("abc", "abc", new int[]{-1, -1, -1}));    // 0  (empty substring)
        System.out.println(F.maximumCostSubstring("abc", "", new int[]{}));                  // 6  (1+2+3)
        System.out.println(F.maximumCostSubstring("z", "z", new int[]{100}));               // 100 (single char override)
        System.out.println(F.maximumCostSubstring("a", "a", new int[]{-5}));                // 0  (negative, take empty)
    }
}
