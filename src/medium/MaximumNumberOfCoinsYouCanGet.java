package medium;

import java.util.Arrays;

public class MaximumNumberOfCoinsYouCanGet {
    public int maxCoins(int[] piles) {
        int count = piles.length / 3;
        Arrays.sort(piles);
        int index = piles.length - 2;
        int result = 0;
        while (count-- > 0) {
            result += (piles[index]);
            index -= 2;
        }
        return result;
    }

    public static void main(String[] args) {
        MaximumNumberOfCoinsYouCanGet M = new MaximumNumberOfCoinsYouCanGet();
        System.out.println(M.maxCoins(new int[]{2,4,1,2,7,8}));
        System.out.println(M.maxCoins(new int[]{2,4,5}));
        System.out.println(M.maxCoins(new int[]{9,8,7,6,5,1,2,3,4}));
    }
}
