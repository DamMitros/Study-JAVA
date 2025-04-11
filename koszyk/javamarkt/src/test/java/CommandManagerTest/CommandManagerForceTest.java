package CommandManagerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javamarkt.cartoperation.CommandManager;
import javamarkt.cartoperation.ShoppingCart;

public class CommandManagerForceTest {
  private ShoppingCart cart;
  private CommandManager commandManager;

  @BeforeEach
  void setUp() {
    cart = new ShoppingCart();
    commandManager = new CommandManager(cart);
  }

  @Test
  void ForceUndoTest() {
    commandManager.undo();
    assertEquals(false, commandManager.canRedo());
  }

  @Test
  void ForceRedoTest() {
    commandManager.redo();
    assertEquals(false, commandManager.canUndo());
  }

  @Test
  void getLastOperationDescriptionTest() {
    String description = commandManager.getLastOperationDescription();
    assertEquals("No operations executed yet.", description);
  }
}
