package CommandManagerTest;

import javamarkt.cartoperation.*;
import javamarkt.cartoperation.operation.*;
import javamarkt.cartoperation.promotion.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RemovePromotionTest {
  private ShoppingCart cart;
  private CommandManager commandManager;
  private Promotion promotion;
  
  @BeforeEach
  void setUp() {
    cart = new ShoppingCart();
    commandManager = new CommandManager(cart);
    promotion = new FreeThirdItem();

    cart.addPromotion(promotion);
  }

  @Test
  void ExecuteOperationTest(){
    OperationCart removeOperation = new RemovePromotionOperation(promotion);
    commandManager.executeOperation(removeOperation);

    assertEquals(0, cart.showPromotions().size());
    assertEquals("Removed promotion: If you buy 3 items, the cheapest one is free", commandManager.getLastOperationDescription());
  }

  @Test
  void UndoOperationTest(){
    OperationCart removeOperation = new RemovePromotionOperation(promotion);
    commandManager.executeOperation(removeOperation);

    commandManager.undo();

    assertEquals(1, cart.showPromotions().size());
    assertEquals(true, commandManager.canRedo());
    assertEquals(false, commandManager.canUndo());
  }

  @Test
  void RedoOperationTest(){
    OperationCart removeOperation = new RemovePromotionOperation(promotion);
    commandManager.executeOperation(removeOperation);

    commandManager.undo();
    commandManager.redo();

    assertEquals(0, cart.showPromotions().size());
    assertEquals(false, commandManager.canRedo());
    assertEquals(true, commandManager.canUndo());
  }
}
