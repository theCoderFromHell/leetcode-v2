package medium;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class KeysAndRooms {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        HashSet<Integer> visited = new HashSet<>();
        dfs(rooms, 0, visited);
        return visited.size() == rooms.size();
    }

    private void dfs(List<List<Integer>> rooms, int room, HashSet<Integer> visited) {
        visited.add(room);
        List<Integer> keysForRooms = rooms.get(room);
        for (int keyRoom : keysForRooms) {
            if (!visited.contains(keyRoom))
                dfs(rooms, keyRoom, visited);
        }
    }
}
