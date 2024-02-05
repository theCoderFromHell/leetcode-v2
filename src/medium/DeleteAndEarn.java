package medium;

import java.util.*;
import java.util.stream.IntStream;

public class DeleteAndEarn {
    public static int deleteAndEarn(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int number : nums)
            map.put(number, map.getOrDefault(number, 0) + 1);
        List<Integer> numsWithoutDuplicates = new ArrayList<>(map.keySet().stream().toList());
        Collections.sort(numsWithoutDuplicates);
        int including = numsWithoutDuplicates.getFirst() * map.get(numsWithoutDuplicates.getFirst());
        int excluding = 0;
        int N = numsWithoutDuplicates.size();
        for (int i = 1; i < N; i++) {
            int prev = including;
            including = (numsWithoutDuplicates.get(i-1)+1 == numsWithoutDuplicates.get(i))
                    ? Math.max(including, excluding + numsWithoutDuplicates.get(i) * map.get(numsWithoutDuplicates.get(i)))
                    : including + numsWithoutDuplicates.get(i) * map.get(numsWithoutDuplicates.get(i));
            excluding = prev;
        }
        return Math.max(including, excluding);
    }

    public static void main(String[] args) {
        System.out.println(deleteAndEarn(new int[]{1,2,3,15,16,17,18}));
//        System.out.println(deleteAndEarn(new int[]{3,4,2}));
//        System.out.println(deleteAndEarn(new int[]{2,2,3,3,3,4}));
    }

}
