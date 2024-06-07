package medium;

import java.util.Arrays;
import java.util.Map;

public class MinimumSwapsToGroupAll1sTogether {
    public int minSwaps(int[] data) {
        int N = data.length;
        int countOfOnes = Arrays.stream(data).sum();
        int[] cumulated = new int[N+1];
        int count = 0;
        cumulated[0] = 0;
        // data =      {1,0,1,0,1,0,0,1,1,0,1}
        // cumulated = {0,1,1,2,2,3,3,3,4,5,5,6}
        for (int i = 1; i <= N; i++) {
            count += data[i-1];
            cumulated[i] = count;
        }
        int end = N-1;
        int maxOnes = 0;
        while (end - countOfOnes + 1 >= 0) {
            maxOnes = Math.max(maxOnes, cumulated[end+1] - cumulated[end - countOfOnes + 1]);
            end--;
        }
        return (countOfOnes - maxOnes);
    }

    public static void main(String[] args) {
        MinimumSwapsToGroupAll1sTogether m = new MinimumSwapsToGroupAll1sTogether();
        System.out.println(m.minSwaps(new int[]{1,0,1,0,1,0,0,1,1,0,1}));
        System.out.println(m.minSwaps(new int[]{1,0,1,0,1}));
        System.out.println(m.minSwaps(new int[]{0,0,0,1,0}));
        System.out.println(m.minSwaps(new int[]{1,0,0,1,1,1}));
    }
}
