import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javamarkt.Product;
import javamarkt.cartoperation.ShoppingCart;
import javamarkt.cartoperation.comparison.*;

public class ComparisationTest {
  private Product product1, product2;
  private ShoppingCart cart;

  @BeforeEach
  void setUp() {
    cart = new ShoppingCart();

    product1 = new Product("112", "Keyboard", 49.99);
    product2 = new Product("113", "Mouse", 29.99);

    cart.addProduct(product1);
    cart.addProduct(product2);
  }

  @Test
  void testNameCompareAscending() {
    CompareProduct comparator = new NameCompare(true);
    cart.setComparator(comparator);

    Product[] sortedProducts = cart.getProducts();
    assertEquals("Keyboard", sortedProducts[0].getName());
    assertEquals("Mouse", sortedProducts[1].getName());
  }

  @Test
  void testNameCompareDescending() {
    CompareProduct comparator = new NameCompare(false);
    cart.setComparator(comparator);

    Product[] sortedProducts = cart.getProducts();
    assertEquals("Mouse", sortedProducts[0].getName());
    assertEquals("Keyboard", sortedProducts[1].getName());
  }

  @Test
  void testPriceCompareAscending() {
    CompareProduct comparator = new PriceCompare(true);
    cart.setComparator(comparator);

    Product[] sortedProducts = cart.getProducts();
    assertEquals("Mouse", sortedProducts[0].getName());
    assertEquals("Keyboard", sortedProducts[1].getName());
  }

  @Test
  void testPriceCompareDescending() {
    CompareProduct comparator = new PriceCompare(false);
    cart.setComparator(comparator);

    Product[] sortedProducts = cart.getProducts();
    assertEquals("Keyboard", sortedProducts[0].getName());
    assertEquals("Mouse", sortedProducts[1].getName());
  }

  @Test
  void testCompositeComparator() {
    cart.addProduct(new Product("116", "PremiumMouse", 49.99));
    cart.addProduct(new Product("117", "CheapKeyboard", 29.99));

    CompareProduct compositeComp = new CompositeComparator(
        new PriceCompare(false), 
        new NameCompare(true)     
    );
    cart.setComparator(compositeComp);

    Product[] sortedProducts = cart.getProducts();
    assertEquals("Keyboard", sortedProducts[0].getName());
    assertEquals("PremiumMouse", sortedProducts[1].getName());
    assertEquals("CheapKeyboard", sortedProducts[2].getName());
    assertEquals("Mouse", sortedProducts[3].getName());
    assertEquals(49.99, sortedProducts[0].getPrice());
    assertEquals(49.99, sortedProducts[1].getPrice());
    assertEquals(29.99, sortedProducts[2].getPrice());
    assertEquals(29.99, sortedProducts[3].getPrice());
  }
}
