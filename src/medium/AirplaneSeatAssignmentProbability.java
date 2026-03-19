package medium;

public class AirplaneSeatAssignmentProbability {
    public double nthPersonGetsNthSeat(int n) {
        if (n == 1)
            return 1d;
        return (double) 1 / 2;
    }
}
