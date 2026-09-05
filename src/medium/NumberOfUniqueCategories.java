package medium;

// https://leetcode.com/problems/number-of-unique-categories/
public class NumberOfUniqueCategories {
    public int numberOfCategories(int n, CategoryHandler categoryHandler) {
        boolean[] visited = new boolean[n];
        int count = n;
        for (int i = 0; i < n; i++) {
            if (visited[i])
                continue;
            for (int j = i + 1; j < n; j++) {
                if (categoryHandler.haveSameCategory(i, j)) {
                    visited[j] = true;
                    count--;
                }
            }
            visited[i] = true;
        }
        return count;
    }

    /*
     * Revision Note — Number of Unique Categories (Medium)
     *
     * Pattern: Greedy grouping via oracle — O(n²) pairwise comparison
     *
     * Key Insight: Start count=n; for each unvisited i, scan all j>i — if same category,
     * mark j visited and decrement count. Transitivity of the equivalence relation ensures
     * no double-decrement: a visited j can only be reached by unvisited i if they share
     * a category, which would have caused i to be visited earlier — contradiction.
     *
     * Gotchas:
     * - Inner loop does NOT check visited[j] — intentional; the outer loop handles it
     * - O(n²) API calls are unavoidable in the worst case (all different categories)
     * - CategoryHandler.haveSameCategory must be a true equivalence (transitive) for correctness
     *
     * Template:
     *   count = n
     *   for i in 0..n-1:
     *     if visited[i]: continue
     *     for j in i+1..n-1:
     *       if sameCategory(i,j): visited[j]=true; count--
     *     visited[i] = true
     *   return count
     */
    public static void main(String[] args) {
        NumberOfUniqueCategories N = new NumberOfUniqueCategories();

        System.out.println("Test 1: " + N.numberOfCategories(6, new CategoryHandler(new int[]{1, 1, 2, 2, 3, 3})) + " (Expected: 3)");
        System.out.println("Test 2: " + N.numberOfCategories(5, new CategoryHandler(new int[]{1, 1, 1, 1, 1}))    + " (Expected: 1)"); // all same
        System.out.println("Test 3: " + N.numberOfCategories(4, new CategoryHandler(new int[]{1, 2, 3, 4}))       + " (Expected: 4)"); // all different
        System.out.println("Test 4: " + N.numberOfCategories(1, new CategoryHandler(new int[]{7}))                + " (Expected: 1)"); // single item
        System.out.println("Test 5: " + N.numberOfCategories(5, new CategoryHandler(new int[]{2, 1, 2, 1, 3}))   + " (Expected: 3)"); // interleaved
    }
}

class CategoryHandler {
    int[] categories;
    public CategoryHandler(int[] categories){
        this.categories = categories;
    }
    public boolean haveSameCategory(int a, int b){
        return categories[a] == categories[b];
    }
}
