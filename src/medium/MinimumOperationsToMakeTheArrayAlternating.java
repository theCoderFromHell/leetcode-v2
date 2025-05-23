package medium;

import java.util.HashMap;

public class MinimumOperationsToMakeTheArrayAlternating {
    public int minimumOperations(int[] nums) {
        int size = nums.length;
        HashMap<Integer,Integer> oddPositions = new HashMap<>();
        HashMap<Integer,Integer> evenPositions = new HashMap<>();
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0)
                evenPositions.put(nums[i], evenPositions.getOrDefault(nums[i], 0) + 1);
            else
                oddPositions.put(nums[i], oddPositions.getOrDefault(nums[i], 0) + 1);
        }
        Integer firstEven = -1, firstEvenFrequency = 0;
        Integer secondEven = -1, secondEvenFrequency = 0;
        for (int key : evenPositions.keySet()) {
            int frequency = evenPositions.get(key);
            if (frequency > firstEvenFrequency) {
                secondEven = firstEven;
                secondEvenFrequency = firstEvenFrequency;
                firstEven = key;
                firstEvenFrequency = frequency;
            } else if (frequency > secondEvenFrequency) {
                secondEven = key;
                secondEvenFrequency = frequency;
            }
        }
        Integer firstOdd = -1, firstOddFrequency = 0;
        Integer secondOdd = -1, secondOddFrequency = 0;
        for (int key : oddPositions.keySet()) {
            int frequency = oddPositions.get(key);
            if (frequency > firstOddFrequency) {
                secondOdd = firstOdd;
                secondOddFrequency = firstOddFrequency;
                firstOdd = key;
                firstOddFrequency = frequency;
            } else if (frequency > secondOddFrequency) {
                secondOdd = key;
                secondOddFrequency = frequency;
            }
        }
        if (!firstOdd.equals(firstEven))
            return (size - (firstOddFrequency + firstEvenFrequency));
        else
            return Math.min((size - (firstOddFrequency + secondEvenFrequency)), (size - (secondOddFrequency + firstEvenFrequency)));
    }

    public static void main(String[] args) {
        MinimumOperationsToMakeTheArrayAlternating M = new MinimumOperationsToMakeTheArrayAlternating();
        System.out.println(M.minimumOperations(new int[]{3,1,3,2,4,3}));
        System.out.println(M.minimumOperations(new int[]{1,2,2,2,2}));
    }
}
