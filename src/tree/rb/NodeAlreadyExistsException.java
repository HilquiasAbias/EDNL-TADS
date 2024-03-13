package tree.rb;

public class NodeAlreadyExistsException extends Exception {
  private static String message = "Node already exists: ";

  public NodeAlreadyExistsException(String key) {
    super(message + key);
  }
}