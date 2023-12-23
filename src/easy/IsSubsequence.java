package easy;

public class IsSubsequence {
    public boolean isSubsequence(String s, String t) {
        if (s == null || s.isBlank() || s.isEmpty())
            return true;
        if (t == null || t.isBlank() || t.isEmpty())
            return false;
        int sSize = s.length();
        int i = 0;
        int tSize = t.length();
        for (int j = 0; j < tSize && i < sSize; j++) {
            if (t.charAt(j) == s.charAt(i))
                i++;
            if (i == sSize)
                return true;
        }
        return false;
    }
    public static void main(String[] args) {

    }
}
