package medium;

public class FindThePunishmentNumberOfAnInteger {
    public int punishmentNumber(int n) {
        int result = 0;
        int square;
        for (int i = 1; i <= n; i++) {
            square = i*i;
            String s = String.valueOf(square);
            int length = s.length();
            if (makesTheCut(s, 0, length, 0, i))
                result += square;
        }
        return result;
    }

    private boolean makesTheCut(String s, int index, int size, int sum, int originalNumber) {
        if (index >= size)
            return sum == originalNumber;
        for (int i = index; i < size; i++) {
            int curr = Integer.parseInt(s.substring(index, i+1));
            if (makesTheCut(s, i+1, size, sum + curr, originalNumber))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        FindThePunishmentNumberOfAnInteger F = new FindThePunishmentNumberOfAnInteger();
        System.out.println(F.punishmentNumber(10));
        System.out.println(F.punishmentNumber(37));
    }
}
