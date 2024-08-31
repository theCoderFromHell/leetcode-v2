package easy;

public class FindTheIndexOfTheFirstOccurrenceInAString {
    public int strStr(String haystack, String needle) {
        int N = haystack.length();
        int M = needle.length();
        if (N < M)
            return -1;
        //create the deadly LPS array
        int[] LPS = new int[M];
        int start = 0, current = 1;
        while (current < M) {
            if (needle.charAt(current) == needle.charAt(start)) {
                LPS[current] = start + 1;
                start++;
                current++;
            } else {
                LPS[current] = 0;
                if (start > 0)
                    start = LPS[start-1];
                else
                    current++;
            }
        }
        int h = 0, n = 0;
        while (h < N) {
            if (haystack.charAt(h) == needle.charAt(n)) {
                h++;
                n++;
                if (n == M) {
                    //Found the pattern
                    return h - M;
                }
            } else {
                if (n > 0)
                    n = LPS[n-1];
                else
                    h++;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        FindTheIndexOfTheFirstOccurrenceInAString F = new FindTheIndexOfTheFirstOccurrenceInAString();
        System.out.println(F.strStr("sadbutsad", "sad"));
    }
}
