package medium;

public class PrintImmutableLinkedListInReverse {
    public void printLinkedListInReverse(ImmutableListNode head) {
        if (head.getNext() != null)
            printLinkedListInReverse(head.getNext());
        head.printValue();

    }

    interface ImmutableListNode {
        public void printValue();

        public ImmutableListNode getNext();
    }
}
