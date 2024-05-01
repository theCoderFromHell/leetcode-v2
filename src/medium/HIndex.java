package medium;

import java.util.Arrays;

public class HIndex {
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int N = citations.length;
        int result = 0;
        for (int i = 0; i < N; i++) {
            if (citations[i] <= N-i)
                result = Integer.max(result, citations[i]);
            else
                result = Integer.max(result, N-i);
        }
        return result;
    }


    public static void main(String[] args) {
        HIndex h = new HIndex();
        System.out.println(h.hIndex(new int[]{3,0,6,1,5}));
        System.out.println(h.hIndex(new int[]{1,3,1}));
        System.out.println(h.hIndex(new int[]{100}));
        System.out.println(h.hIndex(new int[]{}));

    }
}
