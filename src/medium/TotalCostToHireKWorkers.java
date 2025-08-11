package medium;

import java.util.HashSet;
import java.util.PriorityQueue;

public class TotalCostToHireKWorkers {
    public long totalCost(int[] costs, int k, int candidates) {
        int size = costs.length;
        PriorityQueue<int[]> front = new PriorityQueue<>((a, b) -> {
            if (a[0] == b[0])
                return a[1] - b[1];
            return a[0] - b[0];
        });
        PriorityQueue<int[]> back = new PriorityQueue<>((a, b) -> {
            if (a[0] == b[0])
                return a[1] - b[1];
            return a[0] - b[0];
        });
        for (int i = 0; i < candidates; i++) {
            front.add(new int[]{costs[i], i});
            back.add(new int[]{costs[size-1-i], size-1-i});
        }
        int frontIndex = candidates;
        int backIndex = size - 1 - candidates;
        long totalCost = 0;
        HashSet<Integer> chosen = new HashSet<>();
        while (k-- > 0) {
            int[] frontCandidate = !front.isEmpty() ? front.peek() : new int[]{Integer.MAX_VALUE, -1};
            int[] backCandidate = !back.isEmpty() ? back.peek() : new int[]{Integer.MAX_VALUE, -1};
            int[] takenFrom;
            if (frontCandidate[0] == backCandidate[0])
                takenFrom = frontCandidate[1] < backCandidate[1] ? frontCandidate : backCandidate;
            else
                takenFrom = frontCandidate[0] < backCandidate[0] ? frontCandidate : backCandidate;
            totalCost += takenFrom[0];
            chosen.add(takenFrom[1]);
            if (frontCandidate[1] == takenFrom[1]) {
                front.poll();
                while (chosen.contains(frontIndex))
                    frontIndex++;
                if (frontIndex < size) {
                    front.add(new int[]{costs[frontIndex], frontIndex});
                    frontIndex++;
                }
            }
            if (backCandidate[1] == takenFrom[1])  {
                back.poll();
                while (chosen.contains(backIndex))
                    backIndex--;
                if (backIndex >= 0) {
                    back.add(new int[]{costs[backIndex], backIndex});
                    backIndex--;
                }
            }
        }
        return totalCost;
    }

    public static void main(String[] args) {
        TotalCostToHireKWorkers T = new TotalCostToHireKWorkers();
        System.out.println(T.totalCost(new int[]{31,25,72,79,74,65,84,91,18,59,27,9,81,33,17,58}, 11, 2));
        System.out.println(T.totalCost(new int[]{17,12,10,2,7,2,11,20,8}, 3, 4));
        System.out.println(T.totalCost(new int[]{1,2,4,1}, 3, 3));
    }
}
