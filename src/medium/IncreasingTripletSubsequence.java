package medium;

public class IncreasingTripletSubsequence {
    public boolean increasingTriplet(int[] nums) {
        int N = nums.length;
        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            if (nums[i] < first)
                first = nums[i];
            //first < nums[i] < second
            else if (nums[i] < second)
                second = nums[i];
            //first  < second < nums[i]
            else
                return true;
        }
        return false;
    }



    public static void main(String[] args) {
        IncreasingTripletSubsequence I = new IncreasingTripletSubsequence();
        System.out.println(I.increasingTriplet(new int[]{1,2,3,4,5}));
        System.out.println(I.increasingTriplet(new int[]{5,4,3,2,1}));
        System.out.println(I.increasingTriplet(new int[]{2,1,5,0,4,6}));
    }
}
