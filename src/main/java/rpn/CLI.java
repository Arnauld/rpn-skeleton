package rpn;

import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CLI {
    public static final void main(String[] args) {
        String expression = Stream.of(args).collect(Collectors.joining(" "));
        System.out.println("About to evaluate '" + expression + "'");
        long result = evaluate(expression);
        System.out.println("> " + result);
    }

    static long evaluate(String expression) {
        if (expression == null || expression == ""){
            return 0;
        }

        Stack<Long> stack = new Stack();

        long tmp = 0;
        for(int i = 0; i < expression.length(); i++){
            char carac = expression.charAt(i);
            if(carac == ' '){
                if(tmp != 0){
                    stack.push(tmp);
                }
                tmp = 0;
            }
            else{
                if(stack.size() > 1){
                    if(carac == '+' || carac == '-' || carac == '*'){
                        tmp = stack.peek();
                        stack.pop();
                        tmp = calculate(stack.peek(), tmp, carac);
                        stack.pop();
                        stack.push(tmp);
                        continue;
                    }
                }
                tmp = tmp + Character.getNumericValue(carac);
            }
        }
        return stack.peek();
    }

    static long calculate(long nb1, long nb2, char operation){
        switch(operation){
            case '+':
                return nb1 + nb2;
            case '-':
                return nb1 - nb2;
            case '*':
                return nb1 * nb2;
        }
        return 0;
    }
}
