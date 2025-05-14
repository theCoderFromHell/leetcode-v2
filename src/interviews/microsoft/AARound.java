package interviews.microsoft;

import java.util.HashMap;

public class AARound {

    public ListWithTwoPointers copyList(ListWithTwoPointers head) {
        if (head == null)
            return null;
        if (head.next == null && head.other == null)
            return new ListWithTwoPointers(head.value);
        HashMap<ListWithTwoPointers, ListWithTwoPointers> nodeMap = new HashMap<>();
        copy(head, nodeMap);
        return nodeMap.get(head);
    }

    private void copy(ListWithTwoPointers head, HashMap<ListWithTwoPointers, ListWithTwoPointers> nodeMap) {
        if (head == null)
            return;
        ListWithTwoPointers curr = nodeMap.getOrDefault(head, new ListWithTwoPointers(head.value));
        nodeMap.put(head, curr);
        ListWithTwoPointers copyOther = null;
        if (head.other != null) {
            copyOther = nodeMap.getOrDefault(head.other, new ListWithTwoPointers(head.other.value));
            nodeMap.put(head.other, copyOther);
        }
        curr.other = copyOther;
        copy(head.next, nodeMap);
    }

    class ListWithTwoPointers {
        int value;
        ListWithTwoPointers next;
        ListWithTwoPointers other;

        public ListWithTwoPointers(int value) {
            this.value = value;
            this.next = null;
            this.other = null;
        }

        @Override
        public String toString() {
            return "ListWithTwoPointers{" +
                    "value=" + value +
                    ", next=" + next +
                    ", other=" + other +
                    '}';
        }
    }

    public static void main(String[] args) {

    }

}

