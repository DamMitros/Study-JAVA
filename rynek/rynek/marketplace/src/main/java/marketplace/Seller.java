package marketplace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import marketplace.observer.Observer;
import marketplace.observer.Subject;
import marketplace.product.Product;
import marketplace.visitor.Element;
import marketplace.visitor.Visitor;

public class Seller implements Observer, Subject, Element{
  private String name;
  private Map<Product, Integer> inventory;
  private Map<Product, Double> productionCosts;
  private double margin;
  private double currentInflation;
  private List<Observer> buyerObservers;
  private Map<Product, Offer> currentOffers;
  private double totalProfit;

  public Seller(String name, double margin) {
    this.name = name;
    this.inventory = new HashMap<>();
    this.productionCosts = new HashMap<>();
    this.margin = margin;
    this.currentInflation = 0.0;
    this.buyerObservers = new ArrayList<>();
    this.currentOffers = new HashMap<>();
    this.totalProfit = 0.0;
  }

  public String getName() {
    return name;
  }

  public void addProductToSell(Product product, int initialQuantity, double productionCost) {
    this.inventory.put(product, initialQuantity);
    this.productionCosts.put(product, productionCost);
    updateOffer(product);
  }

  public void updateOffer(Product product) {
    if (!inventory.containsKey(product) || !productionCosts.containsKey(product)) {
      System.out.println("Product not found in inventory or production costs.");
      return;
    }
    double cost=productionCosts.get(product);
    double price=cost*(1+currentInflation)*(1+margin);
    int quantity=inventory.get(product);

    Offer offer = new Offer(product, price, this, quantity);
    currentOffers.put(product, offer);
    System.out.println(name + ": Updated offer for " + product.getName() + " Price: " + price + " Qty: " + quantity);
    notifyObservers("New offer for " + product.getName() + ": " + offer);
  }

  public Offer getOffer(Product product) {
    return currentOffers.get(product);
  }

  public double sellProduct(Product product, int quantityToSell) {
    if (inventory.containsKey(product) && inventory.get(product) >= quantityToSell) {
      Offer offer = currentOffers.get(product);
      if (offer!=null && offer.getQuantity() >= quantityToSell) {
        inventory.put(product, inventory.get(product) - quantityToSell);
        offer.setQuantity(offer.getQuantity() - quantityToSell);
        double transactionAmount = offer.getPrice() * quantityToSell;
        totalProfit += transactionAmount - (productionCosts.get(product) * quantityToSell);
        System.out.println(name + ": Sold " + quantityToSell + " of " + product.getName() + ". Remaining: " + inventory.get(product));
        updateOffer(product);
        return transactionAmount;
      }
    }
    System.out.println(name + ": Could not sell " + quantityToSell + " of " + product.getName() + ". Not enough stock or offer invalid.");
    return 0.0;   
  }

  public double getTotalProfit() {
    return totalProfit;
  }

  @Override
  public void update(String message) {
    System.out.println(name + "(Seller): Received update: " + message);
  } 

  @Override
  public void updateInflation(double newInflation) {
    this.currentInflation = newInflation;
    System.out.println(name + "(Seller): receive inflation update: " + newInflation);
    for (Product product : inventory.keySet()) {
      updateOffer(product);
    }
  }

  @Override
  public void registerObserver(Observer observer) {
    buyerObservers.add(observer);
  }

  @Override
  public void removeObserver(Observer observer) {
    buyerObservers.remove(observer);
  }

  @Override
  public void notifyObservers(String message) {
    for (Observer observer : buyerObservers) {
      observer.update(message);
    }
  }

  @Override
  public void notifyInflationChange(double newInflation) {
    System.out.println(name + "(Seller): Inflation change notification: " + newInflation);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

  public Map<Product, Integer> getInventory() {
    return inventory;
  }

  public void setMargin(double margin) {
    this.margin=margin;
    for (Product product : inventory.keySet()) {
      updateOffer(product);
    }
  }

  public double getMargin() {
    return margin;
  }

  public double getCurrentInflation() {
    return currentInflation;
  }
}
