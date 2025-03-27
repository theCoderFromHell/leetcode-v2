package medium;

import java.util.*;

public class SQL {
    public static final String NULL = "<null>";
    HashMap<String, TreeMap<Integer, String>> database;
    HashMap<String, Integer> currentIndex;
    HashMap<String, Integer> columnNumbers;
    public SQL(List<String> names, List<Integer> columns) {
        this.database = new HashMap<>();
        this.currentIndex = new HashMap<>();
        this.columnNumbers = new HashMap<>();
        int size = names.size();;
        for (int i = 0; i < size; i++) {
            String table = names.get(i);
            database.put(table, new TreeMap<>());
            currentIndex.put(table, 1);
            columnNumbers.put(table, columns.get(i));
        }
    }

    public boolean ins(String name, List<String> row) {
        if (!database.containsKey(name))
            return false;
        if (row == null || row.size() != columnNumbers.get(name))
            return false;
        int currentIdx = currentIndex.get(name);
        String value = currentIdx + "," + String.join(",", row);
        TreeMap<Integer, String> table = database.get(name);
        table.put(currentIdx, value);
        currentIndex.put(name, currentIdx+1);
        return true;
    }

    public void rmv(String name, int rowId) {
        if (!database.containsKey(name))
            return;
        int currentIdx = currentIndex.get(name);
        if (rowId >= currentIdx)
            return;
        TreeMap<Integer, String> table = database.get(name);
        if (table.containsKey(rowId) && table.get(rowId) == null)
            return;
        table.put(rowId, null);
    }

    public String sel(String name, int rowId, int columnId) {
        if (!database.containsKey(name))
            return NULL;
        if (columnId <= 0 || columnId > columnNumbers.get(name))
            return NULL;
        int currentIdx = currentIndex.get(name);
        if (rowId >= currentIdx)
            return NULL;
        TreeMap<Integer, String> table = database.get(name);
        String row = table.get(rowId);
        if (row == null || row.isBlank())
            return NULL;
        String [] rowValues = row.split(",");
        return rowValues[columnId];
    }

    public List<String> exp(String name) {
        if (!database.containsKey(name))
            return new ArrayList<>();
        List<String> csv = new ArrayList<>();
        TreeMap<Integer, String> table = database.get(name);
        for (Integer rowId : table.keySet()) {
            String row = table.get(rowId);
            if (row != null)
                csv.add(table.get(rowId));
        }
        return csv;
    }

    public static void main(String[] args) {
        SQL S = new SQL(Arrays.asList("one", "two", "three"), Arrays.asList(2, 3, 1));
        System.out.println(S.ins("two", Arrays.asList("first", "second", "third")));
        System.out.println(S.sel("two", 1, 3));
        System.out.println(S.ins("two", Arrays.asList("fourth", "fifth", "sixth")));
        System.out.println(S.exp("two"));
        S.rmv("two", 1);
        System.out.println(S.sel("two", 2, 2));
        System.out.println(S.exp("two"));
    }
}
