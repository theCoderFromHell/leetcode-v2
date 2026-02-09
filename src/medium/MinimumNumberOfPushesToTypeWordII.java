package medium;

import java.util.Arrays;

public class MinimumNumberOfPushesToTypeWordII {
    public int minimumPushes(String word) {
        int[] frequency = new int[26];
        for (char c : word.toCharArray())
            frequency[c - 'a']++;
        Arrays.sort(frequency);
        int count = 0;
        int position = 0;
        for (int i = 25; i >= 0 && frequency[i] > 0; i--) {
            count += frequency[i] * (position / 8 + 1);
            position++;
        }
        return count;
    }

    public static void main(String[] args) {
        MinimumNumberOfPushesToTypeWordII M = new MinimumNumberOfPushesToTypeWordII();
        System.out.println("Test 1: " + M.minimumPushes("abcde") + " (Expected: 5)");
        System.out.println("Test 2: " + M.minimumPushes("xyzxyzxyzxyz") + " (Expected: 12)");
        System.out.println("Test 3: " + M.minimumPushes("aabbccddeeffgghhiiiiii") + " (Expected: 24)");
        System.out.println("Test 4: " + M.minimumPushes("a") + " (Expected: 1)");
        System.out.println("Test 5: " + M.minimumPushes("aaaaaaaa") + " (Expected: 8)");
        System.out.println("Test 6: " + M.minimumPushes("abcdefghijklmnopqrstuvwxyz") + " (Expected: 56)");
        System.out.println("Test 7: " + M.minimumPushes("aabbccddee") + " (Expected: 10)");
        System.out.println("Test 8: " + M.minimumPushes("aaabbbccc") + " (Expected: 9)");
    }
}
