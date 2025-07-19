package medium;

import java.util.HashMap;

public class CloneBinaryTreeWithRandomPointer {
    public NodeCopy copyRandomBinaryTree(Node root) {
        if (root == null)
            return null;
        if (root.left == null && root.right == null && root.random == null)
            return new NodeCopy(root.val, null, null, null);
        HashMap<Node, NodeCopy> nodeMap = new HashMap<>();
        return copy(root, nodeMap);
    }

    private NodeCopy copy(Node root, HashMap<Node, NodeCopy> nodeMap) {
        if (root == null)
            return null;
        NodeCopy nodeCopy = nodeMap.getOrDefault(root, new NodeCopy());
        nodeCopy.val = root.val;
        nodeMap.put(root, nodeCopy);
        NodeCopy random = null;
        if (root.random != null) {
            random = nodeMap.getOrDefault(root.random, new NodeCopy());
            random.val = root.random.val;
            nodeMap.put(root.random, random);
        }
        nodeCopy.random = random;
        nodeCopy.left = copy(root.left, nodeMap);
        nodeCopy.right = copy(root.right, nodeMap);
        return nodeCopy;
    }
}

class Node {
    int val;
    Node left;
    Node right;
    Node random;

    Node() {}
    Node(int val, Node left, Node right, Node random) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.random = random;
    }
}

class NodeCopy {
    int val;
    NodeCopy left;
    NodeCopy right;
    NodeCopy random;

    NodeCopy() {}
    NodeCopy(int val, NodeCopy left, NodeCopy right, NodeCopy random) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.random = random;
    }
}

