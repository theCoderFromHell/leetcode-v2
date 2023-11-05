package easy;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TopVotedCandidate {
    TreeMap<Integer, Integer> winnerByTime;
    public TopVotedCandidate(int[] persons, int[] times) {
        int N = persons.length;
        HashMap<Integer, Votes> personVotes =  new HashMap<>();
        winnerByTime = new TreeMap<>();
        for (int i = 0; i < N; i++) {
            Votes votes = personVotes.getOrDefault(persons[i], new Votes());
            votes.votes =  votes.votes + 1;
            votes.latestTime = times[i];
            personVotes.put(persons[i], votes);
            int highestVotedPerson = -1;
            int highestVotes = -1;
            for (Integer p : personVotes.keySet()) {
                Votes vote = personVotes.get(p);
                if (vote.votes > highestVotes) {
                    highestVotedPerson = p;
                    highestVotes = vote.votes;
                } else if (vote.votes == highestVotes) {
                    Votes vote2 = personVotes.get(highestVotedPerson);
                    if (vote.latestTime > vote2.latestTime)
                        highestVotedPerson = p;
                }
            }
            winnerByTime.put(times[i], highestVotedPerson);
        }
    }
    public int q(int t) {
        Map.Entry<Integer, Integer> low = winnerByTime.floorEntry(t);
        return low.getValue();
    }

    public static void main(String[] args) {

    }
}
class Votes {
    int votes;
    int latestTime;
}
