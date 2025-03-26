package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class BinaryTreeVerticalOrderTraversal {
    Integer min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    public List<List<Integer>> verticalOrder(TreeNode root) {
        HashMap<Integer, List<Value>> columns = new HashMap<>();
        traverse(root, columns, 0, 0);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            List<Value> values = columns.get(i);
            values.sort(Comparator.comparingInt(o -> o.level));
            List<Integer> sortedList = values.stream().map(o -> o.value).toList();
            result.add(sortedList);
        }
        return result;
    }

    private void traverse(TreeNode root, HashMap<Integer, List<Value>> columns, int width, int level) {
        if (root == null)
            return;
        min = Math.min(min, width);
        max = Math.max(max, width);
        columns.computeIfAbsent(width, f -> new ArrayList<>()).add(new Value(root.val, level));
        traverse(root.left, columns,  width-1, level+1);
        traverse(root.right, columns, width+1, level+1);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(10);
        root.right.left = new TreeNode(9);
        root.right.right = new TreeNode(11);
        root.left.left.right = new TreeNode(5);
        root.left.left.right.right = new TreeNode(6);
        BinaryTreeVerticalOrderTraversal B = new BinaryTreeVerticalOrderTraversal();
        B.verticalOrder(root);
    }

    class Value {
        Integer value;
        Integer level;

        public Value(Integer value, Integer level) {
            this.value = value;
            this.level = level;
        }

        @Override
        public String toString() {
            return "Value{" +
                    "value=" + value +
                    ", level=" + level +
                    '}';
        }
    }
}
