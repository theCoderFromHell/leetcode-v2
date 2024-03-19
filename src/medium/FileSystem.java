package medium;

import java.util.HashMap;

public class FileSystem {
    HashMap<String, Integer> map;
    public FileSystem() {
        map = new HashMap<>();
        map.put("", 0);
    }

    public boolean createPath(String path, int value) {
        if (map.containsKey(path))
            return false;
        int index = path.lastIndexOf("/");
        String parent = path.substring(0, index);
        if (!map.containsKey(parent))
            return false;
        map.put(path, value);
        return true;
    }

    public int get(String path) {
        return map.getOrDefault(path, -1);
    }

    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        System.out.println(fileSystem.createPath("/leet",1));
        System.out.println(fileSystem.createPath("/leet/code",2));
        System.out.println(fileSystem.get("/leet/code"));
        System.out.println(fileSystem.createPath("/c/d",1));
        System.out.println(fileSystem.get("/c"));
    }
}

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * boolean param_1 = obj.createPath(path,value);
 * int param_2 = obj.get(path);
 */