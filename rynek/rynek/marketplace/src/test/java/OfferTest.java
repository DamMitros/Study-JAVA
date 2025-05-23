import marketplace.product.*;
import marketplace.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OfferTest {
  private Product product;
  private Seller seller;
  private Offer offer;

  @BeforeEach
  void setUp() {
    product = new Product("Milk", 2.5, ProductType.ESSENTIAL);
    seller = new Seller("TestSeller", 0.2);
    offer = new Offer(product, 3.0, seller, 10);
  }

  @Test
  void offerCreationAndGetters() {
    assertEquals(product, offer.getProduct());
    assertEquals(3.0, offer.getPrice());
    assertEquals(seller, offer.getSeller());
    assertEquals(10, offer.getQuantity());
  }

  @Test
  void setPrice() {
    offer.setPrice(4.0);
    assertEquals(4.0, offer.getPrice());
  }

  @Test
  void setQuantity() {
    offer.setQuantity(15);
    assertEquals(15, offer.getQuantity());
  }

  @Test
  void offerToString() {
    String expectedString = "Offer{product=Milk, price=3.0, quantity=10, seller=TestSeller}";
    assertEquals(expectedString, offer.toString());
  }
}
