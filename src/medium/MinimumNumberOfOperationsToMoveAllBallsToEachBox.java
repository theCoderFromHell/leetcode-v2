package medium;

import java.util.Arrays;
import java.util.HashSet;

public class MinimumNumberOfOperationsToMoveAllBallsToEachBox {
    public int[] minOperations(String boxes) {
        int size = boxes.length();
        HashSet<Integer> nonEmptyBoxes = new HashSet<>();
        for (int i = 0; i < size; i++) {
            if (boxes.charAt(i) != '0')
                nonEmptyBoxes.add(i);
        }
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            int total = 0;
            for (int index : nonEmptyBoxes)
                total += Math.abs(index - i);
            result[i] = total;
        }
        return result;
    }

    public static void main(String[] args) {
        MinimumNumberOfOperationsToMoveAllBallsToEachBox M = new MinimumNumberOfOperationsToMoveAllBallsToEachBox();
        System.out.println(Arrays.toString(M.minOperations("110")));
        System.out.println(Arrays.toString(M.minOperations("001011")));
    }
}
