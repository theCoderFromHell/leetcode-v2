package medium;

import java.util.*;

public class FindKPairsWithSmallestSums {
    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int size1 = nums1.length;
        int size2 = nums2.length;
        List<List<Integer>> result = new ArrayList<>();
        PriorityQueue<IndexPair> pq = new PriorityQueue<>((o1, o2) -> {
            if (!Objects.equals(o1.sum, o2.sum))
                return o1.sum - o2.sum;
            if (!Objects.equals(o1.i, o2.i))
                return o1.i - o2.i;
            return o1.j - o2.j;
        });
        HashSet<String> hash = new HashSet<>();
        pq.add(new IndexPair(0, 0, nums1[0]+nums2[0]));
        hash.add("0-0");
        while (k > 0 && !pq.isEmpty()) {
            IndexPair pair = pq.poll();
            result.add(Arrays.asList(nums1[pair.i], nums2[pair.j]));
            k--;
            if (pair.i < size1-1 && !hash.contains((pair.i + 1) + "-" + pair.j)) {
                pq.add(new IndexPair(pair.i + 1, pair.j, nums1[pair.i + 1] + nums2[pair.j]));
                hash.add((pair.i + 1) + "-" + pair.j);
            }
            if (pair.j < size2-1 && !hash.contains(pair.i + "-" + (pair.j + 1))) {
                pq.add(new IndexPair(pair.i, pair.j + 1, nums1[pair.i] + nums2[pair.j + 1]));
                hash.add(pair.i + "-" + (pair.j + 1));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(kSmallestPairs(new int[]{1,2,4,5,6}, new int[]{3,5,7,9}, 20));
        //System.out.println(kSmallestPairs(new int[]{}, new int[]{}, ));
    }
}
class IndexPair {
    Integer sum;
    Integer i;
    Integer j;

    public IndexPair(Integer key, Integer j, Integer sum) {
        this.i = key;
        this.j = j;
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexPair indexPair = (IndexPair) o;
        return Objects.equals(sum, indexPair.sum) && Objects.equals(i, indexPair.i) && Objects.equals(j, indexPair.j);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, i, j);
    }
}
