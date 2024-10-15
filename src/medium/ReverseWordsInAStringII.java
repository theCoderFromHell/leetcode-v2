package medium;

public class ReverseWordsInAStringII {
    public void reverseWords(char[] word) {
        int size = word.length;
        int start = 0, end = -1;
        while (end < size) {
            while (end+1 < size && word[end+1] != ' ')
                end++;
            reverse(word, start, end);
            end ++;
            start = end+1;
        }
        reverse(word, 0, size-1);
    }

    private void reverse(char[] word, int start, int end) {
        char temp;
        while (start < end) {
            temp = word[start];
            word[start] = word[end];
            word[end] = temp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        ReverseWordsInAStringII R = new ReverseWordsInAStringII();
        R.reverseWords(new char[]{'t','h','e',' ','s','k','y',' ','i','s',' ','b','l','u','e'});
        R.reverseWords(new char[]{'a'});
        R.reverseWords(new char[]{'b','l','u','e'});

    }
}
