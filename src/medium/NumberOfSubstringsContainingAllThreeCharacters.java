package medium;

import java.util.Arrays;

public class NumberOfSubstringsContainingAllThreeCharacters {
    public int numberOfSubstrings(String s) {
        int size = s.length();
        int[] lastPosition = new int[3];
        Arrays.fill(lastPosition, -1);
        int count = 0;
        for (int i = 0; i < size; i++) {
            lastPosition[s.charAt(i) - 'a'] = i;
            count += 1 + Math.min(lastPosition[0], Math.min(lastPosition[1], lastPosition[2]));
        }
        return count;
    }

    public static void main(String[] args) {
        NumberOfSubstringsContainingAllThreeCharacters N = new NumberOfSubstringsContainingAllThreeCharacters();
        System.out.println(N.numberOfSubstrings("abcabc"));
        System.out.println(N.numberOfSubstrings("aaacb"));
    }
}
