package medium;

import java.util.List;

public class NestedListWeightSum {
    public int depthSum(List<NestedInteger> nestedList) {
        int size = nestedList.size();
        int sum = 0;
        for (int i = 0; i < size; i++)
            sum += deepSum(nestedList.get(i), 1);
        return sum;
    }

    private int deepSum(NestedInteger nestedInteger, int level) {
        if (nestedInteger == null)
            return 0;
        if (nestedInteger.isInteger())
            return level * nestedInteger.getInteger();
        List<NestedInteger> subList = nestedInteger.getList();
        int sum = 0;
        for (NestedInteger n : subList)
            sum += deepSum(n, level + 1);
        return sum;
    }

    public interface NestedInteger {
        public boolean isInteger();
        public Integer getInteger();
        public void setInteger(int value);
        public void add(NestedInteger ni);
        public List<NestedInteger> getList();
    }
}