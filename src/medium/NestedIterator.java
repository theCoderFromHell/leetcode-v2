package medium;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NestedIterator implements Iterator<Integer> {
    private List<Integer> numbers;
    private int index;
    public NestedIterator(List<NestedInteger> nestedList) {
        this.numbers = new ArrayList<>();
        this.index = 0;
        flatten(nestedList, numbers);
    }

    private void flatten(List<NestedInteger> nestedList, List<Integer> numbers) {
        for (NestedInteger ni : nestedList) {
            if (ni.isInteger())
                numbers.add(ni.getInteger());
            else
                flatten(ni.getList(), numbers);
        }
    }

    @Override
    public Integer next() {
        return numbers.get(index++);
    }

    @Override
    public boolean hasNext() {
        if (index == numbers.size())
            return false;
        return true;
    }

    public interface NestedInteger {
        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return empty list if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }
}
