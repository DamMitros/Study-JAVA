import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import marketplace.Main;

public class MainTest {
  @Test
  void RunMainTest() {
    String[] args = {};
    assertDoesNotThrow(() -> {Main.main(args);});
  }

  @Test
  void testMainWithDifferentArgs() {
    String[] args = {"test"};
    assertDoesNotThrow(() -> {Main.main(args);});
  }
  
  @Test
  void testMainClassExists() {
    assertNotNull(new Main());
  }
}
