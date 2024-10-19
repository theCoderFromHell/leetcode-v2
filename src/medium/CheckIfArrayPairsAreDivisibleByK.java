package medium;

import java.util.HashMap;
import java.util.Objects;

public class CheckIfArrayPairsAreDivisibleByK {
    public boolean canArrange(int[] arr, int k) {
        int N = arr.length;
        int[] remainderCount = new int[k];
        for (int i = 0; i < N; i++) {
            int remainder = (arr[i] % k + k) % k;
            remainderCount[remainder]++;
        }
        for (int remainder = 0; remainder < k; remainder++) {
            if (remainder == 0 && remainderCount[remainder] % 2 == 1)
                return false;
            if (remainder!= 0 && remainderCount[remainder] != remainderCount[k - remainder])
                return false;
        }
        return true;
    }

    public boolean canArrangeV2(int[] arr, int k) {
        int N = arr.length;
        HashMap<Integer, Integer> remainderCount = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int remainder = (arr[i] % k + k) % k;
            remainderCount.put(remainder, remainderCount.getOrDefault(remainder, 0) + 1);
        }
        for (int remainder : remainderCount.keySet()) {
            if (remainder == 0 && remainderCount.get(remainder) % 2 == 1)
                return false;
            if (remainder!= 0 && !Objects.equals(remainderCount.get(remainder), remainderCount.get(k - remainder)))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        CheckIfArrayPairsAreDivisibleByK C = new CheckIfArrayPairsAreDivisibleByK();
        System.out.println(C.canArrange(new int[]{1,2,3,4,5,10,6,7,8,9}, 5));
        System.out.println(C.canArrange(new int[]{1,2,3,4,5,6}, 7));
        System.out.println(C.canArrange(new int[]{1,2,3,4,5,6}, 10));
        System.out.println(C.canArrange(new int[]{-1,1,-2,2,-3,3,-4,4}, 3));


    }
}
