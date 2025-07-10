package medium;

import java.util.HashSet;

public class MaximumNumberOfIntegersToChooseFromARangeI {
    public int maxCount(int[] banned, int n, int maxSum) {
        int count = 0;
        int currSum = 0;
        HashSet<Integer> bannedNumbers = new HashSet<>();
        int size = banned.length;
        for (int i = 0; i < size; i++)
            bannedNumbers.add(banned[i]);
        for (int i = 1; i <=n; i++) {
            if (currSum + i <= maxSum && !bannedNumbers.contains(i)) {
                count++;
                currSum += i;
            } else if (currSum + i > maxSum)
                break;
        }
        return count;
    }

    public static void main(String[] args) {
        MaximumNumberOfIntegersToChooseFromARangeI M = new MaximumNumberOfIntegersToChooseFromARangeI();
        System.out.println(M.maxCount(new int[]{1,6,5}, 5, 6));
        System.out.println(M.maxCount(new int[]{1,2,3,4,5,6,7}, 8, 1));
        System.out.println(M.maxCount(new int[]{11}, 7, 50));
    }
}
