package medium;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class FileSystemV2 {
    TrieNode root;
    public FileSystemV2() {
        this.root = new TrieNode("", 0);
    }

    public boolean createPath(String path, int value) {
        String[] paths = path.split("/");
        return root.add(paths, 1, paths.length, value);
    }

    public int get(String path) {
        String[] paths = path.split("/");
        return root.find(paths, 1, paths.length);
    }

    class TrieNode {
        String name;
        int value;
        HashMap<String,TrieNode> children;

        public boolean add(String[] paths, int index, int size, int value) {
            if (index == size-1) {
                if (this.children.getOrDefault(paths[index], null) != null)
                    return false;
                this.children.put(paths[index], new TrieNode(paths[index], value));
                return true;
            } else  {
                TrieNode node = this.children.getOrDefault(paths[index], null);;
                if (node == null)
                    return false;
                return node.add(paths, index+1, size, value);
            }
        }

        public int find(String[] paths, int index, int size) {
            TrieNode node = this.children.getOrDefault(paths[index], null);
            if (node == null)
                return -1;
            if (index == size - 1)
                return node.value;
            return node.find(paths, index + 1, size);
        }

        public TrieNode(String name, int value) {
            this.name = name;
            this.value = value;
            this.children = new HashMap<>();
        }

        @Override
        public String toString() {
            return "TrieNode{" +
                    "name='" + name + '\'' +
                    ", value=" + value +
                    ", children=" + children +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TrieNode trieNode = (TrieNode) o;
            return Objects.equals(name, trieNode.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public static void main(String[] args) {
        FileSystemV2 fileSystem = new FileSystemV2();
        System.out.println(fileSystem.createPath("/leet",1));
        System.out.println(fileSystem.createPath("/leet/code",2));
        System.out.println(fileSystem.get("/leet/code"));
        System.out.println(fileSystem.createPath("/c/d",1));
        System.out.println(fileSystem.get("/c"));
    }
}
