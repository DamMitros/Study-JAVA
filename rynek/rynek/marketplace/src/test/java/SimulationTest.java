import marketplace.*;
import marketplace.product.Product;
import marketplace.product.ProductType;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

public class SimulationTest {
  private Simulation simulation;
  
  @BeforeEach
  void setUp() {
    simulation = new Simulation(5);
  }

  @Test
  void testSetup() {
    assertDoesNotThrow(() -> SimulationSetup.setupSimulation(simulation));
    assertEquals(2, simulation.getNumberOfSellers());
    assertEquals(2, simulation.getNumberOfBuyers());
  }

  @Test
  void testRunSimulation() {
    assertDoesNotThrow(() -> simulation.runSimulation());
  }

  @Test
  void testRunTurn() {
    simulation.setup();
    assertDoesNotThrow(() -> simulation.runTurn());
  }
  
  @Test
  void testRunMultipleTurns() {
    int expectedTurns = 3;
    Simulation shortSimulation = new Simulation(expectedTurns);
    shortSimulation.setup();
    assertEquals(0, shortSimulation.getCurrentTurn());
    assertDoesNotThrow(() -> {
      shortSimulation.runSimulation();
    });
    assertEquals(expectedTurns, shortSimulation.getCurrentTurn());
  }

  @Test
  void testSimulationSetupClassExists() {
    assertNotNull(new SimulationSetup());
  }

  @Test
  void testZeroTurnsSimulation() {
    Simulation zeroTurnSim = new Simulation(0);
    zeroTurnSim.setup();
    zeroTurnSim.runSimulation();
    assertEquals(0, zeroTurnSim.getCurrentTurn());
  }
  
  @Test
  void testRunTurnOfferNull() {
    Simulation simulation = new Simulation(1);
    CentralBank bank = new CentralBank(0.05);
    simulation.setCentralBank(bank);

    Seller sellerNoOffer = new Seller("NoOfferSeller", 0.2);
    Product productA = new Product("A", 1.0, ProductType.ESSENTIAL);
    sellerNoOffer.addProductToSell(productA, 10, 1.0);
    sellerNoOffer.getInventory().put(productA, 10);
    sellerNoOffer.getOffer(productA).setQuantity(0); 

    Seller sellerZeroQty = new Seller("ZeroQtySeller", 0.2);
    Product productB = new Product("B", 1.0, ProductType.ESSENTIAL);
    sellerZeroQty.addProductToSell(productB, 10, 1.0);
    sellerZeroQty.getOffer(productB).setQuantity(0);

    Seller sellerValid = new Seller("ValidSeller", 0.2);
    Product productC = new Product("C", 1.0, ProductType.ESSENTIAL);
    sellerValid.addProductToSell(productC, 10, 1.0);
    
    Buyer buyer = new Buyer("TestBuyer", 100.0);
    buyer.addNeed(productA, 1);
    buyer.addNeed(productB, 1);
    buyer.addNeed(productC, 1);

    simulation.setSellers(List.of(sellerNoOffer, sellerZeroQty, sellerValid));
    simulation.setBuyers(List.of(buyer));
    assertDoesNotThrow(() -> simulation.runTurn());
  }
}
