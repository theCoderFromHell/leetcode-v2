package medium;

public class MaximumNumberOfVowelsInASubstringOfGivenLength {
    public int maxVowels(String s, int k) {
        if (s == null || s.isEmpty() || s.isBlank())
            return 0;
        int N = s.length();
        int start = 0;
        int vowels = 0;
        for (int i = 0; i < k; i++) {
            if (isVowel(s.charAt(i)))
                vowels++;
        }
        int maxVowels = vowels;
        int end = k-1;
        while (end < N-1) {
            if (isVowel(s.charAt(start)))
                vowels--;
            start++;
            end++;
            if (isVowel(s.charAt(end)))
                vowels++;
            maxVowels = Math.max(maxVowels, vowels);
        }
        return maxVowels;
    }

    private boolean isVowel(char c) {
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
            return true;
        return false;
    }
}
