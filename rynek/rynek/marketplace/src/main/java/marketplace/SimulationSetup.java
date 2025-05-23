package marketplace;

import marketplace.product.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationSetup {
    
  public static void setupSimulation(Simulation simulation) {
    CentralBank centralBank = new CentralBank(0.04);
    simulation.setCentralBank(centralBank);
        
    List<Seller> sellers = createSellers();
    List<Buyer> buyers = createBuyers();
        
    setupObserverRelationships(centralBank, sellers, buyers);
        
    simulation.setSellers(sellers);
    simulation.setBuyers(buyers);
        
    System.out.println("Simulation setup complete.");
  }
    
  private static List<Seller> createSellers() {
    List<Seller> sellers = new ArrayList<>();

    Product bread = new Product("Bread", 2.0, ProductType.ESSENTIAL);
    Product milk = new Product("Milk", 1.5, ProductType.ESSENTIAL);
    Product vine = new Product("Vine", 5.0, ProductType.LUXURY);
    Product cigarettes = new Product("Cigarettes", 10.0, ProductType.LUXURY);

    Seller farmer = new Seller("Farmer Jacek", 0.2); 
    farmer.addProductToSell(bread, 50, 1.5);
    farmer.addProductToSell(milk, 30, 1.2);
        
    Seller store = new Seller("General Store", 0.3);
    store.addProductToSell(vine, 20, 8.0);
    store.addProductToSell(cigarettes, 30, 15.0);
    store.addProductToSell(milk, 20, 1.6);
        
    sellers.add(farmer);
    sellers.add(store);
        
    return sellers;
  }
    
  private static List<Buyer> createBuyers() {
    List<Buyer> buyers = new ArrayList<>();
        
    Product bread = new Product("Bread", 2.0, ProductType.ESSENTIAL);
    Product milk = new Product("Milk", 1.5, ProductType.ESSENTIAL);
    Product vine = new Product("Vine", 5.0, ProductType.LUXURY);
    Product cigarettes = new Product("Cigarettes", 10.0, ProductType.LUXURY);
        
    Buyer kasia = new Buyer("Kasia", 500.0);
    kasia.addNeed(bread, 3);
    kasia.addNeed(cigarettes, 5);
        
    Buyer jan = new Buyer("Jan", 300.0);
    jan.addNeed(bread, 2);
    jan.addNeed(milk, 2);
    jan.addNeed(vine, 1);
        
    buyers.add(kasia);
    buyers.add(jan);
        
    return buyers;
  }
    
  private static void setupObserverRelationships(CentralBank centralBank, List<Seller> sellers, List<Buyer> buyers) {
    for (Seller seller : sellers) {
      centralBank.registerObserver(seller);
    }
        
    for (Buyer buyer : buyers) {
      centralBank.registerObserver(buyer);
      for (Seller seller : sellers) {
        seller.registerObserver(buyer);
      }
    }
  }
}
