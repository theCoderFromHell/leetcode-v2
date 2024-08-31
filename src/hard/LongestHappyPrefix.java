package hard;

public class LongestHappyPrefix {
    public String longestPrefix(String s) {
        int N = s.length();
        int[] LPS = new int[N];
        LPS[0] = 0;
        int start = 0, current = 1;
        while (current < N) {
            if (s.charAt(current) == s.charAt(start)) {
                LPS[current] = start + 1;
                start++;
                current++;
            } else {
                if (start > 0)
                    start = LPS[start-1];
                else
                    current++;
            }
        }
        return (LPS[N-1] == 0) ? "" : s.substring(0, LPS[N-1]);
    }

    public static void main(String[] args) {
        LongestHappyPrefix L = new LongestHappyPrefix();
        System.out.println(L.longestPrefix("ababab"));
        System.out.println(L.longestPrefix("level"));
        System.out.println(L.longestPrefix("abcaby"));
    }
}
