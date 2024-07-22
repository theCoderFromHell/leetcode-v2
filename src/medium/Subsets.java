package medium;

import java.util.ArrayList;
import java.util.List;

public class Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        if(null == nums || nums.length == 0)
            return new ArrayList<>();
        int length = nums.length;;
        int maximum = 1<<length;
        List<List<Integer>> result = new ArrayList<>();
        for(int i=0; i<maximum; i++) {
            List<Integer> subset =  new ArrayList<>();
            for(int j=1;j<=length; j++) {
                if((i>>(j-1) & 1) == 1)
                    subset.add(nums[j-1]);
            }
            result.add(subset);
        }
        return result;
    }
}
