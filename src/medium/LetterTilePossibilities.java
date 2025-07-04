package medium;

import java.util.Arrays;
import java.util.HashSet;

public class LetterTilePossibilities {
    int[] factorial = {1, 1, 2, 6, 24, 120, 720, 5040, 40320};
    public int numTilePossibilities(String tiles) {
        int size = tiles.length();
        HashSet<String> words = new HashSet<>();
        char[] chars = tiles.toCharArray();
        Arrays.sort(chars);
        tiles = new String(chars);
        return backtrack(tiles, size, 0, words, "") - 1;
    }

    private int backtrack(String tiles, int size, int index, HashSet<String> words, String current) {
        if (index == size) {
            if (!words.contains(current)) {
                words.add(current);
                return count(current);
            }
            return 0;
        }
        return backtrack(tiles, size, index+1, words, current)
                + backtrack(tiles, size, index+1, words, current + tiles.charAt(index));
    }

    private int count(String current) {
        int[] count = new int[26];
        int size = current.length();
        for (int i = 0; i < size; i++)
            count[current.charAt(i) - 'A']++;
        int fact = factorial[size];
        for (int i = 0; i < 26; i++) {
            if (count[i] > 1)
                fact /= factorial[count[i]];
        }
        return fact;
    }

    public static void main(String[] args) {
        LetterTilePossibilities L = new LetterTilePossibilities();
        System.out.println(L.numTilePossibilities("AAB"));
        System.out.println(L.numTilePossibilities("AAABBC"));
        System.out.println(L.numTilePossibilities("ABC"));
    }
}
