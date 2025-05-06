package medium;

import java.util.PriorityQueue;

public class MinimumCostToConnectSticks {
    public int connectSticks(int[] sticks) {
        int size = sticks.length;
        if (size == 1)
            return 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < size; i++)
            pq.add(sticks[i]);
        int cost = 0;
        while (!pq.isEmpty() && pq.size() > 1) {
            int a = pq.poll();
            int b = pq.poll();
            cost += (a+b);
            pq.add(a+b);
        }
        return cost;
    }

    public static void main(String[] args) {
        MinimumCostToConnectSticks M = new MinimumCostToConnectSticks();
        System.out.println(M.connectSticks(new int[]{2,4,3}));
        System.out.println(M.connectSticks(new int[]{1,8,3,5}));
        System.out.println(M.connectSticks(new int[]{5}));
    }
}
