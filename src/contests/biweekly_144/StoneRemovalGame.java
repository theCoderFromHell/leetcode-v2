package contests.biweekly_144;

public class StoneRemovalGame {
    public boolean canAliceWin(int n) {
        if (n < 10)
            return false;
        n -= 10;
        boolean turn = false;
        int k = 9;
        while (n >= 0 && k >= 1) {
            if (turn && n < k)
                return false;
            if (!turn && n < k)
                return true;
            n = Math.max(0, n - k);
            k--;
            turn = !turn;
        }
        return true;
    }

    public static void main(String[] args) {
        StoneRemovalGame S = new StoneRemovalGame();
        System.out.println(S.canAliceWin(12));
        System.out.println(S.canAliceWin(1));
        System.out.println(S.canAliceWin(50));
        System.out.println(S.canAliceWin(30));
        System.out.println(S.canAliceWin(20));
        System.out.println(S.canAliceWin(16));
        System.out.println(S.canAliceWin(21));
    }
}
