package marketplace;

import marketplace.visitor.*;
import marketplace.observer.*;
import marketplace.product.*;

import java.util.HashMap;
import java.util.Map;

public class Buyer implements Observer, Element {
  private String name;
  private Map<Product, Integer> needs;
  private double money;
  private double currentInflationKnowledge;
  private Map<Product, Integer> purchasedProducts;

  public Buyer(String name, double money) {
    this.name = name;
    this.needs = new HashMap<>();
    this.money = money;
    this.currentInflationKnowledge = 0.0;
    this.purchasedProducts = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  public void addNeed(Product product, int quantity) {
    this.needs.put(product, quantity);
  }

  public double observeSellersOffer(Seller seller, Product product) {
    Offer offer = seller.getOffer(product);
    if (offer != null) {
      System.out.println(name + " (Buyer) observes offer from " + seller.getName() + " for " + product.getName() + " at " + offer.getPrice());
      return decideToBuy(offer);
    }
    return 0.0;
  }

  private boolean isWillingToBuy(Offer offer) {
    Product product = offer.getProduct();
    double basePrice = product.getBaseCost() * 2.0; 
    double priceRatio = offer.getPrice() / basePrice;

    double buyProbability = product.getType() == ProductType.ESSENTIAL ? 
      Math.max(0.3, 2.0 - priceRatio) : Math.max(0.1, 1.5 - priceRatio);

    if (0.0 < currentInflationKnowledge && currentInflationKnowledge < 0.3) {
      buyProbability *= (1 + currentInflationKnowledge);
    }
  
    return Math.random() < buyProbability;
  }

  public double decideToBuy(Offer offer) {
    Product product = offer.getProduct();
    int neededQuantity = needs.getOrDefault(product, 0);
    int quantityToBuy = Math.min(neededQuantity, offer.getQuantity());
    double transactionAmount = 0.0;

    if (quantityToBuy > 0 && money >= offer.getPrice() * quantityToBuy && isWillingToBuy(offer)) {
      transactionAmount = offer.getSeller().sellProduct(product, quantityToBuy);
      if (transactionAmount > 0) {
        money -= transactionAmount;
        purchasedProducts.put(product, purchasedProducts.getOrDefault(product, 0) + quantityToBuy);
        needs.put(product, neededQuantity - quantityToBuy); 
        System.out.println(name + " (Buyer): Bought " + quantityToBuy + " of " + 
          product.getName() + " for " + transactionAmount + ". Remaining money: " + money);
      }
    } else if (quantityToBuy > 0) {
      if (money < offer.getPrice() * quantityToBuy) {
        System.out.println(name + " (Buyer): Cannot afford " + quantityToBuy + " of " + 
                          product.getName() + " at " + offer.getPrice());
      } else {
        System.out.println(name + " (Buyer): Decided not to buy " + product.getName() + 
                          " at price " + offer.getPrice() + " (too expensive)");
      }
    }
    return transactionAmount;
  }

  public double getMoney() {
    return money;
  }

  @Override
  public void update(String message) {
    System.out.println(name + " (Buyer): Received update " + message);
  }

  @Override
  public void updateInflation(double newInflation) {
    this.currentInflationKnowledge = newInflation;
    System.out.println(name + " (Buyer): Inflation updated to " + newInflation);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

  public Map<Product, Integer> getNeeds() {
    return needs;
  }

  public void setMoney(double money) {
    this.money = money;
  }

  public double getCurrentInflationKnowledge() {
    return currentInflationKnowledge;
  }
}