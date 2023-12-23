package common;

public class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "val=" + val +
                ", left=" + left +
                ", right=" + right +
                ", next=" + next +
                '}';
    }
}




//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Node {
//    public int val;
//    public List<Node> neighbors;
//    public Node() {
//        val = 0;
//        neighbors = new ArrayList<>();
//    }
//    public Node(int value) {
//        val = value;
//        neighbors = new ArrayList<>();
//    }
//    public Node(int value, ArrayList<Node> neighbors) {
//        val = value;
//        this.neighbors = neighbors;
//    }
//
//    @Override
//    public String toString() {
//        return "Node{" +
//                "val=" + val +
//                ", neighbors=" + neighbors(this) +
//                '}';
//    }
//
//    private String neighbors(Node node) {
//        List<Node> neighbors = node.neighbors;
//        StringBuilder list = new StringBuilder();
//        for(Node neighbor : neighbors) {
//            if(neighbor != null)
//                list.append(neighbor.val).append(" ");
//        }
//        return list.toString();
//    }
//}
