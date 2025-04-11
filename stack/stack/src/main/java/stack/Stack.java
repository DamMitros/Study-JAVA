package stack;

public class Stack {
  private String[] stack;
  private int size;
  private final int defaultSize = 10;
  private final int defaultChange = 10;

  public Stack() {
    stack = new String[defaultSize];
    size = 0;
  }
  
  public void push(String element) {
    if (size == stack.length) {
      resize(stack.length + defaultChange);
    }
    stack[size] = element;
    size++;
  }
  
  public String pop() {
    if (size == 0) {
      System.out.println("Stack is empty");
      return null;
    }
    String element = stack[size - 1];
    // stack[size-1] = null;
    size--;
    // if (size>defaultSize && (double)size/stack.length < 0.4){
    //   resize(stack.length/2);
    // }
    return element;
  }

  public String peek() {
    if (size == 0) {
      System.out.println("Stack is empty");
      return null;
    }
    return stack[size - 1];
  }

  public int size() {
    return size;
  }
  
  private void resize(int newSize) {
    String[] newStack = new String[newSize];
    for (int i = 0; i < size; i++) {
      newStack[i] = stack[i];
    }
    stack = newStack;
  }
}