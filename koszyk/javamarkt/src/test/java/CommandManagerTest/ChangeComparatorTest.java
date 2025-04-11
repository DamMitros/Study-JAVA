package CommandManagerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javamarkt.Product;
import javamarkt.cartoperation.CommandManager;
import javamarkt.cartoperation.ShoppingCart;
import javamarkt.cartoperation.comparison.*;
import javamarkt.cartoperation.operation.ChangeComparatorOperation;
import javamarkt.cartoperation.operation.OperationCart;

public class ChangeComparatorTest {
  private ShoppingCart cart;
  private CommandManager commandManager;
  private Product product1, product2, product3;

  @BeforeEach
  void setUp() {
    cart = new ShoppingCart();
    commandManager = new CommandManager(cart);

    product1 = new Product("112", "Keyboard", 49.99);
    product2 = new Product("113", "Mouse", 29.99);
    product3 = new Product("114", "Monitor", 199.99);
    
    cart.addProduct(product1);
    cart.addProduct(product2);
    cart.addProduct(product3);
  }

  @Test
  void ExecuteOperationTest() {
    CompareProduct oldComparator = new CompositeComparator(
      new PriceCompare(false),
      new NameCompare(true)
    );

    CompareProduct newComparator = new CompositeComparator(
      new NameCompare(true),
      new PriceCompare(true)
    );

    OperationCart changeOperation = new ChangeComparatorOperation(newComparator, oldComparator);
    commandManager.executeOperation(changeOperation);

    Product[] products = cart.getProducts();
    assertEquals("Keyboard", products[0].getName()); 
    assertEquals("Changed comparator", commandManager.getLastOperationDescription());
  }

  @Test
  void UndoOperationTest() {
    CompareProduct oldComparator = new CompositeComparator(
      new PriceCompare(false),
      new NameCompare(true)
    );

    CompareProduct newComparator = new CompositeComparator(
      new NameCompare(true),
      new PriceCompare(true)
    );

    OperationCart changeOperation = new ChangeComparatorOperation(newComparator, oldComparator);
    commandManager.executeOperation(changeOperation);

    commandManager.undo();

    Product[] products = cart.getProducts();
    assertEquals("Monitor", products[0].getName()); 
  }

  @Test
  void RedoOperationTest() {
    CompareProduct oldComparator = new CompositeComparator(
      new PriceCompare(false),
      new NameCompare(true)
    );

    CompareProduct newComparator = new CompositeComparator(
      new NameCompare(true),
      new PriceCompare(true)
    );

    OperationCart changeOperation = new ChangeComparatorOperation(newComparator, oldComparator);
    commandManager.executeOperation(changeOperation);

    commandManager.undo();
    commandManager.redo();

    Product[] products = cart.getProducts();
    assertEquals("Keyboard", products[0].getName()); 
  }
}
