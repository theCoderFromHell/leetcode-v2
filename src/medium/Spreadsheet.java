package medium;

//https://leetcode.com/problems/design-spreadsheet/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Spreadsheet {
    List<List<Integer>> sheet;
    public Spreadsheet(int rows) {
        this.sheet = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            sheet.add(new ArrayList<>(Collections.nCopies(26, 0)));
        }
    }

    public void setCell(String cell, int value) {
        int column = cell.charAt(0) - 'A';
        int row = Integer.parseInt(cell.substring(1)) - 1;
        sheet.get(row).set(column, value);
    }

    public void resetCell(String cell) {
        int column = cell.charAt(0) - 'A';
        int row = Integer.parseInt(cell.substring(1)) - 1;
        sheet.get(row).set(column, 0);
    }

    public int getValue(String formula) {
        int plus = formula.indexOf('+');
        String left = formula.substring(1, plus);
        String right = formula.substring(plus + 1);
        int result = 0;
        result += (Character.isAlphabetic(left.charAt(0)) ?
                sheet.get(Integer.parseInt(left.substring(1)) - 1).get(left.charAt(0) - 'A') :
                Integer.parseInt(left));
        result += (Character.isAlphabetic(right.charAt(0)) ?
                sheet.get(Integer.parseInt(right.substring(1)) - 1).get(right.charAt(0) - 'A') :
                Integer.parseInt(right));
        return result;
    }

    public static void main(String[] args) {
        Spreadsheet S = new Spreadsheet(3);

        // basic set and get
        S.setCell("A1", 10);
        S.setCell("B1", 20);
        System.out.println(S.getValue("=A1+B1"));   // 30

        // integer literals in formula
        System.out.println(S.getValue("=A1+5"));    // 15
        System.out.println(S.getValue("=3+4"));     // 7

        // reset cell back to 0
        S.resetCell("A1");
        System.out.println(S.getValue("=A1+B1"));   // 20

        // cell in different row
        S.setCell("Z3", 100);
        System.out.println(S.getValue("=Z3+5"));    // 105

        // overwrite existing value
        S.setCell("B1", 50);
        System.out.println(S.getValue("=B1+10"));   // 60
    }
}
