package tree.rb;

public class NodeNotFoundException extends Exception {
  private static String message = "Node not found: ";

  public NodeNotFoundException(String key) {
    super(message + key);
  }
}
