package javamarkt.cartoperation.promotion;

import javamarkt.cartoperation.ShoppingCart;

public interface Promotion {
  double calculatePromotion(ShoppingCart cart);
  boolean isApplicable(ShoppingCart cart);
  String getDescription();
}