package hard;

public class ShortestPalindrome {
    public String shortestPalindrome(String s) {
        String concat = s + "|" + new StringBuilder(s).reverse();
        int[] LPS = findLps(concat);
        int maxLPS = LPS[concat.length() - 1];
        return new StringBuilder(s.substring(maxLPS)).reverse() + s;
    }

    private int[] findLps(String concat) {
        int length = concat.length();
        int[] LPS = new int[length];
        int start = 0, current = 1;
        while (current < length) {
            if (concat.charAt(start) == concat.charAt(current)) {
                LPS[current] = start + 1;
                current++;
                start++;
            } else {
                if (start > 0)
                    start = LPS[start - 1];
                else
                    current++;
            }
        }
        return LPS;
    }

    public static void main(String[] args) {
        ShortestPalindrome S = new ShortestPalindrome();
        System.out.println(S.shortestPalindrome("aacecaaa"));
        System.out.println(S.shortestPalindrome("abcd"));
        System.out.println(S.shortestPalindrome("xztz"));
        System.out.println(S.shortestPalindrome("aabbaaa"));
    }
}
