package medium;

public class BrokenCalculator {
    public int brokenCalc(int startValue, int target) {
        if (target == startValue)
            return 0;
        if (target < startValue)
            return startValue - target;
        int count = 0;
        while (target > startValue) {
            count++;
            if (target % 2 == 0)
                target /= 2;
            else
                target++;
        }
        return count + (startValue - target);
    }

    public static void main(String[] args) {
        BrokenCalculator B = new BrokenCalculator();
        System.out.println(B.brokenCalc(2, 3));
        System.out.println(B.brokenCalc(5, 8));
        System.out.println(B.brokenCalc(3, 10));
        System.out.println(B.brokenCalc(1, 1024));
    }
}
