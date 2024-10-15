package medium;

public class DividePlayersIntoTeamsOfEqualSkill {
    public long dividePlayers(int[] skill) {
        int N = skill.length;
        int sum = 0;
        int[] hash = new int[2001];
        for (int i = 0; i < N; i++) {
            sum += skill[i];
            hash[skill[i]]++;
        }
        int scoreOfTeam = 2 * sum / N;
        if (scoreOfTeam > 2000)
            return -1;
        long result = 0;
        for (int i = 1; i <= 1000; i++) {
            while (hash[i] > 0) {
                if (hash[scoreOfTeam - i] <= 0)
                    return -1;
                result += ((long) i * (scoreOfTeam - i));
                hash[i]--;
                hash[scoreOfTeam - i]--;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        DividePlayersIntoTeamsOfEqualSkill D = new DividePlayersIntoTeamsOfEqualSkill();
        System.out.println(D.dividePlayers(new int[]{3,2,5,1,3,4}));
        System.out.println(D.dividePlayers(new int[]{3,4}));
    }
}
