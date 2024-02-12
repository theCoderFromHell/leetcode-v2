package medium;
import common.TreeNode;

public class ConstructStringFromBinaryTree {
    public static String tree2str(TreeNode root) {
        StringBuilder builder = new StringBuilder();
        createString(root, builder);
        String result =  builder.toString();
        return result.substring(1, result.length()-1);
    }

    private static void createString(TreeNode root, StringBuilder builder) {
        builder.append("(").append(root.val);
        if (root.left != null || root.right != null) {
            if (root.left == null) {
                builder.append("()");
                createString(root.right, builder);
            }
            else {
                createString(root.left, builder);
                if (root.right != null)
                    createString(root.right, builder);
            }
        }
        builder.append(")");
    }

    public static void main(String[] args) {

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        System.out.println(tree2str(root));


        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.right = new TreeNode(4);
        System.out.println(tree2str(root2));

    }
}
