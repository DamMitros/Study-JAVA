package marketplace.visitor;

import marketplace.Seller;
import marketplace.Buyer;
import marketplace.product.*;

import java.util.Map;
import java.util.Random;

public class DataUpdateVisitor implements Visitor {
  private Random random = new Random();

  @Override
  public void visit(Seller seller) {
    System.out.println("Visitor: Updating data for Seller " + seller.getName());
    for (Map.Entry<Product, Integer> entry : seller.getInventory().entrySet()) {
      if (entry.getKey().getType() == ProductType.ESSENTIAL) {
        int restockAmount = 5 + random.nextInt(5); 
        int newInventory = entry.getValue() + restockAmount;
        seller.getInventory().put(entry.getKey(), newInventory);
        System.out.println("Visitor: Restocked " + restockAmount + " " + 
                  entry.getKey().getName() + " for " + seller.getName());
      }
    }

    if (random.nextDouble() < 0.3) {
      double marginAdjustment = 0.95 + (random.nextDouble() * 0.1);
      seller.setMargin(seller.getMargin() * marginAdjustment);
      System.out.println("Visitor: " + seller.getName() + " adjusted margin by factor of " + 
                        String.format("%.2f", marginAdjustment));
    }
  }

  @Override
  public void visit(Buyer buyer) {
    System.out.println("Visitor: Updating data for Buyer " + buyer.getName());
    double salary = 50 + random.nextInt(100);  
    buyer.setMoney(buyer.getMoney() + salary);
    System.out.println("Visitor: Buyer " + buyer.getName() + " received " + 
                      salary + " money. New balance: " + buyer.getMoney());

    for (Map.Entry<Product, Integer> entry : buyer.getNeeds().entrySet()) {
      if (entry.getKey().getType() == ProductType.ESSENTIAL) {
        int newNeed = entry.getValue() + 1 + random.nextInt(3); 
        buyer.getNeeds().put(entry.getKey(), newNeed);
      } else if (random.nextDouble() < 0.2) { 
        buyer.getNeeds().put(entry.getKey(), entry.getValue() + 1);
      }
    }
  }
}