package medium;

import java.util.ArrayList;
import java.util.List;

public class MajorityElementII {
    public List<Integer> majorityElement(int[] nums) {
        int N = nums.length;
        Integer option1 = null;
        Integer option2 = null;
        int option1Count = 0;
        int option2Count = 0;

        for (int i = 0; i < N; i++) {

            if (option1 != null && nums[i] == option1)
                option1Count++;
            else if (option2 != null && nums[i] == option2)
                option2Count++;
            else if (option1 == null) {
                option1 = nums[i];
                option1Count++;
            } else if (option2 == null) {
                option2 = nums[i];
                option2Count++;
            } else {
                option1Count--;
                if (option1Count == 0)
                    option1 = null;

                option2Count--;
                if (option2Count == 0)
                    option2 = null;
            }
        }
        option1Count = 0;
        option2Count = 0;

        for (int i = 0; i < N; i++) {
            if (option1 != null && nums[i] == option1)
                option1Count++;
            if (option2 != null && nums[i] == option2)
                option2Count++;
        }

        List<Integer> result = new ArrayList<>();
        if (option1Count > N/3)
            result.add(option1);
        if (option2Count > N/3)
            result.add(option2);
        return result;
    }

    public static void main(String[] args) {
        MajorityElementII m = new MajorityElementII();
        System.out.println(m.majorityElement(new int[]{3,2,3}));
    }
}
