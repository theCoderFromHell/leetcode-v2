package medium;

public class MinimumFlipsToMakeAORBEqualToC {
    public int minFlips(int a, int b, int c) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            int aBit = a & 1;
            int bBit = b & 1;
            int cBit = c & 1;
            if ((aBit | bBit) != cBit)
                result += (aBit + bBit + cBit);
            a >>= 1;
            b >>= 1;
            c >>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        MinimumFlipsToMakeAORBEqualToC M = new MinimumFlipsToMakeAORBEqualToC();
        System.out.println(M.minFlips(2, 6,5));
        System.out.println(M.minFlips(4, 2, 7));
        System.out.println(M.minFlips(1, 2, 3));
    }
}
