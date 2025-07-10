package medium;

import java.util.Arrays;

public class MaximumTastinessOfCandyBasket {
    public int maximumTastiness(int[] price, int k) {
        int size = price.length;
        Arrays.sort(price);
        int low = 0, high = price[size-1] - price[0];
        int mid;
        int maxTastiness = Integer.MIN_VALUE;
        while (low <= high) {
            mid = low + (high - low)/2;
            if (works(mid, price, size, k)) {
                maxTastiness = Math.max(maxTastiness, mid);
                low = mid + 1;
            } else
                high = mid - 1;
        }
        return maxTastiness;
    }

    private boolean works(int target, int[] price, int size, int k) {
        int count = 1;
        int index = 1;
        int lastSelected = price[0];
        while (index < size && count < k) {
            if (price[index] - lastSelected >= target) {
                count++;
                lastSelected = price[index];
                if (count == k)
                    return true;
            }
            index++;
        }
        return false;
    }

    public static void main(String[] args) {
        MaximumTastinessOfCandyBasket M = new MaximumTastinessOfCandyBasket();
        System.out.println(M.maximumTastiness(new int[]{13,5,1,8,21,2}, 3));
        System.out.println(M.maximumTastiness(new int[]{1,3,1}, 2));
        System.out.println(M.maximumTastiness(new int[]{7,7,7,7}, 2));
    }
}
