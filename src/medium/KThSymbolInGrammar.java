package medium;

public class KThSymbolInGrammar {
    public int kthGrammar(int n, int k) {
        return findKthDigit (n, k-1);
    }

    private int findKthDigit(int n, int k) {
        if (n == 1)
            return 0;
        if (n == 2)
            return k;
        int index = findKthDigit(n-1, k/2);
        if (index == 0)
            return (k % 2 == 0) ? 0 : 1;
        else if (index == 1)
            return (k % 2 == 0) ? 1 : 0;
        return -1;
    }

    public static void main(String[] args) {
        KThSymbolInGrammar K = new KThSymbolInGrammar();
        System.out.println(K.kthGrammar(1,1));
        System.out.println(K.kthGrammar(2,1));
        System.out.println(K.kthGrammar(2,2));
        System.out.println(K.kthGrammar(4, 6));
    }
}
