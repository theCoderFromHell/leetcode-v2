package medium;

import java.util.*;

public class AccountsMerge {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        List<List<String>> result = new ArrayList<>();
        if (accounts == null || accounts.isEmpty())
            return result;
        HashMap<String, List<String>> adj = new HashMap<>();
        int size = accounts.size();
        for (int i = 0; i < size; i++) {
            List<String> acc = accounts.get(i);
            int accountSize = acc.size();
            String src = acc.get(1);
            for (int j = 2; j < accountSize; j++) {
                String dest = acc.get(j);
                List<String> srcList = adj.getOrDefault(src, new ArrayList<>());
                srcList.add(dest);
                adj.put(src, srcList);

                List<String> destList = adj.getOrDefault(dest, new ArrayList<>());
                destList.add(src);
                adj.put(dest, destList);
            }
        }
        HashSet<String> visited = new HashSet<>();
        for (int i = 0; i < size; i++) {
            List<String> acc = accounts.get(i);
            int accountSize = acc.size();
            String name = acc.get(0);
            String src = acc.get(1);
            if (!visited.contains(src)) {
                List<String> merged = new ArrayList<>();
                merged.add(name);
                dfs(src, merged, adj, visited);
                Collections.sort(merged.subList(1, merged.size()));
                result.add(merged);
            }
        }
        return result;
    }

    private void dfs(String node, List<String> merged, HashMap<String, List<String>> adj, HashSet<String> visited) {
        visited.add(node);
        merged.add(node);
        List<String> neighbours = adj.get(node);
        if (neighbours == null || neighbours.isEmpty())
            return;
        for (String neighbour : neighbours) {
            if (!visited.contains(neighbour))
                dfs(neighbour, merged, adj, visited);
        }
    }

    public static void main(String[] args) {
        AccountsMerge A = new AccountsMerge();
        System.out.println(A.accountsMerge(Arrays.asList(
                Arrays.asList("John","johnsmith@mail.com","john_newyork@mail.com"),
                Arrays.asList("John","johnsmith@mail.com","john00@mail.com"),
                Arrays.asList("Mary","mary@mail.com"),
                Arrays.asList("John","johnnybravo@mail.com"))));
        System.out.println(A.accountsMerge(Arrays.asList(
                Arrays.asList("Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"),
                Arrays.asList("Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"),
                Arrays.asList("Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"),
                Arrays.asList("Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"),
                Arrays.asList("Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co"))));
    }
}
