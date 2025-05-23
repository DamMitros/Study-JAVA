package marketplace;

import marketplace.visitor.*;
import marketplace.product.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Simulation {
  private CentralBank centralBank;
  private List<Seller> sellers;
  private List<Buyer> buyers;
  private int currentTurn;
  private final int totalTurns;
  private Visitor dataVisitor;

  public Simulation(int totalTurns) {
    this.totalTurns = totalTurns;
    this.currentTurn = 0;
    this.sellers = new ArrayList<>();
    this.buyers = new ArrayList<>();
    this.dataVisitor = new DataUpdateVisitor(); 
  }

  public void setup() {
    SimulationSetup.setupSimulation(this);
  }

  public void setCentralBank(CentralBank centralBank) {
    this.centralBank = centralBank;
  }

  public void setSellers(List<Seller> sellers) {
    this.sellers = sellers;
  }

  public void setBuyers(List<Buyer> buyers) {
    this.buyers = buyers;
  }

  public void runTurn() {
    currentTurn++;
    System.out.println("\n--- Turn " + currentTurn + " ---");

    for (Buyer buyer : buyers) {
      for (Seller seller : sellers) {
        for (Product neededProduct : buyer.getNeeds().keySet()) {
          if (buyer.getNeeds().get(neededProduct) > 0) { 
            Offer offer = seller.getOffer(neededProduct);
            if (offer != null && offer.getQuantity() > 0) {
              double transactionAmount = buyer.observeSellersOffer(seller, neededProduct);
              if (transactionAmount > 0) {
                centralBank.recordTransaction(transactionAmount);
              }
            }
          }
        }
      }
    }

    System.out.println("\nVisitor applying updates...");
    for (Seller seller : sellers) {
      seller.accept(dataVisitor);
    }
    for (Buyer buyer : buyers) {
      buyer.accept(dataVisitor);
    }

    centralBank.endTurnUpdate(); 

    System.out.println("\nEnd of Turn " + currentTurn + " Summary:");
    for (Seller seller : sellers) {
      System.out.println("Seller " + seller.getName() + " Profit: " + seller.getTotalProfit());
      for(Map.Entry<Product, Integer> entry : seller.getInventory().entrySet()){
        System.out.println("  Inventory " + entry.getKey().getName() + ": " + entry.getValue());
      }
    }
    for (Buyer buyer : buyers) {
      System.out.println("Buyer " + buyer.getName() + " Money: " + buyer.getMoney());
      for(Map.Entry<Product, Integer> entry : buyer.getNeeds().entrySet()){
        if(entry.getValue() > 0) System.out.println("  Needs " + entry.getKey().getName() + ": " + entry.getValue());
      }
    }
    System.out.println("Central Bank Inflation for next turn (potentially): " + centralBank.getInflationRate());
  }

  public void runSimulation() {
    setup();

    for (int i = 0; i < totalTurns; i++) {
      runTurn();
      if (currentTurn >= totalTurns) {
        System.out.println("\nSimulation finished after " + totalTurns + " turns.");
        break;
      }
    }
  }

  public int getNumberOfSellers() {
    return sellers.size();
  }
  
  public int getNumberOfBuyers() {
    return buyers.size();
  }

  public int getCurrentTurn() {
    return currentTurn;
  }

  public List<Seller> getSellers() {
    return sellers;
  }

  public List<Buyer> getBuyers() {
    return buyers;
  }

  public CentralBank getCentralBank() {
    return centralBank;
  }
}