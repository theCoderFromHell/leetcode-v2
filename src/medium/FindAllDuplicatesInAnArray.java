package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindAllDuplicatesInAnArray {
    public List<Integer> findDuplicates(int[] nums) {
        int N = nums.length;
        int[] hash = new int[N+1];
        for (int i = 0; i < N; i++)
            hash[nums[i]]++;
        return IntStream.rangeClosed(1, N)
                .filter(i -> hash[i] == 2)
                .boxed()
                .collect(Collectors.toList());
    }
}
