package contests.biweekly_142;

public class FindTheOriginalTypedStringI {
    public int possibleStringCount(String word) {
        int N = word.length();
        char[] chars = word.toCharArray();
        int i = 0;
        int count;
        int result = 0;
        while (i < N) {
            count = 0;
            while (i < N-1 && chars[i] == chars[i+1]) {
                count++;
                i++;
            }
            result += count;
            i++;
        }
        return result + 1;
    }

    public static void main(String[] args) {
        FindTheOriginalTypedStringI F = new FindTheOriginalTypedStringI();
        System.out.println(F.possibleStringCount("abbcccc"));
        System.out.println(F.possibleStringCount("abcd"));
        System.out.println(F.possibleStringCount("aaaa"));
        System.out.println(F.possibleStringCount("ere"));
    }
}
