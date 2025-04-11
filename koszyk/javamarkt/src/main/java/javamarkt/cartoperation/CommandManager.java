package javamarkt.cartoperation;

import javamarkt.cartoperation.operation.OperationCart;
import java.util.Stack;

public class CommandManager {
  private final Stack<OperationCart> executedOperations;
  private final Stack<OperationCart> undoneOperations;
  private final ShoppingCart cart;

  public CommandManager(ShoppingCart cart){
    this.cart = cart;
    this.executedOperations = new Stack<>();
    this.undoneOperations = new Stack<>();
  }

  public void executeOperation(OperationCart operation) {
    operation.execute(cart);
    executedOperations.push(operation);
    undoneOperations.clear();
  }

  public boolean canUndo() {
    return !executedOperations.isEmpty();
  }

  public boolean canRedo() {
    return !undoneOperations.isEmpty();
  }

  public void undo() {
    if (canUndo()) {
      OperationCart operation = executedOperations.pop();
      operation.undo(cart);
      undoneOperations.push(operation);
    }
  }

  public void redo() {
    if (canRedo()) {
      OperationCart operation = undoneOperations.pop();
      operation.execute(cart);
      executedOperations.push(operation);
    }
  }

  public String getLastOperationDescription() {
    if (!executedOperations.isEmpty()) {
      return executedOperations.peek().getdescription();
    } else {
      return "No operations executed yet.";
    }
  }
}
