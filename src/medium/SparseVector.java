package medium;

import java.util.HashMap;

public class SparseVector {
    HashMap<Integer,Integer> values;
    SparseVector(int[] nums) {
        int size = nums.length;
        this.values = new HashMap<>();
        for (int i = 0; i < size; i++) {
            if (nums[i] != 0)
                values.put(i, nums[i]);
        }
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        int dotProduct = 0;
        HashMap<Integer, Integer> vecValues = vec.values;
        for (Integer index : values.keySet()) {
            if (vecValues.containsKey(index))
                dotProduct += (values.get(index) * vecValues.get(index));
        }
        return dotProduct;
    }

    public static void main(String[] args) {
        SparseVector S1 = new SparseVector(new int[]{1,0,0,2,3});
        SparseVector S2 = new SparseVector(new int[]{0,3,0,4,0});
        System.out.println(S1.dotProduct(S2));
    }
}
