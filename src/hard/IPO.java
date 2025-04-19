package hard;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class IPO {
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int size = profits.length;
        List<int[]> projects = new ArrayList<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] == o2[0])
                return o1[1] - o2[1];
            return o2[0] - o1[0];
        });
        for (int i = 0; i < size; i++)
            projects.add(new int[]{profits[i], capital[i]});
        projects.sort((o1, o2) -> {
            if (o1[1] == o2[1])
                return o2[0] - o1[0];
            return o1[1] - o2[1];
        });
        int index = 0;
        while (k > 0 && (index < size || !pq.isEmpty())) {
            while (index < size && projects.get(index)[1] <= w) {
                pq.add(new int[]{projects.get(index)[0], projects.get(index)[1]});
                index++;
            }
            if (pq.isEmpty())
                return w;
            int[] p = pq.poll();
            w += (p[0]);
            k--;
        }
        return w;
    }

    public static void main(String[] args) {
        IPO I = new IPO();
        System.out.println(I.findMaximizedCapital(1, 2, new int[]{1,2,3}, new int[]{1,1,2}));
        System.out.println(I.findMaximizedCapital(2, 0, new int[]{1,2,3}, new int[]{0,1,1}));
        System.out.println(I.findMaximizedCapital(3, 0, new int[]{1,2,3}, new int[]{0,1,2}));
    }
}
