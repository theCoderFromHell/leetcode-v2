package medium;

public class FindTheKThLuckyNumber {
    public String kthLuckyNumber(int k) {
        if (k == 1)
            return "4";
        if (k == 2)
            return "7";
        int current = 0;
        int multiple = 2;
        int digits = 1;
        while (current + multiple < k) {
            current += multiple;
            multiple *= 2;
            digits++;
        }
        return calculate(k - current, digits);
    }

    private String calculate(int k, int digits) {
        StringBuilder sb = new StringBuilder();
        while (digits-- > 0) {
            sb.append((k % 2) == 0 ? "7" : "4");
            k = (k + 1)/2;
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        FindTheKThLuckyNumber F = new FindTheKThLuckyNumber();
        System.out.println(F.kthLuckyNumber(1000));
        System.out.println(F.kthLuckyNumber(10));
    }
}
