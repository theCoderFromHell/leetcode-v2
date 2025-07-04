package interviews.atlassian;

import java.util.*;

public class AtlassianTwo {

    int totalSize;
    PriorityQueue<Collection> queue;

    HashMap<String, Collection> availableCollections;

    public AtlassianTwo() {
        this.totalSize = 0;
        this.availableCollections = new HashMap<>();
        this.queue = new PriorityQueue<>((o1, o2) -> {
            if (o1.totalSize > o2.totalSize)
                return -1;
            else if (o1.totalSize < o2.totalSize)
                return 1;
            return 0;
        });
    }

    //have a mapping of parents of the collection
    // while adding a file to a collection lets check parent
    // and add the file and the size to the parent as well


    // fileNames = {"file1.txt"}
    //sizes = {100}
    //collections = {"collection1"} empty string for undefined collection
    public void input(String[] fileNames, int[] sizes, String[] collections) {
        //O(N)
        if (fileNames == null || sizes == null || collections == null)
            return;
        if (fileNames.length == 0 || sizes.length == 0 || collections.length == 0)
            return;
        int N = fileNames.length;;
        for (int i = 0; i < N; i++) {
            totalSize += sizes[i];
            Collection collection = availableCollections.getOrDefault(collections[i], new Collection(collections[i]));
            collection.totalSize = collection.totalSize + sizes[i];
            List<String> files =  collection.fileNames;
            files.add(fileNames[i]);
            collection.fileNames = files;
            //updateParents(collection);
            //O(F*C)
            availableCollections.put(collections[i], collection);
        }

        for(String collectionName : availableCollections.keySet()) {
            queue.add(availableCollections.get(collectionName));
        }
    }

    public int getSize() {
        //O(1)
        return totalSize;
    }

    public List<String> topCollections(int N) {
        //O(NlogN)
        List<String> result = new ArrayList<>();
        while (N > 0 && !queue.isEmpty()) {
            Collection collection = queue.poll();
            if (!collection.collectionName.equals("")) {
                result.add(collection.collectionName);
                N--;
            }

        }
        return result;
    }

    /*
    file1.txt (size: 100)
file2.txt (size: 200) in collection "collection1"
file3.txt (size: 200) in collection "collection1"
file4.txt (size: 300) in collection "collection2"
file5.txt (size: 10)
     */

    public static void main(String[] args) {
        AtlassianTwo atlassian = new AtlassianTwo();
        atlassian.input(new String[]{"file1.txt", "file2.txt", "file3.txt", "file4.txt", "file5.txt"},
                new int[] {100, 200, 200, 300, 1000},
                new String[] {"", "collection1", "collection1", "collection2", ""});
        System.out.println(atlassian.totalSize);
        List<String> top = atlassian.topCollections(1);
        for(String s : top)
            System.out.println(s + " ");
    }
}
class Collection {

    Collection parent;
    String collectionName;
    List<String> fileNames;
    int totalSize;

    public Collection(String collectionName) {
        this.parent = null;
        this.collectionName = collectionName;
        this.fileNames = new ArrayList<>();
        this.totalSize = 0;
    }
}
