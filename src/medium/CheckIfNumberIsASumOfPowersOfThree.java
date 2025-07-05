package medium;

public class CheckIfNumberIsASumOfPowersOfThree {
    public boolean checkPowersOfThree(int n) {
        if (n == 1)
            return true;
        if (n % 3 != 0) {
            if (n % 3 != 1)
                return false;
            n--;
        }
        while (n % 3 == 0)
            n /= 3;
        return checkPowersOfThree(n);
    }

    public static void main(String[] args) {
        CheckIfNumberIsASumOfPowersOfThree C = new CheckIfNumberIsASumOfPowersOfThree();
        System.out.println(C.checkPowersOfThree(12));
        System.out.println(C.checkPowersOfThree(91));
        System.out.println(C.checkPowersOfThree(21));
    }
}
