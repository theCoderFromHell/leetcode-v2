package medium;

import java.util.ArrayList;
import java.util.List;

public class GenerateBinaryStringsWithoutAdjacentZeros {
    public List<String> validStrings(int n) {
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<>();
        generate(sb, 0, n, true, result);
        return result;
    }

    private void generate(StringBuilder sb, int index, int size, boolean last, List<String> result) {
        if (index == size) {
            result.add(new String(sb));
            return;
        }
        sb.append("1");
        generate(sb, index + 1, size, true, result);
        sb.deleteCharAt(sb.length() - 1);
        if(last) {
            sb.append("0");
            generate(sb, index + 1, size, false, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        GenerateBinaryStringsWithoutAdjacentZeros G = new GenerateBinaryStringsWithoutAdjacentZeros();
        System.out.println(G.validStrings(1));
        System.out.println(G.validStrings(3));
        System.out.println(G.validStrings(5));
    }
}
