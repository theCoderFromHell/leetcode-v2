package medium;

public class MinimumOperationsToMakeArrayEqual {
    public int minOperations(int n) {
        return (n/2) * (((n % 2 == 0) ? 1 : 2) + n-1)/2;
    }

    public static void main(String[] args) {
        MinimumOperationsToMakeArrayEqual M = new MinimumOperationsToMakeArrayEqual();
        System.out.println(M.minOperations(3));
        System.out.println(M.minOperations(6));
        System.out.println(M.minOperations(10000));
    }
}
