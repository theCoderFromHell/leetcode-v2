package interviews.oa.codility;

public class CarsOverBridge {
    public int numberOfCarsToTurn(int U, int[] weights) {
        int size = weights.length;
        if (size == 0)
            return 0;
        // First check if any single car can cross (weight <= U)
        boolean anyCanCross = false;
        for (int w : weights) {
            if (w <= U) {
                anyCanCross = true;
                break;
            }
        }
        // If no car can cross alone, all must turn back
        if (!anyCanCross)
            return size;

        // Otherwise proceed with DP solution
        int[] dp = new int[size];
        dp[0] = 1;
        int maxLength = 1;
        for (int i = 1; i < size; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (weights[j] + weights[i] <= U)
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            maxLength = Math.max(maxLength, dp[i]);
        }
        return (size - maxLength);
    }
}
