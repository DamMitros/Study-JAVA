import marketplace.product.*;
import marketplace.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class BuyerTest {
  private Buyer buyer;
  private Product essentialProduct;
  private Seller seller;
  private Offer offer;

  @BeforeEach
  void setUp() {
    essentialProduct = new Product("Essential Product", 10.0, ProductType.ESSENTIAL);
    buyer = new Buyer("TestBuyer", 100.0);
    seller = new Seller("TestSeller", 0.2);
    offer = new Offer(essentialProduct, 10.0, seller, 5);
  }

  @Test
  void addNeed() {
    buyer.addNeed(essentialProduct, 3);
    assertEquals(3, buyer.getNeeds().get(essentialProduct));
  }

  @Test
  void updateInflation() {
    buyer.updateInflation(0.1);
    assertEquals(0.1, buyer.getCurrentInflationKnowledge());
  }

  @Test
  void observeSellersOffer() {
    buyer.addNeed(essentialProduct, 3);
    double decision = buyer.observeSellersOffer(seller, essentialProduct);
    assertTrue(decision >= 0.0 && decision <= 3.0);
  }

  @Test
  void decideToBuy() {
    buyer.addNeed(essentialProduct, 3);
    double decision = buyer.decideToBuy(offer);
    assertTrue(decision >= 0.0 && decision <= 3.0);
  }

  @Test
  void isWillingToBuy() {
    buyer.addNeed(essentialProduct, 3);
    double decision = buyer.decideToBuy(offer);
    assertTrue(decision >= 0.0 && decision <= 3.0);
  }

  @Test
  void decideToBuy_cannotAfford() {
    buyer.setMoney(5.0);
    buyer.addNeed(essentialProduct, 3);
    double decision = buyer.decideToBuy(offer);
    assertEquals(0.0, decision);
  }

  @Test
  void decideToBuy_noNeed() {
    buyer.setMoney(100.0);
    double decision = buyer.decideToBuy(offer);
    assertEquals(0.0, decision);
  }
  
  @Test
  void testGetNeeds() {
    buyer.addNeed(essentialProduct, 3);
    assertEquals(3, buyer.getNeeds().get(essentialProduct));
  }

  @Test
  void testGetMoney() {
    assertEquals(100.0, buyer.getMoney(), 0.01);
  }

  @Test
  void testSetMoney() {
    buyer.setMoney(150.0);
    assertEquals(150.0, buyer.getMoney(), 0.01);
  }

  @Test
  void testGetCurrentInflationKnowledge() {
    assertEquals(0.0, buyer.getCurrentInflationKnowledge(), 0.01);
  }
}
