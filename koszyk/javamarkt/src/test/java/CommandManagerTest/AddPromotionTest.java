package CommandManagerTest;

import javamarkt.cartoperation.*;
import javamarkt.cartoperation.operation.*;
import javamarkt.cartoperation.promotion.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AddPromotionTest {
  private ShoppingCart cart;
  private CommandManager commandManager;

  @BeforeEach
  void setUp() {
    cart = new ShoppingCart();
    commandManager = new CommandManager(cart);
  }

  @Test
  void ExecuteOperationTest(){
    Promotion promotion = new FreeThirdItem();
    OperationCart addOperation = new AddPromotionOperation(promotion);
    commandManager.executeOperation(addOperation);
    
    assertEquals(1, cart.showPromotions().size());
    assertEquals("Added promotion: If you buy 3 items, the cheapest one is free", commandManager.getLastOperationDescription());
  }

  @Test
  void UndoOperationTest(){
    Promotion promotion = new FreeThirdItem();
    OperationCart addOperation = new AddPromotionOperation(promotion);
    commandManager.executeOperation(addOperation);

    commandManager.undo();

    assertEquals(0, cart.showPromotions().size());
    assertEquals(true, commandManager.canRedo());
    assertEquals(false, commandManager.canUndo());
  }

  @Test
  void RedoOperationTest(){
    Promotion promotion = new FreeThirdItem();
    OperationCart addOperation = new AddPromotionOperation(promotion);
    commandManager.executeOperation(addOperation);

    commandManager.undo();
    commandManager.redo();

    assertEquals(1, cart.showPromotions().size());
    assertEquals(false, commandManager.canRedo());
    assertEquals(true, commandManager.canUndo());
  }
}

