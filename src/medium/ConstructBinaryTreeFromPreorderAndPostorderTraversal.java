package medium;

import common.TreeNode;

public class ConstructBinaryTreeFromPreorderAndPostorderTraversal {
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        int size = preorder.length;
        return construct(preorder, 0, size-1, postorder,  0, size-1);
    }

    private TreeNode construct(int[] preorder, int preStart, int preEnd, int[] postorder, int postStart, int postEnd) {
        if (preStart > preEnd || postStart > postEnd)
            return null;
        if (preStart == preEnd && postStart == postEnd)
            return new TreeNode(preorder[preStart]);
        TreeNode root = new TreeNode(preorder[preStart]);
        if (preorder[preStart+1] == postorder[postEnd-1]) {
            root.left = construct(preorder, preStart+1, preEnd, postorder, postStart, postEnd-1);
            return root;
        }
        int indexInPost = find(preorder[preStart+1], postorder, postStart, postEnd);
        int indexInPre = find(postorder[postEnd-1], preorder, preStart, preEnd);
        root.left = construct(preorder, preStart+1, indexInPre-1, postorder, postStart, indexInPost);
        root.right = construct(preorder, indexInPre, preEnd, postorder, indexInPost+1, postEnd-1);
        return root;
    }

    private int find(int value, int[] postorder, int postStart, int postEnd) {
        for (int i = postStart; i <= postEnd; i++) {
            if (postorder[i] == value)
                return i;
        }
        return -1;
    }
}
