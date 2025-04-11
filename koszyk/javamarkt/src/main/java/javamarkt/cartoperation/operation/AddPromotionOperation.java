package javamarkt.cartoperation.operation;

import javamarkt.cartoperation.ShoppingCart;
import javamarkt.cartoperation.promotion.*;

public class AddPromotionOperation implements OperationCart {
  private final Promotion promotion;

  public AddPromotionOperation(Promotion promotion) {
    this.promotion = promotion;
  }

  @Override
  public void execute(ShoppingCart cart) {
    cart.addPromotion(promotion);
  }

  @Override
  public void undo(ShoppingCart cart) {
    cart.removePromotion(promotion);
  }

  @Override
  public String getdescription() {
    return "Added promotion: " + promotion.getDescription();
  }
}
