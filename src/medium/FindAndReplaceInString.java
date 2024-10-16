package medium;

import java.util.ArrayList;
import java.util.List;

public class FindAndReplaceInString {
    public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        int N = indices.length;
        String result = new String(s);
        int length = s.length();
        List<Replacement> replacements = new ArrayList<>();
        for (int i = 0; i < N; i++)
            replacements.add(new Replacement(indices[i], sources[i], targets[i]));
        replacements.sort((a, b) -> b.index - a.index);
        for (Replacement replacement : replacements) {
            int size = replacement.source.length();
            if (replacement.index > length - size)
                continue;
            if (replacement.source.equals(s.substring(replacement.index, replacement.index + size))) {
                if (replacement.index > 0)
                    result = result.substring(0, replacement.index) + replacement.target + result.substring(replacement.index + size);
                else
                    result = replacement.target + result.substring(replacement.index + size);
            }
        }
        return result;
    }

    class Replacement {
        int index;
        String source;
        String target;

        public Replacement(int index, String source, String target) {
            this.index = index;
            this.source = source;
            this.target = target;
        }
    }

    public static void main(String[] args) {
        FindAndReplaceInString F = new FindAndReplaceInString();
        //System.out.println(F.findReplaceString("abcd", new int[]{0, 2}, new String[]{"ab","ec"}, new String[]{"eee","ffff"}));
        System.out.println(F.findReplaceString("abcd", new int[]{0, 2}, new String[]{"a", "cd"}, new String[]{"eee", "ffff"}));
        //System.out.println(F.findReplaceString("abcde", new int[]{2,2,3}, new String[]{"cde","cdef","dk"}, new String[]{"fe","f","xyz"}));

    }
}
