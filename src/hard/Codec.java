package hard;

import java.util.HashMap;
import java.util.Objects;

public class Codec {

    Index index;
    String preOrder = "";
    String inOrder = "";
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {;
        preOrder = "";
        inOrder = "";
        HashMap<Integer, Integer> nodes = new HashMap<>();
        preOrder(root, nodes);
        nodes = new HashMap<>();
        inOrder(root, nodes);
        return preOrder + "#" + inOrder;
    }

    private void inOrder(TreeNode root, HashMap<Integer, Integer> nodes) {
        if (root == null)
            return;
        int count = nodes.getOrDefault(root.val, 0);
        nodes.put(root.val, count+1);
        inOrder(root.left, nodes);
        if (!Objects.equals(inOrder, ""))
            inOrder += ",";
        inOrder += root.val + "@" + (count + 1);
        inOrder(root.right, nodes);
    }

    private void preOrder(TreeNode root, HashMap<Integer, Integer> nodes) {
        if (root == null)
            return;
        if (!Objects.equals(preOrder, ""))
            preOrder += ",";
        int count = nodes.getOrDefault(root.val, 0);
        preOrder += root.val + "@" + (count + 1);
        nodes.put(root.val, count+1);
        preOrder(root.left, nodes);
        preOrder(root.right, nodes);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if ("#".equals(data))
            return null;
        System.out.println(data);
        String[] traversal = data.split("#");
        String[] preOrderPart = traversal[0].split(",");
        String[] ineOrderPart = traversal[1].split(",");
        if (preOrderPart.length != ineOrderPart.length)
            return null;
        return fromPreAndIn(preOrderPart, ineOrderPart);
    }

    public TreeNode fromPreAndIn(String[] preorder, String[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length)
            return null;
        int N = preorder.length;
        index = new Index(0);
        return solve(preorder, inorder, index, 0, N-1, N);
    }

    private TreeNode solve(String[] preorder, String[] inorder, Index index, int inStart, int inEnd, int N) {
        if (inStart > inEnd || index.index >= N)
            return null;
        if (inStart == inEnd)
            return new TreeNode(value(preorder[index.index]));
        String  current = preorder[index.index];
        TreeNode root = new TreeNode(value(current));
        int inorderIndex = inorderIndex(inorder, inStart, inEnd, current);
        if (inorderIndex != -1) {
            if (!(inStart > inorderIndex-1 || index.index+1 >= N)) {
                index.index = index.index + 1;
                root.left = solve(preorder, inorder, index, inStart, inorderIndex - 1, N);
            }
            if (!(inorderIndex+1 > inEnd || index.index+1 >= N)) {
                index.index = index.index + 1;
                root.right = solve(preorder, inorder, index, inorderIndex + 1, inEnd, N);
            }
        }
        return root;
    }

    private int value(String s) {
        String[] values = s.split("@");
        return Integer.parseInt(values[0]);
    }

    private int inorderIndex(String[] inorder, int inStart, int inEnd, String current) {
        for (int i = inStart; i <= inEnd; i++) {
            if (Objects.equals(inorder[i], current))
                return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(-1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);
        root.right.left.left = new TreeNode(6);
        root.right.left.right = new TreeNode(7);

         Codec ser = new Codec();
         Codec deser = new Codec();
         TreeNode ans = deser.deserialize(ser.serialize(root));
         System.out.println(ans);
    }
}
class Index {
    public Integer index;

    public Index(Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Index{" +
                "index=" + index +
                '}';
    }
}
