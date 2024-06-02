package medium;

public class AppendCharactersToStringToMakeSubsequence {
    public int appendCharacters(String s, String t) {
        int sLength = s.length();
        int tLength = t.length();
        int i = 0, j = 0;
        while (i < sLength && j < tLength) {
            if (s.charAt(i) == t.charAt(j))
                j++;
            i++;
        }
        return (tLength - j);
    }
}
