package medium;

// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html

import java.util.Iterator;

class PeekingIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private Integer current;
    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        this.iterator = iterator;
        current = (iterator.hasNext()) ? iterator.next() : null;
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return current;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        Integer result = current;
        current = (iterator.hasNext()) ? iterator.next() : null;
        return result;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }
}
