package medium;

public class FindTheWinnerOfTheCircularGame {
    public int findTheWinner(int n, int k) {
        boolean[] players = new boolean[n];
        for (int i = 0; i < n; i++) {
            players[i] = true;
        }
        int remainingPlayers = n;
        int currentIndex = 0;
        while (remainingPlayers > 1) {
            int count = 0;
            while (count < k) {
                if (players[currentIndex]) {
                    count++;
                }
                currentIndex = (currentIndex + 1) % n;
            }
            players[(currentIndex - 1 + n) % n] = false;
            remainingPlayers--;
        }
        for (int i = 0; i < n; i++) {
            if (players[i]) {
                return i + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        FindTheWinnerOfTheCircularGame solver = new FindTheWinnerOfTheCircularGame();
        System.out.println(solver.findTheWinner(5, 2));
        System.out.println(solver.findTheWinner(5, 4));
        System.out.println(solver.findTheWinner(6, 2));
        System.out.println(solver.findTheWinner(6, 4));
    }
}
