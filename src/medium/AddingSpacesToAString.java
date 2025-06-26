package medium;

public class AddingSpacesToAString {
    public String addSpaces(String s, int[] spaces) {
        StringBuilder sb = new StringBuilder();
        int size = spaces.length;
        int start = 0;
        for (int i = 0; i < size; i++) {
            sb.append(s, start, spaces[i]);
            sb.append(" ");
            start = spaces[i];
        }
        sb.append(s, start, s.length());
        return sb.toString();
    }

    public static void main(String[] args) {
        AddingSpacesToAString A = new AddingSpacesToAString();
        System.out.println(A.addSpaces("LeetcodeHelpsMeLearn", new int[]{8,13,15}));
        System.out.println(A.addSpaces("icodeinpython", new int[]{1,5,7,9}));
        System.out.println(A.addSpaces("spacing", new int[]{0,1,2,3,4,5,6}));
    }
}
