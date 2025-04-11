package javamarkt.cartoperation.operation;

import javamarkt.cartoperation.ShoppingCart;

public interface OperationCart {
  void execute(ShoppingCart cart);
  void undo(ShoppingCart cart);
  String getdescription();
}
