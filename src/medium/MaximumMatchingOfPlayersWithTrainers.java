package medium;

import java.util.Arrays;

public class MaximumMatchingOfPlayersWithTrainers {
    public int matchPlayersAndTrainers(int[] players, int[] trainers) {
        int pSize = players.length;
        int tSize = trainers.length;
        Arrays.sort(players);
        Arrays.sort(trainers);
        int count = 0;
        int pIndex = 0, tIndex = 0;
        while (pIndex < pSize && tIndex < tSize) {
            if (players[pIndex] <= trainers[tIndex]) {
                count++;
                pIndex++;
            }
            tIndex++;
        }
        return count;
    }

    public static void main(String[] args) {
        MaximumMatchingOfPlayersWithTrainers M = new MaximumMatchingOfPlayersWithTrainers();
        System.out.println(M.matchPlayersAndTrainers(new int[]{4,7,9}, new int[]{8,2,5,8}));
        System.out.println(M.matchPlayersAndTrainers(new int[]{1,1,1}, new int[]{10}));
    }

}
