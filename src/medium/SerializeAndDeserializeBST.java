package medium;

import common.TreeNode;

import java.util.Arrays;

public class SerializeAndDeserializeBST {
    int index = 0;
    StringBuilder data = new StringBuilder();
    public String serialize(TreeNode root) {
        preOrder(root);
        return data.toString();
    }

    private void preOrder(TreeNode root) {
        if (root == null)
            return;
        if (data.isEmpty())
            data.append(root.val);
        else
            data.append("#").append(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty())
            return null;
        if (!data.contains("#"))
            return new TreeNode(Integer.parseInt(data));
        String[] textNodes = data.split("#");
        int N = textNodes.length;
        int[] preOrder = new int[N];
        for (int i = 0; i < N; i++)
            preOrder[i] = Integer.parseInt(textNodes[i]);

        int[] inOrder = Arrays.copyOfRange(preOrder, 0, preOrder.length);
        Arrays.sort(inOrder);

        return binaryTreeFromPreAndIn(preOrder, inOrder, N, 0,N-1);
    }

    private TreeNode binaryTreeFromPreAndIn(int[] preOrder, int[] inOrder, int N, int start, int end) {
        if (start < 0 || end >= N || start > end)
            return null;
        if (index == N)
            return null;
        TreeNode root = new TreeNode(preOrder[index]);
        int inOrderIndex = Arrays.binarySearch(inOrder, preOrder[index]);
        index++;
        root.left = binaryTreeFromPreAndIn(preOrder, inOrder, N, start, inOrderIndex-1);
        root.right = binaryTreeFromPreAndIn(preOrder, inOrder, N, inOrderIndex+1, end);
        return root;
    }

    public static void main(String[] args) {
        SerializeAndDeserializeBST S = new SerializeAndDeserializeBST();

        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);

        String serialized = S.serialize(root);
        TreeNode newRoot = S.deserialize(serialized);

        System.out.println(isSameTree(root, newRoot));
    }

    private static boolean isSameTree(TreeNode root, TreeNode newRoot) {
        if (root == null && newRoot == null)
            return true;
        if (root == null || newRoot == null)
            return false;
        if (root.val != newRoot.val)
            return false;
        return isSameTree(root.left, newRoot.left) && isSameTree(root.right, newRoot.right);
    }
}
