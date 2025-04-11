package javamarkt.cartoperation.promotion;

import javamarkt.cartoperation.ShoppingCart;
import javamarkt.Product;

public class FreeMug implements Promotion {
  private static final double MugPrice = 25.0; 
  private static final double DiscountMin = 200;
  private Product mug;

  public FreeMug() {
    this.mug = new Product("987", "Mug", MugPrice);
  }

  @Override
  public double calculatePromotion(ShoppingCart cart) {
    if (!isApplicable(cart)) return 0.0;

    boolean mugInCart = false;
    for (Product product : cart.getProducts()) {
      if (product.getCode().equals(mug.getCode())) {
        mugInCart = true;
        break;
      }
    }
    if (!mugInCart) {
      cart.addProduct(mug);
      return 0.0;
    }
    return MugPrice;
  }

  @Override
  public boolean isApplicable(ShoppingCart cart){
    double totalPrice = 0;
    for (Product product : cart.getProducts()){
      totalPrice += product.getPrice();
    }
    return totalPrice > DiscountMin;
  }

  @Override
  public String getDescription() {
    return "Free mug for purchases over " + DiscountMin;
  }
}
