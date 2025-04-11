package javamarkt.cartoperation.operation;

import javamarkt.cartoperation.ShoppingCart;
import javamarkt.cartoperation.promotion.*;

public class RemovePromotionOperation implements OperationCart {
  private final Promotion promotion;
  
  public RemovePromotionOperation(Promotion promotion){
    this.promotion = promotion;
  }

  @Override
  public void execute(ShoppingCart cart) {
    cart.removePromotion(promotion);
  }

  @Override
  public void undo(ShoppingCart cart) {
    cart.addPromotion(promotion);
  }

  @Override
  public String getdescription() {
    return "Removed promotion: " + promotion.getDescription();
  }
}
