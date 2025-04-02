package medium;

public class ConstructQuadTree {
    public Node construct(int[][] grid) {
        int N = grid.length;
        int[] topLeft = new int[]{0, 0};
        int[] bottomRight = new int[]{N-1, N-1};
        return buildQuadTree(grid, topLeft, bottomRight);
    }

    private Node buildQuadTree(int[][] grid, int[] topLeft, int[] bottomRight) {
        int x1 = topLeft[0];
        int y1 = topLeft[1];
        int x2 = bottomRight[0];
        int y2 = bottomRight[1];
        int value = grid[x1][y1];
        if (x1 == x2 && y1 == y2)
            return new Node(value == 1, true);
        if (sameValues(grid, x1, y1, x2, y2, value))
            return new Node(value == 1, true);
        Node root = new Node(value == 1, false);
        root.topLeft = buildQuadTree(grid, new int[]{x1, y1}, new int[]{(x1 + x2)/2, (y1 + y2)/2});
        root.topRight = buildQuadTree(grid, new int[]{x1, 1 + (y1 + y2)/2}, new int[]{(x1 + x2)/2, y2});
        root.bottomLeft = buildQuadTree(grid, new int[]{1 + (x1 + x2)/2, y1}, new int[]{x2, (y1 + y2)/2});
        root.bottomRight = buildQuadTree(grid, new int[]{1 + (x1 + x2)/2, 1 + (y1 + y2)/2}, new int[]{x2, y2});
        return root;
    }

    private boolean sameValues(int[][] grid, int x1, int y1, int x2, int y2, int value) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                if (grid[i][j] != value)
                    return false;
            }
        }
        return true;
    }

    class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;

        public Node(boolean val, boolean isLeaf) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", isLeaf=" + isLeaf +
                    ", topLeft=" + topLeft +
                    ", topRight=" + topRight +
                    ", bottomLeft=" + bottomLeft +
                    ", bottomRight=" + bottomRight +
                    '}';
        }
    }

    public static void main(String[] args) {
        ConstructQuadTree C = new ConstructQuadTree();
        System.out.println(C.construct(new int[][]{
                {1,1,1,1,0,0,0,0},
                {1,1,1,1,0,0,0,0},
                {1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1},
                {1,1,1,1,0,0,0,0},
                {1,1,1,1,0,0,0,0},
                {1,1,1,1,0,0,0,0},
                {1,1,1,1,0,0,0,0}}));
    }

}
