package medium;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class QueueReconstructionByHeight {
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (o1, o2) -> (o1[0] == o2[0]) ? o1[1] - o2[1] : (o2[0] - o1[0]));
        List<int[]> order = new LinkedList<>();
        for (int[] person : people)
            order.add(person[1], person);
        int[][] result =  order.toArray(new int[people.length][2]);
        return result;
    }

    public static void main(String[] args) {
        QueueReconstructionByHeight q = new QueueReconstructionByHeight();
        q.reconstructQueue(new int[][]{
                {7,0},{4,4},{7,1},{5,0},{6,1},{5,2}
        });
    }
}
