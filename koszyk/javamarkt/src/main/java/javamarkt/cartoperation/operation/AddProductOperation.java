package javamarkt.cartoperation.operation;

import javamarkt.Product;
import javamarkt.cartoperation.ShoppingCart;

public class AddProductOperation implements OperationCart{
  private final Product product;

  public AddProductOperation(Product product){
    this.product = product;
  }

  @Override
  public void execute(ShoppingCart cart) {
    cart.addProduct(product);
  }
  
  @Override
  public void undo(ShoppingCart cart) {
    cart.removeProduct(product);
  }

  @Override
  public String getdescription() {
    return "Added product: " + product.getName();
  }
}
