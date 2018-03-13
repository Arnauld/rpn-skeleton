package rpn;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CLI {
    public static final void main(String[] args) {
        String expression = Stream.of(args).collect(Collectors.joining(" "));
        System.out.println("About to evaluate '" + expression + "'");
        double result = evaluate(expression);
        System.out.println("> " + result);
    }

    static double evaluate(String expression) {
        if (expression == null || expression == ""){
            return 0;
        }

        Stack<Double> stack = new Stack();

        double tmp = 0.0;
        int posPoint = 0;
        for(int i = 0; i < expression.length(); i++){
            char carac = expression.charAt(i);
            if (carac == '.'){
                posPoint = i;
                continue;
            }
            if(carac == ' '){
                if(tmp != 0){
                    stack.push(tmp);
                }
                tmp = 0;
                posPoint = 0;
            }
            else{
                if(stack.size() > 1){
                    if(carac == '+' || carac == '-' || carac == '*' || carac == '/'){
                        tmp = stack.peek();
                        stack.pop();
                        tmp = calculate(stack.peek(), tmp, carac);
                        stack.pop();
                        stack.push(tmp);
                        continue;
                    }
                }
                if (posPoint > 0){
                    tmp = tmp + Character.getNumericValue(carac) * Math.pow(10, (i - posPoint) * -1);
                }
                else {
                    tmp = tmp * Math.pow(10, i) + Character.getNumericValue(carac);
                }
            }
        }
        return stack.peek();
    }

    static double calculate(double nb1, double nb2, char operation){
        switch(operation){
            case '+':
                return nb1 + nb2;
            case '-':
                return nb1 - nb2;
            case '*':
                return nb1 * nb2;
            case '/':
                return nb1 / nb2;
        }
        return 0;
    }
}
