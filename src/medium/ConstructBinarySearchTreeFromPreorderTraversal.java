package medium;

import common.TreeNode;

import java.util.Arrays;

public class ConstructBinarySearchTreeFromPreorderTraversal {
    private int idx = 0;
    public TreeNode bstFromPreorder(int[] preorder) {
        int size = preorder.length;
        TreeNode root = new TreeNode(preorder[0]);
        for (int i = 1; i < size; i++)
            addNodeToBST(root, preorder[i]);
        return root;
    }

    private TreeNode addNodeToBST(TreeNode root, int value) {
        if (root == null)
            return new TreeNode(value);
        if (value < root.val)
            root.left = addNodeToBST(root.left, value);
        else
            root.right = addNodeToBST(root.right, value);
        return root;
    }

    public TreeNode bstFromPreorderV2(int[] preorder) {
        int size = preorder.length;
        int[] inorder = Arrays.copyOfRange(preorder, 0, size);
        Arrays.sort(inorder);
        return constructTree(preorder, inorder, size, 0, size-1);
    }

    private TreeNode constructTree(int[] preorder, int[] inorder, int length, int inStart, int inEnd) {
        if(idx >= length)
            return null;
        TreeNode treeNode = new TreeNode(preorder[idx]);
        int index = searchNodeInInorder(inorder, inStart, inEnd, preorder[idx]);
        idx++;
        treeNode.left = index > inStart ? constructTree(preorder, inorder, length, inStart, index - 1) : null;
        treeNode.right = index < inEnd ? constructTree(preorder, inorder,length, index+1, inEnd) : null;
        return treeNode;
    }

    private int searchNodeInInorder(int[] inorder, int inStart, int inEnd, int nodeValue) {
        for(int i=inStart; i<=inEnd; i++) {
            if(inorder[i] == nodeValue)
                return i;
        }
        return -1;
    }
}
