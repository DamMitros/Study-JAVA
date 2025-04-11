import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javamarkt.Product;
import javamarkt.cartoperation.ShoppingCart;
import javamarkt.cartoperation.promotion.*;

public class PromotionTest {
  private ShoppingCart cart;
  private Product product1, product2, product3, product4;

  @BeforeEach
  void setUp() {
    cart = new ShoppingCart();

    product1 = new Product("112", "Keyboard", 49.99);
    product2 = new Product("113", "Mouse", 29.99);
    product3 = new Product("114", "Monitor", 199.99);
    product4 = new Product("115", "Printer", 89.99);
  }

  @Test
  void FreeThirdItemTest(){
    Promotion promotion = new FreeThirdItem();
    cart.addProduct(product1);
    cart.addProduct(product2);
    cart.addProduct(product3);
    cart.addProduct(product4);

    cart.addPromotion(promotion);
    double expectedTotal = product1.getDiscountPrice() + product4.getDiscountPrice() + product3.getDiscountPrice();
    assertEquals(expectedTotal, cart.calculateTotalPrice());
  }

  @Test
  void FreeThirdItemFailTest(){
    Promotion promotion = new FreeThirdItem();
    cart.addProduct(product1);

    cart.addPromotion(promotion);
    double expectedTotal = product1.getPrice();
    assertEquals(expectedTotal, cart.calculateTotalPrice());
  }

  @Test
  void DiscountOver300Test(){
    Promotion promotion = new DiscountOver300();
    cart.addProduct(product1);
    cart.addProduct(product2);
    cart.addProduct(product3);
    cart.addProduct(product4);

    cart.addPromotion(promotion);
    double expectedTotal = product1.getDiscountPrice() + product2.getDiscountPrice() + product3.getDiscountPrice() + product4.getDiscountPrice();
    double discount = promotion.calculatePromotion(cart);
    double finalPrice = Math.round((expectedTotal-discount)*100)/100.0;
    assertEquals(351.46, cart.calculateTotalPrice());
    assertEquals(finalPrice, cart.calculateTotalPrice());
  }

  @Test
  void DiscountOver300FailTest(){
    Promotion promotion = new DiscountOver300();
    cart.addProduct(product1);
    cart.addProduct(product2);

    cart.addPromotion(promotion);
    double expectedTotal = product1.getDiscountPrice() + product2.getDiscountPrice();
    assertEquals(expectedTotal, cart.calculateTotalPrice());
  }

  @Test
  void OneTimeDiscountTest(){
    Promotion promotion = new OneTimeDiscount("113");
    cart.addProduct(product1);
    cart.addProduct(product2);

    cart.addPromotion(promotion);
    double expectedTotal = product1.getDiscountPrice() + product2.getDiscountPrice();
    double discount = promotion.calculatePromotion(cart);
    double finalPrice = Math.round((expectedTotal-discount)*100)/100.0;
    assertEquals(70.98, cart.calculateTotalPrice());
    assertEquals(finalPrice, cart.calculateTotalPrice());
  }

  @Test
  void OneTimeDiscountFailTest(){
    Promotion promotion = new OneTimeDiscount("112");
    cart.addProduct(product3);
    cart.addProduct(product4);

    cart.addPromotion(promotion);
    double expectedTotal = product3.getDiscountPrice() + product4.getDiscountPrice();
    assertEquals(expectedTotal, cart.calculateTotalPrice());
  }
  
  @Test
  void FreeMugTest(){
    Promotion promotion = new FreeMug();
    cart.addProduct(product3);
    cart.addProduct(product4);

    cart.addPromotion(promotion);
    double expectedTotal = product3.getDiscountPrice() + product4.getDiscountPrice();
    assertEquals(expectedTotal, cart.calculateTotalPrice());
    assertEquals(3, cart.getProducts().length);

    boolean foundMug = false;
    for (Product product : cart.getProducts()) {
      if (product.getCode().equals("987")) {
        foundMug = true;
        break;
      }
    }
    assertEquals(true, foundMug);
  }

  @Test
  void FreeMugInCartTest(){
    Promotion promotion = new FreeMug();
    cart.addProduct(product3);
    cart.addProduct(product4);
    cart.addProduct(new Product("987", "Mug", 25.0));

    cart.addPromotion(promotion);
    double expectedTotal = product3.getDiscountPrice() + product4.getDiscountPrice();
    assertEquals(expectedTotal, cart.calculateTotalPrice());
    assertEquals(3, cart.getProducts().length);
  }

  @Test
  void FreeMugFailTest(){
    Promotion promotion = new FreeMug();
    cart.addProduct(product1);
    cart.addProduct(product2);

    cart.addPromotion(promotion);
    double expectedTotal = product1.getDiscountPrice() + product2.getDiscountPrice();
    assertEquals(expectedTotal, cart.calculateTotalPrice());
    assertEquals(2, cart.getProducts().length);
  }

  @Test
  void MultiplePromotionsTest(){
    Promotion discountPromotion = new DiscountOver300();
    Promotion freeItemPromotion = new FreeThirdItem();

    cart.addProduct(product1);
    cart.addProduct(product2);
    cart.addProduct(product3);
    cart.addProduct(product4);

    cart.addPromotion(discountPromotion);
    cart.addPromotion(freeItemPromotion);

    double totalPrice = cart.calculateTotalPrice();
    assertEquals(321.47, totalPrice);
  }

  @Test
  void allPromotionForceTest(){
    Promotion promotion1 = new FreeThirdItem();
    Promotion promotion2 = new DiscountOver300();
    Promotion promotion3 = new OneTimeDiscount("112");
    Promotion promotion4 = new FreeMug();

    double discount1 = promotion1.calculatePromotion(cart);
    double discount2 = promotion2.calculatePromotion(cart);  
    double discount3 = promotion3.calculatePromotion(cart);
    double discount4 = promotion4.calculatePromotion(cart);
 
    assertEquals(0.0, discount1);
    assertEquals(0.0, discount2);
    assertEquals(0.0, discount3);
    assertEquals(0.0, discount4);

    assertEquals(false, promotion1.isApplicable(cart));
    assertEquals(false, promotion2.isApplicable(cart));
    assertEquals(false, promotion3.isApplicable(cart));
    assertEquals(false, promotion4.isApplicable(cart));
  }
}
