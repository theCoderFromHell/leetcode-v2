package medium;

import java.util.ArrayList;
import java.util.List;

public class LeetCode {

    public static void main(String[] args) {
        List<String> remarks = new ArrayList<>();
        remarks.add("");
        remarks.add("Javed");
        remarks.add("Khan");

        for (int i = 0; i < 3; i++) {
            if (remarks.get(i).equals("Khan")) {
                remarks.set(i, "Ali");
            }
        }

        for (int i = 0; i < 3; i++) {
            System.out.println(remarks.get(i));
        }

    }
}
