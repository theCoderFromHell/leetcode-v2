package medium;

public class NumberOfPairsOfStringsWithConcatenationEqualToTarget {
    public int numOfPairs(String[] nums, String target) {
        int size = nums.length;
        int result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j && target.equals(nums[i].concat(nums[j])))
                    result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        NumberOfPairsOfStringsWithConcatenationEqualToTarget N = new NumberOfPairsOfStringsWithConcatenationEqualToTarget();

        System.out.println("Test 1: " + N.numOfPairs(new String[]{"777","7","77","77"}, "7777") + " (Expected: 4)");
        System.out.println("Test 2: " + N.numOfPairs(new String[]{"123","4","12","34"}, "1234") + " (Expected: 2)");
        System.out.println("Test 3: " + N.numOfPairs(new String[]{"1","1","1"}, "11") + " (Expected: 6)");
        System.out.println("Test 4: " + N.numOfPairs(new String[]{"5","6","78"}, "578") + " (Expected: 1)");
        System.out.println("Test 5: " + N.numOfPairs(new String[]{"9","99","999"}, "9999") + " (Expected: 2)");
        System.out.println("Test 6: " + N.numOfPairs(new String[]{"12","34"}, "1234") + " (Expected: 1)");
        System.out.println("Test 7: " + N.numOfPairs(new String[]{"1","11","111"}, "1111") + " (Expected: 2)");
        System.out.println("Test 8: " + N.numOfPairs(new String[]{"123","456","789"}, "123456") + " (Expected: 1)");
        System.out.println("Test 9: " + N.numOfPairs(new String[]{"2"}, "22") + " (Expected: 0)");
        System.out.println("Test 10: " + N.numOfPairs(new String[]{"10","20","30","1020"}, "102030") + " (Expected: 1)");
    }
}
