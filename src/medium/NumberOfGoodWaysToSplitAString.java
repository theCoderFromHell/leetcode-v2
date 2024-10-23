package medium;

public class NumberOfGoodWaysToSplitAString {
    public int numSplits(String s) {
        int length = s.length();
        int[] left = new int[length];
        int[] right = new int[length];
        boolean[] distinctChars = new boolean[26];
        char[] sChars = s.toCharArray();
        int currentDistinct = 0;
        for (int i = 0; i < length; i++) {
            if (!distinctChars[sChars[i] - 'a']) {
                currentDistinct++;
                distinctChars[sChars[i] - 'a'] = true;
            }
            left[i] = currentDistinct;
        }
        distinctChars = new boolean[26];
        currentDistinct = 0;
        for (int i = length-1; i >= 0; i--) {
            if (!distinctChars[sChars[i] - 'a']) {
                currentDistinct++;
                distinctChars[sChars[i] - 'a'] = true;
            }
            right[i] = currentDistinct;
        }
        int count = 0;
        for (int i = 1; i < length; i++) {
            if (left[i-1] == right[i])
                count++;
        }
        return count;
    }

    public static void main(String[] args) {
        NumberOfGoodWaysToSplitAString N = new NumberOfGoodWaysToSplitAString();
        System.out.println(N.numSplits("aacaba"));
        System.out.println(N.numSplits("abcd"));
    }
}
