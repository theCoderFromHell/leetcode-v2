package medium;

// https://leetcode.com/problems/find-unique-binary-string/

public class FindUniqueBinaryString {
    public String findDifferentBinaryString(String[] nums) {
        int size = nums.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(nums[i].charAt(i) == '0' ? '1' : '0');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        FindUniqueBinaryString F = new FindUniqueBinaryString();
        System.out.println(F.findDifferentBinaryString(new String[]{"01", "10"}));            // e.g. "00" or "11"
        System.out.println(F.findDifferentBinaryString(new String[]{"00", "01"}));            // e.g. "10" or "11"
        System.out.println(F.findDifferentBinaryString(new String[]{"111", "011", "001"}));   // e.g. "000"
        System.out.println(F.findDifferentBinaryString(new String[]{"0"}));                   // "1"
    }
}
