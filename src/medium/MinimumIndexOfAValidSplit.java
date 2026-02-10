package medium;

import java.util.List;

public class MinimumIndexOfAValidSplit {
    public int minimumIndex(List<Integer> nums) {
        int size = nums.size();
        int dominantNumber = findDominant(nums, size);
        int[] dominantFrequency = new int[size];
        dominantFrequency[0] = (dominantNumber == nums.getFirst()) ? 1 : 0;
        for (int i = 1; i < size; i++) {
            dominantFrequency[i] = dominantFrequency[i-1] + (nums.get(i) == dominantNumber ? 1 : 0);
        }
        int total = dominantFrequency[size-1];
        for (int i = 0; i < size; i++) {
            int left = dominantFrequency[i];
            int right = total - dominantFrequency[i];
            if (left * 2 > i+1 && right * 2 > size - 1 - i)
                return i;
        }
        return -1;
    }

    private int findDominant(List<Integer> nums, int size) {
        int dominant = nums.getFirst();
        int count = 1;
        for (int i = 1; i < size; i++) {
            if (nums.get(i) == dominant)
                count++;
            else
                count--;
            if (count == 0) {
                count = 1;
                dominant = nums.get(i);
            }
        }
        return dominant;
    }

    public static void main(String[] args) {
        MinimumIndexOfAValidSplit M = new MinimumIndexOfAValidSplit();

        System.out.println("Test 1: " + M.minimumIndex(List.of(1,2,2,2)));
        System.out.println("Test 2: " + M.minimumIndex(List.of(2,1,3,1,1,1,7,1,2,1)));
        System.out.println("Test 3: " + M.minimumIndex(List.of(3,3,3,3,7,2,2)));
        System.out.println("Test 4: " + M.minimumIndex(List.of(1,1,1,1,1)));
        System.out.println("Test 5: " + M.minimumIndex(List.of(5,5,5,5,5,1,2,3)));
        System.out.println("Test 6: " + M.minimumIndex(List.of(1,2,1,2,1)));
        System.out.println("Test 7: " + M.minimumIndex(List.of(7,7,7,7,7,7,7)));
        System.out.println("Test 8: " + M.minimumIndex(List.of(9,9,1,9,9)));
        System.out.println("Test 9: " + M.minimumIndex(List.of(1,2,3,4,5)));
    }
}
