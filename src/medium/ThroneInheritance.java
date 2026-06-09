package medium;

import java.util.*;

// https://leetcode.com/problems/throne-inheritance/
public class ThroneInheritance {
    HashMap<String,Person> peopleMap;
    Person king;
    public ThroneInheritance(String kingName) {
        this.peopleMap = new HashMap<>();
        Person king = new Person(kingName);
        this.king = king;
        peopleMap.put(kingName, king);
    }

    public void birth(String parentName, String childName) {
        Person p = peopleMap.get(parentName);
        Person child = new Person(childName);
        peopleMap.put(childName, child);
        p.children.add(child);
    }

    public void death(String name) {
        Person p = peopleMap.get(name);
        p.isDead = true;
    }

    public List<String> getInheritanceOrder() {
        List<String> inheritanceOrder = new ArrayList<>();
        findOrder(king, inheritanceOrder);
        return inheritanceOrder;
    }

    private void findOrder(Person p, List<String> inheritanceOrder) {
        if (!p.isDead)
            inheritanceOrder.add(p.name);
        List<Person> children = p.children;
        if (children == null || children.isEmpty())
            return;
        for (Person child : children)
            findOrder(child, inheritanceOrder);
    }

    /*
     * Revision Note — Throne Inheritance (Medium)
     * Pattern: Pre-order DFS on an n-ary tree built incrementally via birth() calls
     * Key Insight: Inheritance order IS pre-order DFS of the family tree. Death never
     *              modifies the tree structure — just mark the node and filter it during
     *              traversal. This keeps death O(1) and avoids re-wiring.
     * Gotchas:
     *   - Use List<Person> for children, not Queue — children are iterated in insertion
     *     order, not consumed FIFO. Queue works accidentally but communicates wrong intent.
     *   - children == null check is dead code when children is always initialized in constructor.
     *   - No need to store a parent reference if DFS always starts from the king root.
     */
    public static void main(String[] args) {
        // Test 1: LeetCode example — 3-generation family, then bob dies
        ThroneInheritance T = new ThroneInheritance("king");
        T.birth("king", "andy");
        T.birth("king", "bob");
        T.birth("king", "catherine");
        T.birth("andy", "matthew");
        T.birth("bob", "alex");
        T.birth("bob", "asha");
        System.out.println("Test 1: " + T.getInheritanceOrder() + " (Expected: [king, andy, matthew, bob, alex, asha, catherine])");

        T.death("bob");
        System.out.println("Test 2: " + T.getInheritanceOrder() + " (Expected: [king, andy, matthew, alex, asha, catherine])");

        // Test 3: dead person's children still inherit
        T.death("andy");
        System.out.println("Test 3: " + T.getInheritanceOrder() + " (Expected: [king, matthew, alex, asha, catherine])");

        // Test 4: king dies — children still inherit in order
        ThroneInheritance T2 = new ThroneInheritance("king");
        T2.birth("king", "alice");
        T2.birth("king", "bob");
        T2.death("king");
        System.out.println("Test 4: " + T2.getInheritanceOrder() + " (Expected: [alice, bob])");

        // Test 5: single node alive
        ThroneInheritance T3 = new ThroneInheritance("king");
        System.out.println("Test 5: " + T3.getInheritanceOrder() + " (Expected: [king])");

        // Test 6: single node dead
        ThroneInheritance T4 = new ThroneInheritance("king");
        T4.death("king");
        System.out.println("Test 6: " + T4.getInheritanceOrder() + " (Expected: [])");

        // Test 7: all dead
        ThroneInheritance T5 = new ThroneInheritance("king");
        T5.birth("king", "alice");
        T5.death("king");
        T5.death("alice");
        System.out.println("Test 7: " + T5.getInheritanceOrder() + " (Expected: [])");
    }
}

class Person {
    String name;
    boolean isDead;
    List<Person> children;

    public Person(String name) {
        this.name = name;
        this.isDead = false;
        this.children = new ArrayList<>();
    }
}
