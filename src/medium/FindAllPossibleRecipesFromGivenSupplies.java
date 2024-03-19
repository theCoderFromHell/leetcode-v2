package medium;

import java.util.*;

public class FindAllPossibleRecipesFromGivenSupplies {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        int N = recipes.length;
        HashMap<String, List<String>> dependencies = new HashMap<>();
        HashMap<String, Boolean> availabilities = new HashMap<>();
        for (String supply : supplies)
            availabilities.put(supply, true);
        for (String recipe : recipes)
            availabilities.put(recipe, false);
        for (int i = 0; i < N; i++) {
            String recipe = recipes[i];
            List<String> requirements = ingredients.get(i);
            dependencies.put(recipe, requirements);
        }
        for (String recipe : recipes) {
            if (!availabilities.containsKey(recipe) || !availabilities.get(recipe)) {
                HashMap<String, Boolean> visited = new HashMap<>();
                if (dfs(recipe, dependencies, availabilities, visited))
                    availabilities.put(recipe, true);
            }
        }
        List<String> result = new ArrayList<>();
        for (String recipe : recipes)
            if (availabilities.containsKey(recipe) && availabilities.get(recipe))
                result.add(recipe);
        return result;

    }

    private boolean dfs(String recipe, HashMap<String, List<String>> dependencies,
                        HashMap<String, Boolean> availabilities, HashMap<String, Boolean> visited) {
        visited.put(recipe, true);
        if (!dependencies.containsKey(recipe))
            return false;
        for (String dependency : dependencies.get(recipe)) {
            if (!availabilities.containsKey(dependency) || !availabilities.get(dependency)) {
                if(visited.containsKey(dependency))
                    return false;
                if (!dfs(dependency, dependencies, availabilities, visited))
                    return false;
                else
                    availabilities.put(dependency, true);

            }
        }
        return true;
    }

    public static void main(String[] args) {
        FindAllPossibleRecipesFromGivenSupplies f = new FindAllPossibleRecipesFromGivenSupplies();
        List<List<String>> ingredients = new ArrayList<>();
        ingredients.add(Arrays.asList("yeast","flour"));
        ingredients.add(Arrays.asList("bread","meat"));
        System.out.println(f.findAllRecipes(new String[]{"bread","sandwich"}, ingredients, new String[]{"yeast","flour","meat"}));
        ingredients = new ArrayList<>();
        ingredients.add(Arrays.asList("sandwich","meat","bread"));
        ingredients.add(Arrays.asList("yeast","flour"));
        ingredients.add(Arrays.asList("bread","meat"));
        System.out.println(f.findAllRecipes(new String[]{"burger","bread","sandwich"}, ingredients, new String[]{"yeast","flour","meat"}));
    }
}
