package medium;

import java.util.PriorityQueue;

public class SeatReservationManager {
    public static void main(String[] args) {
        SeatManager S = new SeatManager(5);
        System.out.println("Reserved seat " + S.reserve());
        System.out.println("Reserved seat " + S.reserve());
        S.unreserve(2);
        System.out.println("Reserved seat " + S.reserve());
        System.out.println("Reserved seat " + S.reserve());
        System.out.println("Reserved seat " + S.reserve());
        System.out.println("Reserved seat " + S.reserve());
        S.unreserve(5);
    }
}
class SeatManager {
    PriorityQueue<Integer> pq;
    int N;
    public SeatManager(int n) {
        this.N = n;
        this.pq = new PriorityQueue<>();
        for (int i = 1; i <= N; i++)
            pq.add(i);
    }

    public int reserve() {
        return pq.poll();
    }

    public void unreserve(int seatNumber) {
        pq.add(seatNumber);
    }
}
