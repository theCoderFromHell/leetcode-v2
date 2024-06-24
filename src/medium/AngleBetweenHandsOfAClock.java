package medium;

public class AngleBetweenHandsOfAClock {
    public double angleClock(int hour, int minutes) {
        double value = Math.abs(hour * (30.0d) + minutes * 0.5d - 6.0d * minutes);
        return Math.min(value, Math.abs(360.0 - value));
    }

    public static void main(String[] args) {
        AngleBetweenHandsOfAClock a = new AngleBetweenHandsOfAClock();
        System.out.println(a.angleClock(12, 30));
        System.out.println(a.angleClock(3, 30));
        System.out.println(a.angleClock(3, 15));
        System.out.println(a.angleClock(8, 50));

    }
}
