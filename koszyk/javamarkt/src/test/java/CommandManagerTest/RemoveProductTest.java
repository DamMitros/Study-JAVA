package CommandManagerTest;

import javamarkt.Product;
import javamarkt.cartoperation.*;
import javamarkt.cartoperation.operation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RemoveProductTest {
  private ShoppingCart cart;
  private CommandManager commandManager;
  private Product product1, product2;
  
  @BeforeEach
  void setUp() {
    cart = new ShoppingCart();
    commandManager = new CommandManager(cart);

    product1 = new Product("112", "Keyboard", 49.99);
    product2 = new Product("113", "Mouse", 29.99);

    cart.addProduct(product1);
    cart.addProduct(product2);
  }

  @Test
  void ExecuteOperationTest(){
    OperationCart removeOperation = new RemoveProductOperation(product1);
    commandManager.executeOperation(removeOperation);

    assertEquals(1, cart.getProducts().length);
    assertEquals(product2, cart.getProducts()[0]);
    assertEquals(false, commandManager.canRedo());
    assertEquals(true, commandManager.canUndo());
    assertEquals("Removed product: Keyboard", commandManager.getLastOperationDescription());
  }

  @Test
  void UndoOperationTest(){
    OperationCart removeOperation = new RemoveProductOperation(product1);
    commandManager.executeOperation(removeOperation);

    commandManager.undo();

    assertEquals(2, cart.getProducts().length);
    assertEquals(true, commandManager.canRedo());
    assertEquals(false, commandManager.canUndo());
  }

  @Test
  void RedoOperationTest(){
    OperationCart removeOperation = new RemoveProductOperation(product1);
    commandManager.executeOperation(removeOperation);

    commandManager.undo();
    commandManager.redo();

    assertEquals(1, cart.getProducts().length);
    assertEquals(false, commandManager.canRedo());
    assertEquals(true, commandManager.canUndo());
  }
}
