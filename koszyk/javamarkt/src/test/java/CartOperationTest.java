import javamarkt.Product;
import javamarkt.cartoperation.*;
import javamarkt.cartoperation.comparison.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartOperationTest {
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
  void AddProductTest(){
    cart.addProduct(product1);
    cart.addProduct(product2);

    assertEquals(2, cart.getProducts().length);
    assertEquals(79.98, cart.calculateTotalPrice());
  }
  
  @Test
  void RemoveProductTest(){
    cart.addProduct(product1);
    cart.addProduct(product2);
    cart.addProduct(product3);

    cart.removeProduct(product2);
    assertEquals(2, cart.getProducts().length);
    assertEquals(product1, cart.getProducts()[1]);
  }

  @Test
  void RemoveNonExistingProductTest(){
    cart.addProduct(product1);
    cart.addProduct(product2);
    cart.addProduct(product3);

    Product nonExistingProduct = new Product("999", "NonExisting", 0.00);
    cart.removeProduct(nonExistingProduct);

    assertEquals(3, cart.getProducts().length);
  }

  @Test
  void FindCheapestProduct() {
    cart.addProduct(product1);
    cart.addProduct(product2);
    cart.addProduct(product3);

    Product cheapestProduct = cart.FindExtremeProduct(true);
    assertEquals(product2, cheapestProduct);
  }

  @Test 
  void FindMostExpensiveProduct() {
    cart.addProduct(product1);
    cart.addProduct(product2);
    cart.addProduct(product3);

    Product mostExpensiveProduct = cart.FindExtremeProduct(false);
    assertEquals(product3, mostExpensiveProduct);
  }

  @Test
  void FindExtremeProductWithNoProducts() {
    Product cheapestProduct = cart.FindExtremeProduct(true);
    assertNull(cheapestProduct);

    Product mostExpensiveProduct = cart.FindExtremeProduct(false);
    assertNull(mostExpensiveProduct);
  }

  @Test
  void Find2CheapestProducts() {
    cart.addProduct(product1);
    cart.addProduct(product2);
    cart.addProduct(product3);
    cart.addProduct(product4);

    Product[] cheapestProducts = cart.FindExtremeProducts(2, true);
    assertEquals(2, cheapestProducts.length);
    assertEquals(product2, cheapestProducts[0]);
    assertEquals(product1, cheapestProducts[1]);
  }

  @Test
  void Find2MostExpensiveProducts() {
    cart.addProduct(product1);
    cart.addProduct(product2);
    cart.addProduct(product3);
    cart.addProduct(product4);

    Product[] mostExpensiveProducts = cart.FindExtremeProducts(2, false);
    assertEquals(2, mostExpensiveProducts.length);
    assertEquals(product3, mostExpensiveProducts[0]);
    assertEquals(product4, mostExpensiveProducts[1]);
  } 

  @Test
  void FindExtremeProductsWithNoProducts() {
    Product[] cheapestProducts = cart.FindExtremeProducts(2, true);
    assertEquals(0, cheapestProducts.length);

    Product[] mostExpensiveProducts = cart.FindExtremeProducts(2, false);
    assertEquals(0, mostExpensiveProducts.length);
  }

  @Test
  void Find0ExtremeProducts(){
    Product[] cheapestProducts = cart.FindExtremeProducts(0, true);
    assertEquals(0, cheapestProducts.length);
  }

  @Test
  void ChangeComparatorOperationTest(){
    cart.addProduct(product1);
    cart.addProduct(product2);
    cart.addProduct(product3);

    assertEquals(product3, cart.getProducts()[0]);
    CompareProduct nameCompare = new NameCompare(true);
    cart.setComparator(nameCompare);
    assertEquals(product1, cart.getProducts()[0]);
  }

  @Test
  void EmptyCartTest(){
    assertEquals(0, cart.getProducts().length);
    assertEquals(0, cart.calculateTotalPrice());
  }

  @Test
  void ResizingCartTest(){
    for (int i = 0; i < 12; i++) {
      cart.addProduct(new Product("Product" + i, "Product" + i, 10.0));
    }

    assertEquals(12, cart.getProducts().length);
    assertEquals(120.00, cart.calculateTotalPrice());
  }
}