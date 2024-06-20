package medium;

public class NumberOfStepsToReduceANumberInBinaryRepresentationToOne {
    public int numSteps(String s) {
        StringBuilder sb = new StringBuilder(s);
        int count = 0;
        while (!new String(sb).equals("1")) {
            if (sb.charAt(sb.length()-1) == '1') {
                sb = addOne(sb);
            } else {
                sb.deleteCharAt(sb.length()-1);
            }
            count++;
        }
        return count;
    }

    private StringBuilder addOne(StringBuilder sb) {
        int N = sb.length();
        int index = N-1;
        while (index >= 0) {
            if (sb.charAt(index) == '1')
                sb.setCharAt(index, '0');
            else {
                sb.setCharAt(index, '1');
                break;
            }
            index--;
        }
        if (index < 0)
            sb.insert(0, '1');
        return sb;
    }

    public static void main(String[] args) {
        NumberOfStepsToReduceANumberInBinaryRepresentationToOne N = new NumberOfStepsToReduceANumberInBinaryRepresentationToOne();
        System.out.println(N.numSteps("1101"));
        System.out.println(N.numSteps("10"));
        System.out.println(N.numSteps("1"));

    }
}
