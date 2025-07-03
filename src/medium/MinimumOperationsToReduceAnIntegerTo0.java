package medium;

public class MinimumOperationsToReduceAnIntegerTo0 {
    public int minOperations(int n) {
        int count = 0;
        while (n != 0) {
            long nearestTwoPower = nearestTwoPower(n);
            n = Math.abs(n - (int)Math.pow(2, nearestTwoPower));
            count++;
        }
        return count;
    }

    private long nearestTwoPower(int n) {
        return Math.round(Math.log10(n)/Math.log10(2));
    }

    public static void main(String[] args) {
        MinimumOperationsToReduceAnIntegerTo0 M = new MinimumOperationsToReduceAnIntegerTo0();
        System.out.println(M.minOperations(39));
        System.out.println(M.minOperations(54));
//        System.out.println(M.minOperations());
//        System.out.println(M.minOperations());
//        System.out.println(M.minOperations());
    }
}
