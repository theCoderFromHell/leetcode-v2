package medium;

import java.util.HashMap;

public class TupleWithSameProduct {
    public int tupleSameProduct(int[] nums) {
        int size = nums.length;
        HashMap<Integer,Integer> sameProduct = new HashMap<>();
        for (int i = 0; i < size; i++) {
            for (int j = i+1; j < size; j++)
                sameProduct.put(nums[i] * nums[j], sameProduct.getOrDefault(nums[i] * nums[j], 0) + 1);
        }
        int count = 0;
        for (int product : sameProduct.keySet()) {
            int value = sameProduct.get(product);
            count += ((8 * value * (value-1)))/2;
        }
        return count;
    }

    public static void main(String[] args) {
        TupleWithSameProduct T = new TupleWithSameProduct();
        System.out.println(T.tupleSameProduct(new int[]{2,3,4,6}));
        System.out.println(T.tupleSameProduct(new int[]{1,2,4,5,10}));
    }
}
