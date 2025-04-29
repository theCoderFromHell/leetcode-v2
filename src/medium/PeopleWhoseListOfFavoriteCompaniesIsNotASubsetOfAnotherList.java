package medium;

import java.util.*;

public class PeopleWhoseListOfFavoriteCompaniesIsNotASubsetOfAnotherList {
    public List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
        int size = favoriteCompanies.size();
        HashMap<Integer, HashSet<String>> companies = new HashMap<>();
        for (int i = 0; i < size; i++)
            companies.put(i, new HashSet<>(favoriteCompanies.get(i)));
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            boolean found = false;
            for (Integer key : companies.keySet()) {
                if (key == i)
                    continue;
                int count = 0;
                HashSet<String> favCompanies = companies.get(i);
                for (String c : favCompanies) {
                    if (companies.get(key).contains(c))
                        count++;
                }
                if (count == favCompanies.size()) {
                    found = true;
                    break;
                }
            }
            if (!found)
                result.add(i);
        }
        return result;
    }

    public static void main(String[] args) {
        PeopleWhoseListOfFavoriteCompaniesIsNotASubsetOfAnotherList P = new PeopleWhoseListOfFavoriteCompaniesIsNotASubsetOfAnotherList();
        System.out.println(P.peopleIndexes(Arrays.asList(Arrays.asList("leetcode","google","facebook"),
                Arrays.asList("google","microsoft"),
                Arrays.asList("google","facebook"),
                Arrays.asList("google"),
                Arrays.asList("amazon")
                )));
    }
}