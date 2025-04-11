import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import stack.Stack;

public class StackTest {
  private Stack stack;

  @BeforeEach
  void setUp() {
    stack = new Stack();
  }

  @Test
  void pushAndPopTest() {
    stack.push("test");
    assertEquals("test", stack.pop());
  }

  @Test
  void popEmptyStackTest() {
    assertEquals(null, stack.pop());
  }

  @Test
  void peekTest() {
    stack.push("test");
    assertEquals("test", stack.peek());
    assertEquals("test", stack.peek()); 
  }

  @Test
  void peekEmptyStackTest() {
    assertEquals(null, stack.peek());
  }

  @Test
  void multipleOperationsTest() {
    stack.push("first");
    stack.push("second");
    assertEquals("second", stack.pop());
    assertEquals("first", stack.pop());
    assertEquals(null, stack.pop());
  }
  
  @Test
  void sizeUpTest() {
    for (int i = 0; i < 12; i++) {
      stack.push("test");
    }
    assertEquals(12, stack.size());
  }

  @Test
  void sizeDownTest() {
    for (int i = 0; i < 50; i++) {
      stack.push("test");
    }
    for (int i = 0; i < 30; i++) {
      stack.pop();
    }
    assertEquals(20, stack.size());
  }
}
