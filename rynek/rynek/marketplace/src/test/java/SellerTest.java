import marketplace.*;
import marketplace.product.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SellerTest {
  private Seller seller;
  private Product product;

  @BeforeEach
  void setUp() {
    product = new Product("TestProduct", 10.0, ProductType.ESSENTIAL);
    seller = new Seller("TestSeller", 0.2);
  }

  @Test
  void testSellerCreationAndGetters() {
    assertEquals("TestSeller", seller.getName());
    assertEquals(0.2, seller.getMargin());
  }

  @Test
  void testAddProductToSell() {
    seller.addProductToSell(product, 10, 1.5);
    assertEquals(10, seller.getInventory().get(product));
    assertNotNull(seller.getOffer(product));
  }

  @Test
  void testUpdateOffer() {
    seller.addProductToSell(product, 10, 1.5);
    Offer initialOffer = seller.getOffer(product);
    double initialPrice = initialOffer.getPrice();
        
    seller.setMargin(0.3);
    Offer updatedOffer = seller.getOffer(product);
        
    assertNotEquals(initialPrice, updatedOffer.getPrice());
  }
   
  @Test
  void testUpdateOfferWithMissingProduct() {
    Product missingProduct = new Product("MissingProduct", 5.0, ProductType.ESSENTIAL);
    seller.updateOffer(missingProduct);
    assertNull(seller.getOffer(missingProduct));
  }

  @Test
  void testUpdateOfferMissingProductionCost() {
    seller.getInventory().put(product, 10);
    seller.updateOffer(product);
    assertNull(seller.getOffer(product));
  }
  
  @Test
  void testSellProduct() {
    seller.addProductToSell(product, 10, 1.5);
    double transactionAmount = seller.sellProduct(product, 3);
        
    assertTrue(transactionAmount > 0);
    assertEquals(7, seller.getInventory().get(product));
  }

  @Test 
  void testSellProductNotEnoughInventory() {
    seller.addProductToSell(product, 2, 1.0);
    double result = seller.sellProduct(product, 5); 
    assertEquals(0.0, result);
  }

  @Test
  void testSellProductNotInInventory() {
    Product unknownProduct = new Product("Unknown", 5.0, ProductType.LUXURY);
    double result = seller.sellProduct(unknownProduct, 1);
    assertEquals(0.0, result);
  }

  @Test
  void testSellProductWithNullOffer(){
    Product prod = new Product("NoOffer", 5.0, ProductType.LUXURY);
    seller.getInventory().put(prod, 5); 
    double result = seller.sellProduct(prod, 2); 
    assertEquals(0.0, result);
  }
  
  @Test
  void testSellProductValidOfferAndQuantity() {
    seller.updateInflation(0.2);
    seller.setMargin(0.2);
    seller.addProductToSell(product, 10, 2.0);
    double result = seller.sellProduct(product, 5);
    assertEquals(5.0*2.0*(1+seller.getCurrentInflation())*(1+seller.getMargin()), result, 0.01);
  }

  @Test
  void testSellProductWithLowOfferQuantity() {
    seller.addProductToSell(product, 10, 1.5);
    seller.getOffer(product).setQuantity(2);
    double result = seller.sellProduct(product, 3);
    assertEquals(0.0, result);
  }
  
  @Test
  void testSetMargin() {
    seller.setMargin(0.3);
    assertEquals(0.3, seller.getMargin());
  }
    
  @Test
  void testUpdateInflation() {
    seller.addProductToSell(product, 10, 1.5);
    double initialPrice = seller.getOffer(product).getPrice();
        
    seller.updateInflation(0.1);
    double updatedPrice = seller.getOffer(product).getPrice();
    assertTrue(updatedPrice > initialPrice);
  }
    
  @Test
  void testTotalProfit() {
    seller.addProductToSell(product, 10, 1.5);
    seller.sellProduct(product, 3);
        
    assertTrue(seller.getTotalProfit() > 0);
  }

  @Test
  void testUpdateMessage() {
    String message = "Test message";
    seller.update(message);
  }

  @Test
  void testRemoveObserver() {
    Buyer buyer = new Buyer("TestBuyer", 100.0);
    seller.registerObserver(buyer);
    seller.removeObserver(buyer);
  }

  @Test
  void testNotifyInflationChange(){
    double newInflation = 0.05;
    seller.notifyInflationChange(newInflation);
  }
}