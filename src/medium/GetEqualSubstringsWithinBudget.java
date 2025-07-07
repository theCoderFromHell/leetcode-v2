package medium;

public class GetEqualSubstringsWithinBudget {
    public int equalSubstring(String s, String t, int maxCost) {
        int size = s.length();
        int[] diff = new int[size];
        for (int i = 0; i < size; i++)
            diff[i] = Math.abs(s.charAt(i) - t.charAt(i));
        int start = 0, end = 0;
        int maxLength = 0;
        int currCost = diff[0];
        while (start<= end && end < size) {
            if (currCost <= maxCost)
                maxLength = Math.max(maxLength, end - start + 1);
            end++;
            if (end < size)
                currCost += diff[end];
            while (start < end && currCost > maxCost) {
                currCost -= diff[start];
                start++;
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        GetEqualSubstringsWithinBudget G = new GetEqualSubstringsWithinBudget();
        System.out.println(G.equalSubstring("abcd", "bcdf", 3));
        System.out.println(G.equalSubstring("abcd", "cdef", 3));
        System.out.println(G.equalSubstring("abcd", "acde", 0));
//        System.out.println(G.equalSubstring("", "", ));
    }
}
