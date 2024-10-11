package medium;

public class BestSightseeingPair {
    public int maxScoreSightseeingPair(int[] values) {
        int N = values.length;
        int maxSoFar = values[0] - 1;
        int result = Integer.MIN_VALUE;
        for (int i = 1; i < N; i++) {
            result = Math.max(result, values[i] + maxSoFar);
            maxSoFar = Math.max(maxSoFar, values[i]) - 1;
        }
        return result;
    }

    public static void main(String[] args) {
        BestSightseeingPair B = new BestSightseeingPair();
        System.out.println(B.maxScoreSightseeingPair(new int[]{8,1,5,2,6}));
        System.out.println(B.maxScoreSightseeingPair(new int[]{1,2}));
    }
}
