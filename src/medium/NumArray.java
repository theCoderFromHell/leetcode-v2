package medium;

public class NumArray {
    int[] numbers;
    int[] bit;
    int size;
    public NumArray(int[] nums) {
        this.size = nums.length + 1;
        this.numbers = new int[nums.length];
        this.bit = new int[this.size];
        for (int i = 0; i < nums.length; i++)
            update(i, nums[i]);
    }

    public void update(int index, int val) {
        int value = val - numbers[index];
        numbers[index] = val;
        index++;
        while (index < size) {
            bit[index] += value;
            index += (index & -index);
        }
    }

    private int sum(int index) {
        int sum = 0;
        index++;
        while (index > 0) {
            sum += bit[index];
            index -= (index & -index);
        }
        return sum;
    }

    public int sumRange(int left, int right) {
        return sum(right) - sum(left-1);
    }

    public static void main(String[] args) {
        NumArray numArray = new NumArray(new int[]{1,3,5});
        System.out.println(numArray.sumRange(0, 2));
        numArray.update(1, 2);
        System.out.println(numArray.sumRange(0,2));
    }
}
