package medium;

public class RectangleArea {
    public int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        int area1 = Math.abs(ax2 - ax1) * Math.abs(ay2 - ay1);
        int area2 = Math.abs(bx2 - bx1) * Math.abs(by2 - by1);

        int xOverlap = Math.min(ax2, bx2) - Math.max(ax1, bx1);
        int yOverlap = Math.min(ay2, by2) - Math.max(ay1, by1);
        int overlapArea = 0;
        if (xOverlap > 0 && yOverlap > 0)
            overlapArea = xOverlap * yOverlap;
        return area1 + area2 - overlapArea;
    }

    public static void main(String[] args) {
        RectangleArea R = new RectangleArea();
        System.out.println(R.computeArea(-3, 0, 3, 4, 0, -1, 9, 2));
    }
}
