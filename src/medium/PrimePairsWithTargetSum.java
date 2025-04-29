package medium;

import java.util.ArrayList;
import java.util.List;

public class PrimePairsWithTargetSum {
    public List<List<Integer>> findPrimePairs(int n) {
        if (n <= 3)
            return List.of();
        int[] numbers = new int[n+1];
        findPrimes(n, numbers);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 2; i <= n/2; i++) {
            if (numbers[i] == 0 && numbers[n-i] == 0)
                result.add(List.of(i, n-i));
        }
        return result;
    }

    private void findPrimes (int N, int[] numbers) {
        for (int i = 2; i <= Math.sqrt(N); i++) {
            if (numbers[i] == 0)
                for (int j = i*i; j <= N; j+=i)
                    numbers[j] = 1;
        }
    }

    public static void main(String[] args) {
        PrimePairsWithTargetSum P = new PrimePairsWithTargetSum();
        System.out.println(P.findPrimePairs(10));
        System.out.println(P.findPrimePairs(20));
        System.out.println(P.findPrimePairs(2));
    }
}
