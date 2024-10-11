package medium;

import java.util.HashMap;

public class MaximumProductOfWordLengths {
    public int maxProduct(String[] words) {
        int N = words.length;
        int result = 0;
        HashMap<Integer, int[]> charMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String word = words[i];
            int length = word.length();
            int[] hash = new int[26];
            for (int j = 0; j < length; j++)
                hash[word.charAt(j) - 'a'] = 1;
            charMap.put(i, hash);
        }
        for (int i = 0; i < N; i++) {
            int[] hash1 = charMap.get(i);
            for (int j = i+1; j < N; j++) {
                int[] hash2 = charMap.get(j);
                if (noCommonChar(hash1, hash2))
                    result = Math.max(result, words[i].length() * words[j].length());
            }
        }
        return result;
    }

    private boolean noCommonChar(int[] hash1, int[] hash2) {
        for (int i = 0; i < 26; i++) {
            if (hash1[i] == 1 && hash2[i] == 1)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        MaximumProductOfWordLengths M = new MaximumProductOfWordLengths();
        System.out.println(M.maxProduct(new String[]{"abcw","baz","foo","bar","xtfn","abcdef"}));
        System.out.println(M.maxProduct(new String[]{"a","ab","abc","d","cd","bcd","abcd"}));
        System.out.println(M.maxProduct(new String[]{"a","aa","aaa","aaaa"}));
        //System.out.println(M.maxProduct(new String[]{}));

    }
}
