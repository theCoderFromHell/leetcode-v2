package medium;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SingleThreadedCPU {
    public int[] getOrder(int[][] tasks) {
        int N = tasks.length;
        List<int[]> primary = new ArrayList<>();
        for (int i = 0; i < N; i++)
            primary.add(new int[] {tasks[i][0], tasks[i][1], i});
        primary.sort(Comparator.comparingInt(o -> o[0]));
        int currTime = 1;
        PriorityQueue<int[]> secondary = new PriorityQueue<>((o1, o2) -> {
            if (o1[1] == o2[1])
                return o1[2] - o2[2];
            return o1[1] - o2[1];
        });
        int index = 0;
        int[] result = new int[N];
        int resultIndex = 0;
        while (index < N || !secondary.isEmpty()) {
            while (index < N && primary.get(index)[0] <= currTime)
                secondary.add(primary.get(index++));

            if (!secondary.isEmpty()) {
                int[] currTask = secondary.poll();
                result[resultIndex++] = currTask[2];
                currTime += currTask[1];
            } else if (index < N)
                currTime = primary.get(index)[0];
        }
        return result;
    }

    public static void main(String[] args) {
        SingleThreadedCPU S = new SingleThreadedCPU();
        System.out.println(S.getOrder(new int[][]{{1,2},{2,4},{3,2},{4,1}}));
        System.out.println(S.getOrder(new int[][]{{7,10},{7,12},{7,5},{7,4},{7,2}}));
        System.out.println(S.getOrder(new int[][]{{19,13},{16,9},{21,10},{32,25},{37,4},{49,24},{2,15},{38,41},{37,34},{33,6},{45,4},{18,18},{46,39},{12,24}}));
    }
}
