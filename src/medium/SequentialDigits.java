package medium;

import java.util.ArrayList;
import java.util.List;

public class SequentialDigits {
    public List<Integer> sequentialDigits(int low, int high) {
        String sample = "123456789";
        List<Integer> result = new ArrayList<>();
        int left = String.valueOf(low).length();
        int right = String.valueOf(high).length();
        for (int length = left; length <= right; length++) {
            for (int index = 0; index < 10 - length; index++) {
                int value = Integer.parseInt(sample.substring(index, index + length));
                if (value >= low && value <= high)
                    result.add(value);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        SequentialDigits sq = new SequentialDigits();
        System.out.println(sq.sequentialDigits(100, 300));
        System.out.println(sq.sequentialDigits(1000, 13000));
        System.out.println(sq.sequentialDigits(45, 23984));
    }
}
