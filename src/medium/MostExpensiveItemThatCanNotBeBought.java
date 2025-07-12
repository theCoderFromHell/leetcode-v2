package medium;

public class MostExpensiveItemThatCanNotBeBought {
    public int mostExpensiveItem(int primeOne, int primeTwo) {
        return (primeOne * (primeTwo - 1) + primeTwo * (primeOne - 1) - primeOne * primeTwo);
    }

    public static void main(String[] args) {
        MostExpensiveItemThatCanNotBeBought M = new MostExpensiveItemThatCanNotBeBought();
        System.out.println(M.mostExpensiveItem(2, 5));
        System.out.println(M.mostExpensiveItem(5, 7));
        System.out.println(M.mostExpensiveItem(11, 29));
        System.out.println(M.mostExpensiveItem(31, 13));
    }
}
