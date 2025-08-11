package medium;

import java.util.Arrays;

public class DetermineIfTwoStringsAreClose {
    public boolean closeStrings(String word1, String word2) {
        int length1 = word1.length();
        int length2 = word2.length();
        int[] hash1 = new int[26];
        int[] hash2 = new int[26];
        int charIndex;
        for (int i = 0; i < length1; i++) {
            charIndex = word1.charAt(i) - 'a';
            hash1[charIndex]++;
        }
        for (int i = 0; i < length2; i++) {
            charIndex = word2.charAt(i) - 'a';
            hash2[charIndex]++;
        }
        for (int i = 0; i < 26; i++) {
            if ((hash1[i] > 0 && hash2[i] == 0) || (hash2[i] > 0 && hash1[i] == 0))
                return false;
        }
        Arrays.sort(hash1);
        Arrays.sort(hash2);
        for (int i = 0; i < 26; i++) {
            if (hash1[i] != hash2[i])
                return false;
        }
        return true;
    }
}
