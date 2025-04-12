package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SmallestRangeCoveringElementsFromKLists {
    public int[] smallestRange(List<List<Integer>> nums) {
        int size = nums.size();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.value));
        int maxRange = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            pq.add(new Node(nums.get(i).get(0), i, 0));
            maxRange = Math.max(maxRange, nums.get(i).get(0));
        }
        int resultStart = 0, resultEnd = Integer.MAX_VALUE;
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            if (maxRange - node.value < resultEnd - resultStart) {
                resultStart = node.value;
                resultEnd = maxRange;
            }
            if (node.column < nums.get(node.row).size()-1) {
                pq.add(new Node(nums.get(node.row).get(node.column + 1), node.row, node.column+1));
                maxRange = Math.max(maxRange, nums.get(node.row).get(node.column + 1));
            } else
                break;
        }
        return new int[]{resultStart, resultEnd};
    }

    class Node {
        int value;
        int row;
        int column;

        public Node(int value, int row, int column) {
            this.value = value;
            this.row = row;
            this.column = column;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", row=" + row +
                    ", column=" + column +
                    '}';
        }
    }

    public static void main(String[] args) {
        SmallestRangeCoveringElementsFromKLists S = new SmallestRangeCoveringElementsFromKLists();
        System.out.println(Arrays.toString(S.smallestRange(List.of(List.of(4,10,15,24,26), List.of(0,9,12,20), List.of(5,18,22,30)))));
        System.out.println(Arrays.toString(S.smallestRange(List.of(List.of(1,2,3), List.of(1,2,3), List.of(1,2,3)))));
    }
}
