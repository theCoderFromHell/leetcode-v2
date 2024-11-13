package hard;

public class LexicographicallySmallestBeautifulString {
    public String smallestBeautifulString(String s, int k) {
        int length = s.length();
        char[] sChars = s.toCharArray();
        for (int i = length-1; i >= 0; i--) {
            while (sChars[i] - 'a' < k-1) {
                sChars[i]++;
                if (!palindromeExists(sChars, i)) {
                    int j = i + 1;
                    while (j < length) {
                        sChars[j] = 'a';
                        while (palindromeExists(sChars, j)) {
                            if (sChars[j] - 'a' < k - 1)
                                sChars[j]++;
                            else
                                break;
                        }
                        j++;
                    }
                    if (j == length || !palindromeExists(sChars, j))
                        return new String(sChars);
                }
            }
        }
        return "";
    }

    private boolean palindromeExists(char[] sChars, int index) {
        if (index > 0 && sChars[index-1] == sChars[index])
            return true;
        if (index > 1 && sChars[index-2] == sChars[index])
            return true;
        return false;
    }

    public static void main(String[] args) {
        LexicographicallySmallestBeautifulString L = new LexicographicallySmallestBeautifulString();
        System.out.println(L.smallestBeautifulString("abcz", 26));
        System.out.println(L.smallestBeautifulString("dc", 4));
    }
}
