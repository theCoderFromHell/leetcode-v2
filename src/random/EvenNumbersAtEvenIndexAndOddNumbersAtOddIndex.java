package random;

public class EvenNumbersAtEvenIndexAndOddNumbersAtOddIndex {

    public void evenOdd(int[] arr) {
        int i = 0; // starting index for even numbers
        int j = 1; // starting index for odd numbers
        int n = arr.length; // length of the array
        while (i < n && j < n) {
            // if the number at i is odd and the number at j
            // is even, swap them and move to next pair of
            // indices
            if (arr[i] % 2 != 0 && arr[j] % 2 == 0) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i += 2; // move to next odd index
                j += 2; // move to next even index
            }
            else {
                // if the number at i is even, move to next
                // odd index
                if (arr[i] % 2 == 0) {
                    i += 2;
                }
                // if the number at j is odd, move to next
                // even index
                if (arr[j] % 2 != 0) {
                    j += 2;
                }
            }
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        EvenNumbersAtEvenIndexAndOddNumbersAtOddIndex E = new EvenNumbersAtEvenIndexAndOddNumbersAtOddIndex();
        int[] nums = {3,1,4,2,5,6};
        E.evenOdd(nums);
        for (int num : nums)
            System.out.print(num + " ");
    }
}
