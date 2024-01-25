package medium;

public class MinimumPenaltyForAShop {
    public int bestClosingTime(String customers) {
        int N = customers.length();
        int penalty = 0;
        for (char c : customers.toCharArray()) {
            if (c == 'Y')
                penalty++;
        }
        int lowestPenalty = penalty;
        int closingHour = 0;
        for (int i = 0; i < N; i++) {
            char c = customers.charAt(i);
            switch (c) {
                case 'Y' -> penalty--;
                case 'N' -> penalty++;
            }
            if (penalty < lowestPenalty) {
                lowestPenalty = penalty;
                closingHour = i+1;
            }
        }
        return closingHour;
    }

    public static void main(String[] args) {

    }
}
