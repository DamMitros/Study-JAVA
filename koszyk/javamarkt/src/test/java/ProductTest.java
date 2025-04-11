import javamarkt.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
  @Test
  void testProductCreate(){
    Product product = new Product("009", "Computer", 8499.99);

    assertEquals("009", product.getCode());
    assertEquals("Computer", product.getName());
    assertEquals(8499.99, product.getPrice());
    assertEquals(8499.99, product.getDiscountPrice());
  }

  @Test
  void ProductSetDiscountPriceTest(){
    Product product = new Product("009", "Computer", 8499.99);
    product.setDiscountPrice(7999.99);

    assertEquals(7999.99, product.getDiscountPrice());
  }

  @Test
  void ProductResetDiscountPriceTest(){
    Product product = new Product("009", "Computer", 8499.99);
    product.setDiscountPrice(7999.99);
    product.resetDiscountPrice();

    assertEquals(8499.99, product.getDiscountPrice());
  }
}
