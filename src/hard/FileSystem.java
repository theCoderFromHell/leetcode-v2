package hard;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class FileSystem {
    FileSystemNode root;

    public FileSystem() {
        this.root = new FileSystemNode("", NodeType.DIRECTORY);
    }

    public List<String> ls(String path) {
        String[] paths = path.split("/");
        if (paths.length < 1)
            return new ArrayList<>(root.children.keySet());
        return fetch(root, paths, 1, paths.length);
    }

    private List<String> fetch(FileSystemNode root, String[] paths, int index, int size) {
        FileSystemNode next = root.children.get(paths[index]);
        if (index == size-1) {
            if (NodeType.DIRECTORY.equals(next.nodeType)) {
                TreeMap<String, FileSystemNode> map = next.children;
                return new ArrayList<>(map.keySet());
            }
            else
                return List.of(next.name);
        }
        return fetch(next, paths, index+1, size);
    }

    public void mkdir(String path) {
        String[] paths = path.split("/");
        if (paths.length < 1)
            return;
        create(root, paths, 1, paths.length);
    }

    private void create(FileSystemNode root, String[] paths, int index, int size) {
        FileSystemNode node;
        if (!root.children.containsKey(paths[index])) {
            node = new FileSystemNode(paths[index], NodeType.DIRECTORY);
            root.children.put(paths[index], node);
        } else
            node = root.children.get(paths[index]);
        if (index == size-1)
            return;
        create(node, paths, index+1, size);
    }

    public void addContentToFile(String filePath, String content) {
        String[] paths = filePath.split("/");
        if (paths.length < 1)
            return;
        addContent(root, paths, 1, paths.length, content);
    }

    private void addContent(FileSystemNode root, String[] paths, int index, int size, String content) {
        FileSystemNode node = root.children.get(paths[index]);
        if (index == size-1) {
            node = node == null ? new FileSystemNode(paths[index], NodeType.FILE) : node;
            node.content += content;
            root.children.put(paths[index], node);
            return;
        }
        addContent(node, paths, index+1, size, content);
    }

    public String readContentFromFile(String filePath) {
        String[] paths = filePath.split("/");
        if (paths.length < 1)
            return "";
        return readContent(root, paths, 1, paths.length);
    }

    private String readContent(FileSystemNode root, String[] paths, int index, int size) {
        FileSystemNode node = root.children.get(paths[index]);
        if (index == size-1 && NodeType.FILE.equals(node.nodeType))
            return node.content;
        return readContent(node, paths, index+1, size);
    }

    class FileSystemNode implements Comparable{
        String name;
        String content;
        TreeMap<String, FileSystemNode> children;
        NodeType nodeType;

        public FileSystemNode(String name, NodeType nodeType) {
            this.name = name;
            this.children = (NodeType.DIRECTORY.equals(nodeType)) ? new TreeMap<>() : null;
            this.nodeType = nodeType;
            this.content = "";
        }

        @Override
        public int compareTo(Object obj) {
            if (obj instanceof FileSystemNode) {
                FileSystemNode o = (FileSystemNode)obj;
                return this.name.compareTo(o.name);
            }
            return 0;
        }
    }
    enum NodeType {
        DIRECTORY,
        FILE;
    }

    public static void main(String[] args) {
        FileSystem F = new FileSystem();
        System.out.println(F.ls("/"));
        F.mkdir("/a/b/c");
        F.addContentToFile("/a/b/c/d", "hello");
        System.out.println(F.ls("/"));
        System.out.println(F.readContentFromFile("/a/b/c/d"));
    }
}
