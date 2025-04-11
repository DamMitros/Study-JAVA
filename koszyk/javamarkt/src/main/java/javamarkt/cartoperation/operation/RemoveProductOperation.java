package javamarkt.cartoperation.operation;

import javamarkt.Product;
import javamarkt.cartoperation.ShoppingCart;

public class RemoveProductOperation implements OperationCart{
  private final Product product;

  public RemoveProductOperation(Product product){
    this.product = product;
  }

  @Override
  public void execute(ShoppingCart cart) {
    cart.removeProduct(product);
  }

  @Override
  public void undo(ShoppingCart cart) {
    cart.addProduct(product);
  }

  @Override
  public String getdescription() {
    return "Removed product: " + product.getName();
  }
}
