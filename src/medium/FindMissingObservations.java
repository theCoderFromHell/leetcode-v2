package medium;

public class FindMissingObservations {
    public int[] missingRolls(int[] rolls, int mean, int n) {
        int m = rolls.length;
        int existingSum = 0;
        for (int roll : rolls)
            existingSum += roll;
        int requiredSum = ((n + m) * mean) - existingSum;
        if (6 * n < requiredSum)
            return new int[0];
        if (n > requiredSum)
            return new int[0];
        int q = requiredSum / n;
        int remainder = requiredSum % n;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = q;
            if (remainder > 0) {
                result[i] += 1;
                remainder--;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        FindMissingObservations f = new FindMissingObservations();
        int[] result = f.missingRolls(new int[]{3,2,4,3}, 4, 2);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        result = f.missingRolls(new int[]{1,5,6}, 3, 4);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        result = f.missingRolls(new int[]{1, 2, 3, 4}, 6, 4);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
    }
}
