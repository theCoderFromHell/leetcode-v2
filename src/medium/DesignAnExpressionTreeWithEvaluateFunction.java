package medium;

import java.util.Stack;

public class DesignAnExpressionTreeWithEvaluateFunction {

    abstract class Node {
        public abstract int evaluate();
    }

    class ExpressionNode extends Node {
        boolean isOperator;
        String value;
        ExpressionNode left;
        ExpressionNode right;

        public ExpressionNode(boolean isOperator, String value) {
            this.isOperator = isOperator;
            this.value = value;
        }

        @Override
        public int evaluate() {
            if (!isOperator)
                return Integer.parseInt(value);
            int leftValue = left.evaluate();
            int rightValue = right.evaluate();
            return switch (value) {
                case "+" -> leftValue + rightValue;
                case "-" -> leftValue - rightValue;
                case "*" -> leftValue * rightValue;
                case "/" -> leftValue / rightValue;
                default -> 0;
            };
        }
    }

    class TreeBuilder {
        Stack<ExpressionNode> stack;
        ExpressionNode root;

        public TreeBuilder() {
            this.stack = new Stack<>();
        }

        Node buildTree(String[] postfix) {
            int size = postfix.length;
            ExpressionNode current;
            for (int i = 0; i < size; i++) {
                if (isOperator(postfix[i])) {
                    ExpressionNode right = stack.pop();
                    ExpressionNode left = stack.pop();
                    current = new ExpressionNode(true, postfix[i]);
                    current.left = left;
                    current.right = right;
                    stack.add(current);
                    if (i == size-1)
                        this.root = current;
                } else
                    stack.add(new ExpressionNode(false, postfix[i]));
            }
            return root;
        }

        private boolean isOperator(String postfix) {
            return (postfix.equals("+") || postfix.equals("-") || postfix.equals("*") || postfix.equals("/"));
        }
    };
}


