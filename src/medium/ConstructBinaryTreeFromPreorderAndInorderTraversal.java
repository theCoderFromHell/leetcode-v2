package medium;

import common.TreeNode;

public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    private int idx = 0;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder == null || inorder == null)
            return null;
        if(preorder.length != inorder.length)
            return null;
        int length = preorder.length;
        return constructTree(preorder, inorder, length, 0, length-1);
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
