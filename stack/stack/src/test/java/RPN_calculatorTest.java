import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import stack.calculator.RPN;

public class RPN_calculatorTest {
  private RPN calculator;

  @BeforeEach
  void setUp() {
    calculator = new RPN();
  }

  @Test
  void addTest() {
    calculator.push("2");
    calculator.push("3");
    calculator.push("+");
    assertEquals("5", calculator.peek());
  }

  @Test
  void addEmptyStackTest() {
    calculator.push("2");
    calculator.push("+");
    assertEquals("2", calculator.peek());
  }

  @Test
  void subtractTest() {
    calculator.push("5");
    calculator.push("3");
    calculator.push("-");
    assertEquals("2", calculator.peek());
  }

  @Test
  void subtractEmptyStackTest() {
    calculator.push("2");
    calculator.push("-");
    assertEquals("2", calculator.peek());
  }

  @Test
  void multiplyTest() {
    calculator.push("2");
    calculator.push("3");
    calculator.push("*");
    assertEquals("6", calculator.peek());
  }

  @Test
  void multiplyEmptyStackTest() {
    calculator.push("2");
    calculator.push("*");
    assertEquals("2", calculator.peek());
  }

  @Test
  void divideTest() {
    calculator.push("6");
    calculator.push("2");
    calculator.push("/");
    assertEquals("3", calculator.peek());
  }

  @Test
  void divideEmptyStackTest() {
    calculator.push("2");
    calculator.push("/");
    assertEquals("2", calculator.peek());
  }

  @Test
  void divideByZeroTest() {
    calculator.push("5");
    calculator.push("0");
    calculator.push("/");
    assertEquals("0", calculator.peek());
  }

  @Test 
  void invalidNumberFormatTest() {
    calculator.push("5");
    calculator.push("a");
    calculator.push("+");
    calculator.push("-");
    calculator.push("*");
    calculator.push("/");
    assertEquals("5", calculator.peek());
  }

  @Test 
  void popTest() {
    calculator.push("5");
    calculator.push("3");
    calculator.push("+");
    assertEquals("8", calculator.pop());
  }

  @Test
  void multipleOperationsTest() {
    calculator.push("1");
    calculator.push("2");
    calculator.push("3");
    calculator.push("+");
    calculator.push("*");
    assertEquals("5", calculator.peek());
    calculator.push("1");
    calculator.push("-");
    assertEquals("4", calculator.peek());
    calculator.push("2");
    calculator.push("/");
    assertEquals("2", calculator.peek());
  }
}