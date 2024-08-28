package medium;

public class MaximumEnergyBoostFromTwoDrinks {
    public long maxEnergyBoost(int[] energyDrinkA, int[] energyDrinkB) {
        int N = energyDrinkA.length;
        Long[][] dp = new Long[N][3];
        return solve(energyDrinkA, energyDrinkB, 0, 0, N, dp);
    }

    private long solve(int[] energyDrinkA, int[] energyDrinkB, int hour, int option, int N, Long[][] dp) {
        if (hour == N)
            return 0;
        if (dp[hour][option] != null)
            return dp[hour][option];
        if (option == 0)
            dp[hour][option] = Math.max(energyDrinkA[hour] + solve(energyDrinkA, energyDrinkB, hour+1, 1, N, dp),
                    Math.max(energyDrinkB[hour] + solve(energyDrinkA, energyDrinkB, hour+1, 2, N, dp),
                            solve(energyDrinkA, energyDrinkB, hour+1, 0, N, dp)));
        else if (option == 1)
            dp[hour][option] = Math.max(energyDrinkA[hour] + solve(energyDrinkA, energyDrinkB, hour+1, 1, N, dp),
                    solve(energyDrinkA, energyDrinkB, hour+1, 0, N, dp));
        else if (option == 2)
            dp[hour][option] = Math.max(energyDrinkB[hour] + solve(energyDrinkA, energyDrinkB, hour+1, 2 , N, dp),
                    solve(energyDrinkA, energyDrinkB, hour+1, 0, N, dp));
        return dp[hour][option];
    }

    public static void main(String[] args) {
        MaximumEnergyBoostFromTwoDrinks M = new MaximumEnergyBoostFromTwoDrinks();
        System.out.println(M.maxEnergyBoost(new int[]{1,3,1}, new int[]{3,1,1}));
        System.out.println(M.maxEnergyBoost(new int[]{4,1,1}, new int[]{1,1,3}));
        System.out.println(M.maxEnergyBoost(new int[]{3,3,3,5}, new int[]{5,3,6,5}));
    }
}
