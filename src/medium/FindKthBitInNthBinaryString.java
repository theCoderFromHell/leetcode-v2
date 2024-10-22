package medium;

public class FindKthBitInNthBinaryString {
    public char findKthBit(int n, int k) {
        if (n == 1)
            return '0';
        if (n == 2)
            return (k == 1) ? '0' : '1';
        long digits = pow(2, n) - 1;
        if (k <= digits/2)
            return findKthBit(n-1, k);
        if (k == digits/2 + 1)
            return '1';
        return invert(findKthBit(n-1, (int)(digits - k + 1)));
    }

    private char invert(char bit) {
        return (bit == '0') ? '1' : '0';
    }

    long pow(int base, int power) {
        long result = 1;
        for (int i = 0; i < power; i++)
            result *= base;
        return result;
    }

    public static void main(String[] args) {
        FindKthBitInNthBinaryString F = new FindKthBitInNthBinaryString();
        System.out.println(F.findKthBit(3, 1));
        System.out.println(F.findKthBit(4, 11));
    }
}
