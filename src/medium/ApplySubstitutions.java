package medium;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplySubstitutions {
    public String applySubstitutions(List<List<String>> replacements, String text) {
        int size = replacements.size();
        HashMap<String,String> mappings = new HashMap<>();
        String key, value;
        for (int i = 0; i < size; i++) {
           key = replacements.get(i).get(0);
           value = replacements.get(i).get(1);
           mappings.put(key, value);
        }
        for (Map.Entry<String,String> entry : mappings.entrySet())
            mappings.put(entry.getKey(), parse(entry.getValue(), mappings));
        return parse(text, mappings);
    }

    private String parse(String value, HashMap<String, String> mappings) {
        if (!value.contains("%"))
            return value;
        int size = value.length();
        int index = 0;
        StringBuilder sb = new StringBuilder();
        while (index < size) {
            if (index < size-2 && value.charAt(index) == '%' && value.charAt(index+2) == '%') {
                sb.append(parse(mappings.get(String.valueOf(value.charAt(index + 1))), mappings));
                index +=2;
            }
            else
                sb.append(value.charAt(index));
            index++;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ApplySubstitutions A = new ApplySubstitutions();
        System.out.println(A.applySubstitutions(List.of(List.of("A","bce"), List.of("B","ace"), List.of("C","abc%B%")),"%A%_%B%_%C%" ));
    }
}
