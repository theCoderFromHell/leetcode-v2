package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GroupThePeopleGivenTheGroupSizeTheyBelongTo {
    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        int size = groupSizes.length;
        HashMap<Integer, LinkedList<Integer>> peopleForSize = new HashMap<>();
        for (int i = 0; i < size; i++) {
            LinkedList<Integer> people = peopleForSize.getOrDefault(groupSizes[i], new LinkedList<>());
            people.add(i);
            peopleForSize.put(groupSizes[i], people);
        }
        List<List<Integer>> result = new ArrayList<>();
        for (int groupSize : peopleForSize.keySet()) {
            LinkedList<Integer> people = peopleForSize.get(groupSize);
            int numberOfGroups = people.size() / groupSize;
            while (numberOfGroups-- > 0) {
                List<Integer> group = new ArrayList<>(groupSize);
                int count = 0;
                while (count < groupSize) {
                    group.add(people.removeFirst());
                    count++;
                }
                result.add(group);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        GroupThePeopleGivenTheGroupSizeTheyBelongTo G = new GroupThePeopleGivenTheGroupSizeTheyBelongTo();
        System.out.println(G.groupThePeople(new int[]{3,3,3,3,3,1,3}));
        System.out.println(G.groupThePeople(new int[]{2,1,3,3,3,2   }));
    }
}
