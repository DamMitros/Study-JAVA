package stack.operation;

import stack.Stack;

public interface Operation {
  String getSymbol();
  void calculate(Stack stack);
}