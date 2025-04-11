package stack.operation;

import stack.Stack;

public class Multiply implements Operation {
  @Override
  public String getSymbol() {
    return "*";
  }

  @Override
  public void calculate(Stack stack) {
    if (stack.size()<2){
      System.out.println("Not enough elements on stack");
      return;
    }
    String string1 = stack.pop();
    String string2 = stack.pop();
    // try{
    int number1 = Integer.parseInt(string1);
    int number2 = Integer.parseInt(string2);
    int result=number2*number1;

    stack.push(String.valueOf(result));
    return;
    // } catch (NumberFormatException e){
    //   System.out.println("Invalid number format, returning elements to stack");
    //   stack.push(string2);
    //   stack.push(string1);
    // }
  }
}