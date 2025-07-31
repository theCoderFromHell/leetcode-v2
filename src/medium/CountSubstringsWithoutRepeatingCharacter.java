package medium;

import java.util.HashMap;

public class CountSubstringsWithoutRepeatingCharacter {
    public int numberOfSpecialSubstrings(String s) {
        int size = s.length();
        HashMap<Integer,Integer> frequency = new HashMap<>();
        int start = 0, end = 0;
        int result = 0;
        while (start <=  end && end < size) {
            int currentChar = s.charAt(end) - 'a';
            frequency.put(currentChar, frequency.getOrDefault(currentChar, 0) + 1);
            end++;
            while (start < end && frequency.size() != end - start) {
                currentChar = s.charAt(start) - 'a';
                frequency.put(currentChar, frequency.getOrDefault(currentChar, 0) - 1);
                if (frequency.get(currentChar) == 0)
                    frequency.remove(currentChar);
                start++;
            }
            if (frequency.size() == end - start)
                result += (end - start);
        }
        return result;
    }

    public static void main(String[] args) {
        CountSubstringsWithoutRepeatingCharacter C = new CountSubstringsWithoutRepeatingCharacter();
        System.out.println(C.numberOfSpecialSubstrings("abcd"));
        System.out.println(C.numberOfSpecialSubstrings("ooo"));
        System.out.println(C.numberOfSpecialSubstrings("abab"));
    }
}
