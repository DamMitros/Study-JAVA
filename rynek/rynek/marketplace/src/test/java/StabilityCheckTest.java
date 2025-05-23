import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import marketplace.CentralBank;
import marketplace.Simulation;
import marketplace.Seller;
import marketplace.Buyer;
import marketplace.product.*;

public class StabilityCheckTest {   
  @Test
  void testInflationStabilityNormalConditions() {
    Simulation simulation = new Simulation(100); 
    simulation.setup();
    CentralBank bank = simulation.getCentralBank();
    for (int i = 0; i < 50; i++) {
      simulation.runTurn();
    }
  
    double[] inflationRates = new double[50]; 
    for (int i = 0; i < 50; i++) {
      simulation.runTurn();
      inflationRates[i] = bank.getInflationRate();
    }
  
    double sum = 0;
    for (double rate : inflationRates) {
      sum += rate;
    }
    double mean = inflationRates.length > 0 ? sum / inflationRates.length : 0.0;
  
    double sumOfSquares = 0;
    for (double rate : inflationRates) {
      sumOfSquares += (rate - mean) * (rate - mean);
    }    
    double standardDeviation = inflationRates.length > 0 ? Math.sqrt(sumOfSquares / inflationRates.length) : 0.0;
    assertTrue(mean == 0.0 || standardDeviation < (Math.abs(mean) * 0.25));
  }

  @Test
  void testInflationStabilityAfterShock() {
    Simulation simulation = new Simulation(50);
    simulation.setup();
    CentralBank bank = simulation.getCentralBank();

    bank.setInflationRate(1.0); 
    double maxInflation = bank.getInflationRate();
    double minInflation = bank.getInflationRate();
  
    for (int i = 0; i < 50; i++) {
      simulation.runTurn();
      double currentInflation = bank.getInflationRate();
      if (currentInflation > maxInflation) maxInflation = currentInflation;
      if (currentInflation < minInflation) minInflation = currentInflation;
    }
    
    assertTrue(bank.getInflationRate() < 0.25);
  }

  @Test
  void testProductShortage() {
    Simulation simulation = new Simulation(100);
    simulation.setup();
    CentralBank bank = simulation.getCentralBank();
    Product targetProduct = new Product("Bread", 2.0, ProductType.ESSENTIAL);
    for (Seller seller : simulation.getSellers()) {
      if (seller.getInventory().containsKey(targetProduct)) {
        seller.getInventory().put(targetProduct, 1); 
        seller.updateOffer(targetProduct); 
      }
    }

    for (int i = 0; i < 30; i++) {
      simulation.runTurn();
    }

    double[] inflationRates = new double[50];
    for (int i = 0; i < 50; i++) {
      simulation.runTurn();
      inflationRates[i] = bank.getInflationRate();
    }

    double sum = 0;
    for (double rate : inflationRates) {
      sum += rate;
    }
    double mean = inflationRates.length > 0 ? sum / inflationRates.length : 0.0;

    double sumOfSquares = 0;
    for (double rate : inflationRates) {
      sumOfSquares += (rate - mean) * (rate - mean);
    }
    double standardDeviation = inflationRates.length > 0 ? Math.sqrt(sumOfSquares / inflationRates.length) : 0.0;
    assertTrue(mean == 0.0 || standardDeviation < (Math.abs(mean) * 0.25));
  }

  @Test
  void testProductSurplus() {
    Simulation simulation = new Simulation(100);
    simulation.setup();
    CentralBank bank = simulation.getCentralBank();
    Product targetProduct = new Product("Milk", 1.5, ProductType.ESSENTIAL);
    for (Seller seller : simulation.getSellers()) {
      if (seller.getInventory().containsKey(targetProduct)) {
        seller.getInventory().put(targetProduct, 500); 
        seller.updateOffer(targetProduct);
      }
    }

    for (int i = 0; i < 30; i++) {
      simulation.runTurn();
    }

    double[] inflationRates = new double[50];
    for (int i = 0; i < 50; i++) {
      simulation.runTurn();
      inflationRates[i] = bank.getInflationRate();
    }

    double sum = 0;
    for (double rate : inflationRates) {
      sum += rate;
    }
    double mean = inflationRates.length > 0 ? sum / inflationRates.length : 0.0;

    double sumOfSquares = 0;
    for (double rate : inflationRates) {
      sumOfSquares += (rate - mean) * (rate - mean);
    }
    double standardDeviation = inflationRates.length > 0 ? Math.sqrt(sumOfSquares / inflationRates.length) : 0.0;
    assertTrue(mean == 0.0 || standardDeviation < (Math.abs(mean) * 0.25));
  }

  @Test
  void testPoorBuyers() {
    Simulation simulation = new Simulation(100);
    simulation.setup();
    CentralBank bank = simulation.getCentralBank();

    for (Buyer buyer : simulation.getBuyers()) {
      buyer.setMoney(20.0); 
    }

    for (int i = 0; i < 30; i++) { 
      simulation.runTurn();
    }

    double[] inflationRates = new double[50];
    for (int i = 0; i < 50; i++) {
      simulation.runTurn();
      inflationRates[i] = bank.getInflationRate();
    }

    double sum = 0;
    for (double rate : inflationRates) {
      sum += rate;
    }
    double mean = inflationRates.length > 0 ? sum / inflationRates.length : 0.0;

    double sumOfSquares = 0;
    for (double rate : inflationRates) {
      sumOfSquares += (rate - mean) * (rate - mean);
    }
    double standardDeviation = inflationRates.length > 0 ? Math.sqrt(sumOfSquares / inflationRates.length) : 0.0;
    assertTrue(mean == 0.0 || standardDeviation < (Math.abs(mean) * 0.25));
  }

  @Test
  void testWealthyBuyers() {
    Simulation simulation = new Simulation(100);
    simulation.setup();
    CentralBank bank = simulation.getCentralBank();
    for (Buyer buyer : simulation.getBuyers()) {
      buyer.setMoney(10000.0);
    }
    
    for (int i = 0; i < 30; i++) {
      simulation.runTurn();
    }

    double[] inflationRates = new double[50];
    for (int i = 0; i < 50; i++) {
      simulation.runTurn();
      inflationRates[i] = bank.getInflationRate();
    }

    double sum = 0;
    for (double rate : inflationRates) {
      sum += rate;
    }
    double mean = inflationRates.length > 0 ? sum / inflationRates.length : 0.0;

    double sumOfSquares = 0;
    for (double rate : inflationRates) {
      sumOfSquares += (rate - mean) * (rate - mean);
    }
    double standardDeviation = inflationRates.length > 0 ? Math.sqrt(sumOfSquares / inflationRates.length) : 0.0;
    assertTrue(mean == 0.0 || standardDeviation < (Math.abs(mean) * 0.25));
  }
}