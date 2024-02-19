package medium;

import java.util.Arrays;

public class MinimumNumberOfDaysToMakeMBouquets {
    public static int minDays(int[] bloomDay, int m, int k) {
        int N = bloomDay.length;
        if ((long)m*k > N)
            return -1;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            min = Math.min(min, bloomDay[i]);
            max = Math.max(max, bloomDay[i]);
        }
        int mid;
        int minDays = Integer.MAX_VALUE;
        while (min <= max) {
            mid = min + (max-min)/2;
            if (canMakeBouquets(bloomDay, N, m, k, mid)) {
                minDays = mid;
                max = mid-1;
            } else
                min = mid + 1;
        }
        return minDays;
    }

    private static boolean canMakeBouquets(int[] bloomDay, int N, int m, int k, int cutOff) {
        int count = 0;
        int bouquets = 0;
        for (int i = 0; i < N; i++) {
            if (bloomDay[i] <= cutOff)
                count++;
            else {
                bouquets += count / k;
                count = 0;
            }
        }
        bouquets += count/k;
        return bouquets >= m;
    }

    public static void main(String[] args) {
        System.out.println(minDays(new int[]{1,10,3,10,2}, 3, 1));
        System.out.println(minDays(new int[]{1,10,3,10,2}, 3, 2));
        System.out.println(minDays(new int[]{7,7,7,7,12,7,7}, 2, 3));

    }
}
