package javamarkt.cartoperation.promotion;

import javamarkt.cartoperation.ShoppingCart;
import javamarkt.Product;

public class DiscountOver300 implements Promotion {
  private static final double DiscountRate=0.05;
  private static final double DiscountMin=300;

  @Override
  public double calculatePromotion(ShoppingCart cart) {
    double totalPrice = calculateTotalPrice(cart);
    return isApplicable(cart) ? totalPrice * DiscountRate : 0;
  }

  @Override
  public boolean isApplicable(ShoppingCart cart) {
    return calculateTotalPrice(cart) > DiscountMin;
  }

  private double calculateTotalPrice(ShoppingCart cart) {
    double totalPrice = 0;
    for (Product product : cart.getProducts()) {
      totalPrice += product.getPrice();
    }
    return totalPrice;
  }

  @Override
  public String getDescription() {
    return "Discount of " + (DiscountRate * 100) + "% for purchases over " + DiscountMin;
  }
}
