package medium;

import java.util.HashMap;

public class MinimumNumberOfOperationsToMakeArrayEmpty {
    public int minOperations(int[] nums) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int num : nums)
            map.put(num, map.getOrDefault(num, 0) + 1);

        int count = 0;
        for (int number : map.keySet()) {
            int frequency = map.get(number);
            if (frequency % 3 == 0)
                count += (frequency / 3);
            else if (frequency % 2 == 0){
                while (frequency > 3 && frequency % 3 != 0) {
                    frequency -= 2;
                    count++;
                }
                if (frequency % 3 == 0)
                    count += (frequency / 3);
                else count += (frequency / 2);
            } else {
                while (frequency > 3 && frequency % 3 != 0) {
                    frequency -= 2;
                    count++;
                }
                if (frequency % 3 == 0)
                    count += (frequency / 3);
                else
                    return -1;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        MinimumNumberOfOperationsToMakeArrayEmpty M = new MinimumNumberOfOperationsToMakeArrayEmpty();
        System.out.println(M.minOperations(new int[]{14,12,14,14,12,14,14,12,12,12,12,14,14,12,14,14,14,12,12}));
    }
}
