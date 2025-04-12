package medium;

import java.util.Comparator;
import java.util.PriorityQueue;

public class KthSmallestElementInASortedMatrix {
    public int kthSmallest(int[][] matrix, int k) {
        int size = matrix.length;
        PriorityQueue<Box> pq = new PriorityQueue<>((Comparator.comparingInt(o -> o.value)));
        for (int i = 0; i < size; i++)
            pq.add(new Box(matrix[i][0], i, 0));
        Box box;
        while (!pq.isEmpty() && k-- > 0) {
            box = pq.poll();
            if (k == 0)
                return box.value;;
            if (box.column < size-1)
                pq.add(new Box(matrix[box.row][box.column + 1], box.row, box.column + 1));
        }
        return -1;
    }

    class Box {
        int value;
        int row;
        int column;

        public Box(int value, int row, int column) {
            this.value = value;
            this.row = row;
            this.column = column;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "value=" + value +
                    ", row=" + row +
                    ", column=" + column +
                    '}';
        }
    }

    public static void main(String[] args) {
        KthSmallestElementInASortedMatrix K = new KthSmallestElementInASortedMatrix();
        System.out.println(K.kthSmallest(new int[][]{{1,5,9},{10,11,13},{12,13,15}}, 8));
        System.out.println(K.kthSmallest(new int[][]{{-5}}, 1));
    }
}
