package tree.rb;

public class RBTree {
  private final String LEFT = "left";
  private final String RIGHT = "right";
  private final int RED = 0;
  private final int BLACK = 1;
  private final int DOUBLE_BLACK = 2;
  private final Node GLOBAL_BLACK_LEAF = new Node(0, null, BLACK);
  private Node root;
  private int size;

  public RBTree() {
    this.root = null;
    this.size = 0;
  }

  public Node root() {
    return this.root;
  }

  public Boolean isRoot(Node node) {
    return node == root;
  }

  public int size() {
    return size;
  }

  public Boolean hasLeft(Node node) {
    return node.getLeft() != null;
  }

  public Boolean hasRight(Node node) {
    return node.getRight() != null;
  }

  public Boolean hasAbove(Node node) {
    return node.getAbove() != null;
  }

  private boolean isUnbalanced(Node node) {
    return node.getBalanceFactor() == 3 || node.getBalanceFactor() == -3;
  }

  private Node successor(Node node) {
    if (hasRight(node)) {
      Node successor = node.getRight();

      while (this.hasLeft(successor)) {
        successor = successor.getLeft();
      }

      return successor;
    }

    return node;
  }

  private Node find(int key, Node current) {
    if (key == current.getKey()) {
      return current;
    }

    else if (hasLeft(current) && key < current.getKey()) {
      return find(key, current.getLeft());
    }

    else if (hasRight(current) && key > current.getKey()) {
      return find(key, current.getRight());
    }

    return current;
  }

  public Node search(int key) {
    try {
      return findForSearch(key);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  private Node findForSearch(int key) throws NodeNotFoundException {
    if (key == root.getKey()) {
      return root;
    }

    Node node = find(key, root);

    if (node.getKey() != key) {
      throw new NodeNotFoundException(Integer.toString(key));
    } else {
      return node;
    }
  }


  private Node findForInsert(int key) throws NodeAlreadyExistsException {
    if (key == root.getKey()) {
      return root;
    }

    Node node = find(key, root);

    if (node.getKey() == key) {
      throw new NodeAlreadyExistsException(Integer.toString(key));
    } else {
      return node;
    }
  }

  private Node findForDelete(int key) throws NodeNotFoundException {
    if (key == root.getKey()) {
      return root;
    }

    Node node = find(key, root);

    if (node.getKey() != key) {
      throw new NodeNotFoundException(Integer.toString(key));
    } else {
      return node;
    }
  }

  public Node insert(int key, Object value) {
    if (root == null) {
      root = new Node(key, value, BLACK, GLOBAL_BLACK_LEAF);
      size++;
      return root;
    }

    try {
      Node lastNode = findForInsert(key);
      Node node = new Node(key, value, RED, GLOBAL_BLACK_LEAF);

      if (key < lastNode.getKey()) {
        lastNode.setLeft(node);
      } else {
        lastNode.setRight(node);
      }

      node.setAbove(lastNode);
      size++;
      checkIntegrityOfRulesAfterInsertion(node);
      return node;
    } catch (NodeAlreadyExistsException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  private void checkIntegrityOfRulesAfterInsertion(Node insertedNode) {
    Node above = insertedNode.getAbove();
    if (above.getColor() == BLACK) {
      return;
    }

    Node nextToAbove = getNextToAbove(insertedNode);
    if (nextToAbove.getColor() == RED) {
      above.setColor(BLACK);
      nextToAbove.setColor(BLACK);
      if (isRoot(above.getAbove())) {
        return;
      } 
      
      above.getAbove().setColor(RED);

      // verificar outras casos
    }
  }

  private Node getNextToAbove(Node node) {
    Node above = node.getAbove();
    if (above.isLeft(node)) {
      return above.getRight();
    } else {
      return above.getLeft();
    }
  }
}
