package medium;

public class MinimumArrayEnd {
    public long minEnd(int n, int x) {
        long result = x;
        for (int i = 1; i < n; i++)
            result = (result + 1) | x;
        return result;
    }

    public static void main(String[] args) {
        MinimumArrayEnd M = new MinimumArrayEnd();
        System.out.println(M.minEnd(3, 4));
        System.out.println(M.minEnd(2, 7));
    }
}
