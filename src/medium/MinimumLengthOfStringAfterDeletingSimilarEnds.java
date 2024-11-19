package medium;

public class MinimumLengthOfStringAfterDeletingSimilarEnds {
    public int minimumLength(String s) {
        int size = s.length();
        int start = 0, end = size-1;
        char[] sChars = s.toCharArray();
        int result = size;
        while (start < end && sChars[start] == sChars[end]) {
            char c = sChars[start];
            while (start < size && sChars[start] == c)
                start++;
            while (end >= 0 && sChars[end] == c)
                end--;
            result = Math.min(result, Math.max(0, end - start + 1));
        }
        return result;
    }

    public static void main(String[] args) {
        MinimumLengthOfStringAfterDeletingSimilarEnds M = new MinimumLengthOfStringAfterDeletingSimilarEnds();
        System.out.println(M.minimumLength("ca"));
        System.out.println(M.minimumLength("cabaabac"));
        System.out.println(M.minimumLength("aabccabba"));
    }
}
