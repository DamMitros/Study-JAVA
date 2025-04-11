package javamarkt.cartoperation.promotion;

import javamarkt.cartoperation.ShoppingCart;
import javamarkt.cartoperation.comparison.PriceCompare;
import javamarkt.Product;
import java.util.Arrays;

public class FreeThirdItem implements Promotion {
  @Override
  public double calculatePromotion(ShoppingCart cart) {
     if (!isApplicable(cart)) return 0.0;

    Product[] products = cart.getProducts().clone();
    PriceCompare priceCompare = new PriceCompare(true);
    Arrays.sort(products, priceCompare::compare);
    return products[0].getPrice(); 
  }

  @Override
  public boolean isApplicable(ShoppingCart cart) {
    Product[] products = cart.getProducts();
    return products.length >= 3;
  }

  @Override
  public String getDescription() {
    return "If you buy 3 items, the cheapest one is free";
  }
}