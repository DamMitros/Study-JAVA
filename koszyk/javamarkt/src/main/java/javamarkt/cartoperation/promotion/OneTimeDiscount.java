package javamarkt.cartoperation.promotion;

import javamarkt.cartoperation.ShoppingCart;
import javamarkt.Product;

public class OneTimeDiscount implements Promotion {
  private static final double DISCOUNT_RATE = 0.3;
  private final String productCode;
  
  public OneTimeDiscount(String productCode) {
    this.productCode = productCode;
  }

  @Override
  public double calculatePromotion(ShoppingCart cart){
    for (Product product : cart.getProducts()){
      if (product.getCode().equals(productCode)){
        return product.getPrice() * DISCOUNT_RATE;
      }
    }
    return 0.0;
  }

  @Override
  public boolean isApplicable(ShoppingCart cart) {
    for (Product product : cart.getProducts()){
      if (product.getCode().equals(productCode)){
        return true;
      }
    }
    return false;
  }

  @Override
  public String getDescription() {
    return "One-time discount of " + (DISCOUNT_RATE * 100) + "% on product with code: " + productCode;
  }
}
