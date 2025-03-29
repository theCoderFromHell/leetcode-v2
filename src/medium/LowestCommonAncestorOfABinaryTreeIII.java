package medium;


import java.util.HashSet;

public class LowestCommonAncestorOfABinaryTreeIII {
    public Node lowestCommonAncestor(Node p, Node q) {
        if (p == null || q == null)
            return null;
        HashSet<Node> pParents = new HashSet<>();
        HashSet<Node> qParents = new HashSet<>();
        Node parent = p;
        while (parent != null) {
            pParents.add(parent);
            parent = parent.parent;
        }
        parent = q;
        while (parent != null) {
            if (pParents.contains(parent))
                return parent;
            qParents.add(parent);
            parent = parent.parent;
        }
        return null;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }
}
