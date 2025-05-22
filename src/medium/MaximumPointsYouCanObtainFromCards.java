package medium;

public class MaximumPointsYouCanObtainFromCards {
    public int maxScore(int[] cardPoints, int k) {
        int size = cardPoints.length;
        int currSum = 0;
        int minSum = Integer.MAX_VALUE;
        int windowLength = size - k;
        for (int i = 0; i < windowLength; i++)
            currSum += cardPoints[i];
        int totalSum = currSum;
        for (int i = windowLength; i < size; i++) {
            minSum = Math.min(minSum, currSum);
            currSum -= cardPoints[i - windowLength];
            currSum += cardPoints[i];
            totalSum += cardPoints[i];
        }
        return (totalSum - Math.min(minSum, currSum));
    }

    public static void main(String[] args) {
        MaximumPointsYouCanObtainFromCards M = new MaximumPointsYouCanObtainFromCards();
        System.out.println(M.maxScore(new int[]{1,2,3,4,5,6,1}, 3));
        System.out.println(M.maxScore(new int[]{2,2,2}, 2));
        System.out.println(M.maxScore(new int[]{9,7,7,9,7,7,9}, 7));
    }
}
