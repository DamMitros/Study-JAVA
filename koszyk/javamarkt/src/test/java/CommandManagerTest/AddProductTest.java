package CommandManagerTest;
import javamarkt.Product;
import javamarkt.cartoperation.*;
import javamarkt.cartoperation.operation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AddProductTest {
  private ShoppingCart cart;
  private CommandManager commandManager;
  private Product product1, product2;

  @BeforeEach
  void setUp() {
    cart = new ShoppingCart();
    commandManager = new CommandManager(cart);

    product1 = new Product("112", "Keyboard", 49.99);
    product2 = new Product("113", "Mouse", 29.99);
  }

  @Test
  void ExecuteOperationTest(){
    OperationCart addOperation = new AddProductOperation(product1);
    commandManager.executeOperation(addOperation);

    assertEquals(1, cart.getProducts().length);
    assertEquals(product1, cart.getProducts()[0]);

    assertEquals(false, commandManager.canRedo());
    assertEquals(true, commandManager.canUndo());
    assertEquals("Added product: Keyboard", commandManager.getLastOperationDescription());
  }

  @Test
  void UndoOperationTest(){
    OperationCart addOperation = new AddProductOperation(product1);
    commandManager.executeOperation(addOperation);

    commandManager.undo();

    assertEquals(0, cart.getProducts().length);
    assertEquals(true, commandManager.canRedo());
    assertEquals(false, commandManager.canUndo());
  }

  @Test
  void RedoOperationTest(){
    OperationCart addOperation1 = new AddProductOperation(product1);
    OperationCart addOperation2 = new AddProductOperation(product2);
    commandManager.executeOperation(addOperation1);
    commandManager.executeOperation(addOperation2);

    commandManager.undo();
    commandManager.undo();

    commandManager.redo();

    assertEquals(1, cart.getProducts().length);
    assertEquals(product1, cart.getProducts()[0]);
    assertEquals(true, commandManager.canRedo());
    assertEquals(true, commandManager.canUndo());
  }
}
