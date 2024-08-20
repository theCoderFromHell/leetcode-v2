package medium;

public class UglyNumberII {
    public int nthUglyNumber(int n) {
        int[] uglyNumbers = new int[n];
        uglyNumbers[0] = 1;
        int nextOfTwo = 2, indexOfTwo = 0;
        int nextOfThree = 3, indexOfThree = 0;
        int nextOfFive = 5, indexOfFive = 0;
        for (int i = 1; i < n; i++) {
            uglyNumbers[i] = Math.min(nextOfTwo,
                    Math.min(nextOfThree, nextOfFive));
            if (nextOfTwo == uglyNumbers[i]) {
                indexOfTwo++;
                nextOfTwo = uglyNumbers[indexOfTwo] * 2;
            }
            if (nextOfThree == uglyNumbers[i]) {
                indexOfThree++;
                nextOfThree = uglyNumbers[indexOfThree] * 3;
            }
            if (nextOfFive == uglyNumbers[i]) {
                indexOfFive++;
                nextOfFive = uglyNumbers[indexOfFive] * 5;
            }
        }
        return uglyNumbers[n-1];
    }

    public static void main(String[] args) {
        UglyNumberII U = new UglyNumberII();
        System.out.println(U.nthUglyNumber(10));
    }
}
