package medium;

public class MaximumSubstringsWithDistinctStart {
    public int maxDistinct(String s) {
        int size = s.length();
        boolean[] seen = new boolean[26];
        int count = 0;
        for (int i = 0; i < size; i++) {
            int index = s.charAt(i) - 'a';
            if (!seen[index]) {
                seen[index] = true;
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        MaximumSubstringsWithDistinctStart M = new MaximumSubstringsWithDistinctStart();
        System.out.println(M.maxDistinct("abab"));
        System.out.println(M.maxDistinct("abcd"));
        System.out.println(M.maxDistinct("aaaa"));
        System.out.println(M.maxDistinct("abacaba"));
    }
}
