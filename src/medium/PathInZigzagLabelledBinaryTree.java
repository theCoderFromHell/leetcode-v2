package medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathInZigzagLabelledBinaryTree {
    public List<Integer> pathInZigZagTree(int label) {
        List<Integer> result = new ArrayList<>();
        while (label > 0) {
            result.add(label);
            label = (label % 2 == 0) ? label/2 : (label -1)/2 ;
        }
        int size = result.size();
        Collections.reverse(result);
        for (int i = size-2; i >= 0; i-= 2)
            result.set(i, (3 * (1 << i) - 1) - result.get(i));
        return result;
    }

    public static void main(String[] args) {
        PathInZigzagLabelledBinaryTree P = new PathInZigzagLabelledBinaryTree();

        System.out.print("Test 1: ");
        printList(P.pathInZigZagTree(14));

        System.out.print("Test 2: ");
        printList(P.pathInZigZagTree(26));

        System.out.print("Test 3: ");
        printList(P.pathInZigZagTree(1));

        System.out.print("Test 4: ");
        printList(P.pathInZigZagTree(2));

        System.out.print("Test 5: ");
        printList(P.pathInZigZagTree(3));

        System.out.print("Test 6: ");
        printList(P.pathInZigZagTree(1000000));
    }

    private static void printList(List<Integer> list) {
        System.out.println(list);
    }
}