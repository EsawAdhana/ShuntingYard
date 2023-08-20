import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] tokens = sc.nextLine().split(" ");
        Stack<String> ops = new Stack<>();
        Queue<String> vals = new LinkedList<>();

        for (String token : tokens) {
            if (isNum(token)) {
                vals.add(token);
            } else {
                if (token.equals("(")) {
                    ops.push(token);
                } else if (token.equals(")")) {
                    while (!ops.isEmpty() && !ops.peek().equals("(")) {
                        vals.add(ops.pop());
                    }
                    if (!ops.isEmpty() && ops.peek().equals("(")) {
                        ops.pop(); // Pop the opening parenthesis
                    }
                } else {
                    while (!ops.isEmpty() && getPrec(token) <= getPrec(ops.peek())) {
                        vals.add(ops.pop());
                    }
                    ops.push(token);
                }
            }
        }

        while (!ops.isEmpty()) {
            vals.add(ops.pop());
        }

        System.out.println(vals);
        System.out.println(evaluatePostfix(vals));
    }

    public static int getPrec(String str) {
        return switch (str) {
            case "*", "/" -> 2;
            case "+", "-" -> 1;
            default -> 0;  // Default precedence for '(' and ')'
        };
    }

    public static boolean isNum(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int evaluatePostfix(Queue<String> postfixQueue) {
        Stack<Integer> stack = new Stack<>();
        while (!postfixQueue.isEmpty()) {
            String token = postfixQueue.poll();
            if (isNum(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result = performOperation(token, operand1, operand2);
                stack.push(result);
            }
        }

        return stack.pop();
    }

    public static int performOperation(String operator, int operand1, int operand2) {
        return switch (operator) {
            case "+" -> operand1 + operand2;
            case "-" -> operand1 - operand2;
            case "*" -> operand1 * operand2;
            case "/" -> operand1 / operand2;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
}
