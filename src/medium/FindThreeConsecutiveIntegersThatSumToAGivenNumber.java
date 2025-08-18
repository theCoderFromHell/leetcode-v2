package medium;

public class FindThreeConsecutiveIntegersThatSumToAGivenNumber {
    public long[] sumOfThree(long num) {
        if (num % 3 != 0)
            return new long[]{};
        return new long[]{num/3 -1, num/3, num/3 + 1};
    }
}
