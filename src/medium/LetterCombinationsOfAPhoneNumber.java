package medium;

import java.util.*;

public class LetterCombinationsOfAPhoneNumber {
    static Map<Character, ArrayList<Character>> map = Map.ofEntries(Map.entry('2', new ArrayList<>(Arrays.asList('a', 'b', 'c'))),
            Map.entry('3', new ArrayList<>(Arrays.asList('d', 'e', 'f'))),
            Map.entry('4', new ArrayList<>(Arrays.asList('g', 'h', 'i'))),
            Map.entry('5', new ArrayList<>(Arrays.asList('j', 'k', 'l'))),
            Map.entry('6', new ArrayList<>(Arrays.asList('m', 'n', 'o'))),
            Map.entry('7', new ArrayList<>(Arrays.asList('p', 'q', 'r', 's'))),
            Map.entry('8', new ArrayList<>(Arrays.asList('t', 'u', 'v'))),
            Map.entry('9', new ArrayList<>(Arrays.asList('w', 'x', 'y', 'z'))));
    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.isEmpty() || digits.isBlank())
            return result;
        int size = digits.length();
        add(digits.toCharArray(), 0, size, new char[size], result);
        return result;
    }

    private static void add(char[] digits, int index, int size, char[] currWord, List<String> result) {
        if (index == size) {
            result.add(String.valueOf(currWord));
            return;
        }
        ArrayList<Character> characters = map.get(digits[index]);
        for (Character c : characters) {
            currWord[index] = c;
            add(digits, index+1, size, currWord, result);
        }
    }

    public static void main(String[] args) {
        System.out.println(letterCombinations("23"));
        System.out.println(letterCombinations(""));
        System.out.println(letterCombinations("2"));
        System.out.println(letterCombinations("245"));

    }
}
