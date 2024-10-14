package medium;

import common.TreeNode;

import java.util.HashMap;

public class ConstructBinaryTreeFromInorderAndPostorderTraversal {
    int index;
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int N = inorder.length;
        index = N;
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < N; i++)
            map.put(inorder[i], i);
        TreeNode root = createTree(inorder, postorder, 0, N-1, map);
        return root;
    }

    private TreeNode createTree(int[] inorder, int[] postorder, int start, int end, HashMap<Integer,Integer> map) {
        if (start > end)
            return null;
        index--;
        if (start == end)
            return new TreeNode(postorder[index]);
        TreeNode root = new TreeNode(postorder[index]);
        int idx = map.get(postorder[index]);
        root.right = createTree(inorder, postorder, idx+1, end, map);
        root.left = createTree(inorder, postorder, start, idx-1, map);
        return root;
    }

    public static void main(String[] args) {
        ConstructBinaryTreeFromInorderAndPostorderTraversal C = new ConstructBinaryTreeFromInorderAndPostorderTraversal();
        System.out.println(C.buildTree(new int[]{9,3,15,20,7}, new int[]{9,15,7,20,3}));
    }
}
