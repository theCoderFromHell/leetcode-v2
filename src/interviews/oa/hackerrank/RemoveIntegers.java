package interviews.oa.hackerrank;

import java.util.HashMap;
import java.util.HashSet;

public class RemoveIntegers {
    private static final int MOD = 1_000_000_007;

    public int minCost(int N, int K, int[] specialNumbers, int P, int Q) {
        HashSet<Integer> specials = new HashSet<>();
        for (int num : specialNumbers)
            specials.add(num);
        HashMap<String, Long> dp = new HashMap<>();
        return (int) (dfs(1, 1 << N, dp, specials, P, Q) % MOD);
    }

    private long dfs(int l, int r, HashMap<String, Long> dp, HashSet<Integer> specials, int P, int Q) {
        String key = l + "," + r;
        if (dp.containsKey(key))
            return dp.get(key);
        int L = r - l + 1;
        int X = countSpecial(l, r, specials);
        long cost = (X == 0) ? Q : (long) L * X * P;

        if (L % 2 == 0) {
            int mid = (l + r) / 2;
            long splitCost = dfs(l, mid, dp, specials, P, Q) + dfs(mid + 1, r, dp, specials, P, Q);
            if (splitCost < cost)
                cost = splitCost;
        }
        dp.put(key, cost);
        return cost;
    }

    private int countSpecial(int l, int r, HashSet<Integer> specials) {
        int count = 0;
        for (int i = l; i <= r; i++) {
            if (specials.contains(i))
                count++;
        }
        return count;
    }
}

/*
Given the first 2^N integers in which K of them are special, remove all the 2^N integers.

There are 3 choices to remove integers, starting with 2^N integers:


1. If the current segment contains L integers and X of them are special, the cost of removing the Lintegers is L*X*P where P is a constant.

2. If the current segment contains L integers and none of them is special, the cost of removing all the L integers is Q, where Q is a constant.

3. If the current segment contains L integers and L is divisible by 2, then there are 2 choices:

1. Calculate the cost of this part by using method 1 or method 2

2. Divide this part into two parts containing L/2 integers each, then add the costs of those two parts together.

Find the minimum cost of removing all 2 integers, modulo (10^9 + 7).

NOTE: The integers must remain in their original order while dividing the current part into two smaller parts. If the current part contains integers [1, 2, 3, 4], the only correct way to divide it is [1, 2] and [3,4]

N <= 30
 */
