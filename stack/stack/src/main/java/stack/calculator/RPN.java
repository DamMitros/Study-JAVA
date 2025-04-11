package stack.calculator;

import stack.Stack;
import stack.operation.*;

import java.util.HashMap;
import java.util.Map;

public class RPN {
  private Stack stack;
  private Map<String, Operation> operations;

  public RPN() {
    stack = new Stack();
    operations = new HashMap<>();
    initializeOperations();
  }
  
  private void initializeOperations(){
    Operation[] availableOperations = {
      new Add(),
      new Subtract(),
      new Multiply(),
      new Divide()
    };

    for (Operation op : availableOperations) {
      operations.put(op.getSymbol(), op);
    }
  }

  public void push(String element) {
    if (operations.containsKey(element)) {
      apply(element);
    } else {
      try {
        Integer.parseInt(element);
        stack.push(element);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Invalid input: " + element);
      }
    }
  }

  private void apply(String operator){
    operations.get(operator).calculate(stack);
  }

  public String pop() {
    return stack.pop();
  }

  public String peek() {
    return stack.peek();
  }
}