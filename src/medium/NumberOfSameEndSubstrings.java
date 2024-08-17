package medium;

import java.util.HashMap;

public class NumberOfSameEndSubstrings {
    public int[] sameEndSubstringCount(String s, int[][] queries) {
        int N = s.length();
        int[][] count = new int[26][N];
        for (int i = 0; i < N; i++) {
            char current = s.charAt(i);
            for (int j = 0; j < 26; j++) {
                char c = (char)(j + 97);
                if (c == current)
                    count[c - 'a'][i] = (i == 0) ? 1 : count[c - 'a'][i-1]  + 1;
                else
                    count[c - 'a'][i]  = (i == 0) ? 0 : count[c - 'a'][i-1] ;
            }

        }
        int Q = queries.length;
        int[] result = new int[Q];
        int index = 0;
        for (int[] query : queries) {
            int low = query[0];
            int high = query[1];
            for (int j = 0; j < 26; j++) {
                char c = (char)(j + 97);
                int[] cumulativeCount = count[c - 'a'];
                int frequency = cumulativeCount[high] - (low == 0 ? 0 : cumulativeCount[low-1]);
                if (frequency == 1)
                    result[index] += 1;
                else
                    result[index] += (frequency * (frequency-1) / 2) + frequency;
            }
            index++;
        }
        return result;
    }

    public static void main(String[] args) {
        NumberOfSameEndSubstrings N = new NumberOfSameEndSubstrings();
        System.out.println(N.sameEndSubstringCount("abcaab", new int[][]{{0,0},{1,4},{2,5},{0,5}}));
    }
}
