package contests.biweekly_138;

public class FindTheKeyOfTheNumbers {
    public int generateKey(int num1, int num2, int num3) {
        int result = 0, factor = 1;
        for (int i = 0; i < 4; i++) {
            int value = Math.min(num1%10,Math.min(num2%10, num3%10));
            num1 /= 10;
            num2 /= 10;
            num3 /= 10;
            result += value*factor;
            factor*= 10;
        }
        return result;
    }

    public static void main(String[] args) {
        FindTheKeyOfTheNumbers F = new FindTheKeyOfTheNumbers();
        System.out.println(F.generateKey(987,
                879,
                798));
        System.out.println(F.generateKey(1,
                10,
                1000));
    }
}
