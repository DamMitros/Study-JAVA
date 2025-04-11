package javamarkt.cartoperation.operation;

import javamarkt.cartoperation.ShoppingCart;
import javamarkt.cartoperation.comparison.CompareProduct;

public class ChangeComparatorOperation implements OperationCart {
  private final CompareProduct comparator;
  private final CompareProduct oldComparator;

  public ChangeComparatorOperation(CompareProduct comparator, CompareProduct oldComparator) {
    this.comparator = comparator;
    this.oldComparator = oldComparator;
  }

  @Override
  public void execute(ShoppingCart cart) {
    cart.setComparator((CompareProduct) comparator);
  }

  @Override
  public void undo(ShoppingCart cart) {
    cart.setComparator((CompareProduct) oldComparator);
  }

  @Override
  public String getdescription() {
    return "Changed comparator";
  }
}
