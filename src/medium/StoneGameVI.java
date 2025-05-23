package medium;

import java.util.Comparator;
import java.util.PriorityQueue;

public class StoneGameVI {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int size = aliceValues.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> -o[0]));
        for (int i = 0; i < size; i++)
            pq.add(new int[]{aliceValues[i] + bobValues[i], aliceValues[i], bobValues[i]});
        int A = 0, B = 0;
        boolean flag = true;
        while (!pq.isEmpty()) {
            int[] stone = pq.poll();
            if (flag)
                A += stone[1];
            else
                B += stone[2];
            flag = !flag;
        }
        if (A == B)
            return 0;
        return (A > B) ? 1 : -1;
    }

    public static void main(String[] args) {
        StoneGameVI S = new StoneGameVI();
        System.out.println(S.stoneGameVI(new int[]{1,3}, new int[]{2,1}));
        System.out.println(S.stoneGameVI(new int[]{1,2}, new int[]{3,1}));
        System.out.println(S.stoneGameVI(new int[]{2,4,3}, new int[]{1,6,7}));
    }
}
