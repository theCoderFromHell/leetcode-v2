package common;
public class DoubleListNode {
    public int val;
    public DoubleListNode left;
    public DoubleListNode right;

    public DoubleListNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }


    @Override
    public String toString() {
        return "DoubleListNode{" +
                "val=" + val +
                ", left=" + (left == null ? "null" : left.val) +
                ", right=" + (right == null ? "null" : right.val) +
                '}';
    }
}
