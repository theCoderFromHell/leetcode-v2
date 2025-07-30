package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class AllElementsInTwoBinarySearchTrees {
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> result = new ArrayList<>();
        List<Integer> elements1 = new ArrayList<>();
        pickNodes(root1, elements1);
        List<Integer> elements2 = new ArrayList<>();
        pickNodes(root2, elements2);
        int size1 = elements1.size();
        int size2 = elements2.size();
        int index1 = 0, index2 = 0;
        while (index1 < size1 && index2 < size2) {
            if (elements1.get(index1) <= elements2.get(index2)) {
                result.add(elements1.get(index1));
                index1++;
            } else {
                result.add(elements2.get(index2));
                index2++;
            }
        }
        while (index1 < size1) {
            result.add(elements1.get(index1));
            index1++;
        }
        while (index2 < size2) {
            result.add(elements2.get(index2));
            index2++;
        }
        return result;
    }

    private void pickNodes(TreeNode root, List<Integer> elements) {
        if (root == null)
            return;
        pickNodes(root.left, elements);
        elements.add(root.val);
        pickNodes(root.right, elements);
    }
}
