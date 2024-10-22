package medium;

import java.util.ArrayList;
import java.util.List;

/*
https://leetcode.com/problems/encode-and-decode-strings/
 */
public class Codec {
    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        int N = strs.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++)
            sb.append(strs.get(i).replace("/", "//")).append("/:");
        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> result = new ArrayList<>();
        int index = 0;
        int length = s.length();
        StringBuilder current = new StringBuilder();
        while (index < length) {
            if (index + 1 < length  && s.charAt(index) == '/' && s.charAt(index+1) == '/') {
                current.append('/');
                index += 2;
            } else if (index + 1 < length  && s.charAt(index) == '/' && s.charAt(index+1) == ':') {
                result.add(current.toString());
                current = new StringBuilder();
                index += 2;
            } else {
                current.append(s.charAt(index));
                index++;
            }
        }
        return result;
    }
}
