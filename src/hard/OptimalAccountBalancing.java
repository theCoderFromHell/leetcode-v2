package hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OptimalAccountBalancing {
    public int minTransfers(int[][] transactions) {
        HashMap<Integer,Integer> pendingForUser = new HashMap<>();
        for (int[] transaction : transactions) {
            pendingForUser.put(transaction[0], pendingForUser.getOrDefault(transaction[0], 0) + transaction[2]);
            pendingForUser.put(transaction[1], pendingForUser.getOrDefault(transaction[1], 0) - transaction[2]);
        }
        int[] netCredit = new int[12];
        int index = 0;
        for (Integer user : pendingForUser.keySet()) {
            if (pendingForUser.get(user) != 0)
                netCredit[index++] = pendingForUser.get(user);
        }
        return dfs(netCredit, netCredit.length, 0);
    }

    private int dfs(int[] netCredit, int N, int index) {
        if (index == N)
            return 0;
        int minTxns = Integer.MAX_VALUE;
        if (netCredit[index] == 0)
            return dfs(netCredit, N, index+1);
        else {
            for (int i = index+1; i < N; i++) {
                if (netCredit[index] * netCredit[i] < 0) {
                    netCredit[i] += netCredit[index];
                    minTxns = Math.min(minTxns, 1 + dfs(netCredit, N, index+1));
                    netCredit[i] -= netCredit[index];
                }
            }
        }
        return minTxns;
    }

    public static void main(String[] args) {
        OptimalAccountBalancing o = new OptimalAccountBalancing();
        System.out.println(o.minTransfers(new int[][]{
                {0, 1, 10},
                {2, 0, 5}
        }));
        System.out.println(o.minTransfers(new int[][]{
                {0,1,10},
                {1,0,1},
                {1,2,5},
                {2,0,5}
        }));
    }
}
