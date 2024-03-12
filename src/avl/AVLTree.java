package avl;

public class AVLTree {
  private final String LEFT = "left";
  private final String RIGHT = "right";
  private Node root;
  private int size;

  public AVLTree() {
    this.root = null;
    this.size = 0;
  }

  public AVLTree(int key, Object value) {
    this.root = new Node(key, value);
    this.size = 1;
  }

  public Node root() {
    return root;
  }

  public Boolean isRoot(Node node) {
    return node == root;
  }

  public int size() {
    return size;
  }

  public Node left(Node node) {
    return node.getLeft();
  }

  public Node right(Node node) {
    return node.getRight();
  }

  public Node above(Node node) {
    return node.getAbove();
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
    return node.getBalanceFactor() == 2 || node.getBalanceFactor() == -2;
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

  private void rotate(Node node) {
    if (node.getBalanceFactor() == 2) {
      if (node.getLeft().getBalanceFactor() >= 0) {
        rightRotation(node);
      } else {
        doubleRightRotation(node);
      }
    } else if (node.getBalanceFactor() == -2) {
      if (node.getRight().getBalanceFactor() <= 0) {
        leftRotation(node);
      } else {
        doubleLeftRototion(node);
      }
    }
  }

  private void leftRotation(Node node) {
    Node right = node.getRight();

    if (hasLeft(right)) {
      node.setRight(right.getLeft());
      right.getLeft().setAbove(node);
    } else {
      node.setRight(null);
    }
    
    if (isRoot(node)) {
      root = right;
      right.setAbove(null);
    } else {
      right.setAbove(node.getAbove());
      node.getAbove().setRight(right);
    }
    
    right.setLeft(node);
    node.setAbove(right);

    int newNodeBalanceFactor = node.getBalanceFactor() + 1 - Math.min(right.getBalanceFactor(), 0);
    int newRightBalanceFactor = right.getBalanceFactor() + 1 + Math.max(newNodeBalanceFactor, 0);

    node.setBalanceFactor(newNodeBalanceFactor);
    right.setBalanceFactor(newRightBalanceFactor);
  }

  private void rightRotation(Node node) {
    Node left = node.getLeft();

    if (hasRight(left)) {
      node.setLeft(left.getRight());
      left.getRight().setAbove(node);
    } else {
      node.setLeft(null);
    }
    
    if (isRoot(node)) {
      root = left;
      left.setAbove(null);
    } else {
      left.setAbove(node.getAbove());
      node.getAbove().setRight(left);
    }
    
    left.setRight(node);
    node.setAbove(left);

    int newNodeBalanceFactor = node.getBalanceFactor() - 1 - Math.max(left.getBalanceFactor(), 0);
    int newRightBalanceFactor = left.getBalanceFactor() - 1 + Math.min(newNodeBalanceFactor, 0);

    node.setBalanceFactor(newNodeBalanceFactor);
    left.setBalanceFactor(newRightBalanceFactor);
  }

  private void doubleLeftRototion(Node node) {
    rightRotation(node.getRight());
    leftRotation(node);
  }

  private void doubleRightRotation(Node node) {
    leftRotation(node.getLeft());
    rightRotation(node);
  }

  private void updateBalanceFactorAfterInsertion(Node node) {
    if (isRoot(node)) {
      return;
    }
    
    if (node.getAbove().isRight(node)) {
      node.getAbove().decreaseBalanceFactor();
    } else {
      node.getAbove().increaseBalanceFactor();
    }

    if (this.isUnbalanced(node.getAbove())) {
      this.rotate(node.getAbove());

      return;
    } else if (node.getAbove().getBalanceFactor() == 0 || isRoot(node.getAbove())) {
      return;
    } else {
      this.updateBalanceFactorAfterInsertion(node.getAbove());
    }
  }

  private void updateBalanceFactorAfterDeletion(Node node, String side) {
    if (isRoot(node)) {
      return;
    }

    if (side.equals(LEFT)) {
      node.decreaseBalanceFactor();
    } else if (side.equals(RIGHT)) {
      node.increaseBalanceFactor();
    }

    if (this.isUnbalanced(node.getAbove())) {
      this.rotate(node.getAbove());

      return;
    } else if (node.getBalanceFactor() != 0 || isRoot(node)) {
      return;
    } else {
      if (node.isLeft(node.getAbove())) {
        this.updateBalanceFactorAfterDeletion(node.getAbove(), LEFT);
      } else {
        this.updateBalanceFactorAfterDeletion(node.getAbove(), RIGHT);
      }
    }
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

  public Node insert(int key, Object value) {
    if (root == null) {
      root = new Node(key, value);
      size++;
      return root;
    }

    try {
      Node node = new Node(key, value);
      Node lastNode = findForInsert(key);

      if (key < lastNode.getKey()) {
        lastNode.setLeft(node);
      } else {
        lastNode.setRight(node);
      }

      node.setAbove(lastNode);
      this.updateBalanceFactorAfterInsertion(node);
      size++;

      return node;
    } catch (NodeAlreadyExistsException e) {
      System.out.println(e.getMessage());
      return null;
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

  public Node delete(int key) {
    try {
      Node node = findForDelete(key);

      if (node.isLeaf()) {
        deleteWhenIsLeaf(node);
      } else {
        deleteWhenIsNotLeaf(node);
      }

      size--;
      return node;
    } catch (NodeNotFoundException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  private void deleteWhenIsLeaf(Node node) {
    Node above = node.getAbove();

    if (above.isLeft(node)) {
      above.setLeft(null);
    } else {
      above.setRight(null);
    }
  }

  private void deleteWhenIsNotLeaf(Node node) {
    Node above = node.getAbove();

    if (!hasRight(node)) {
      String side = null;
      if (above.isLeft(node)) {
        above.setLeft(node.getLeft());
        side = LEFT;
      } else {
        above.setRight(node.getLeft());
        side = RIGHT;
      }
      node.getLeft().setAbove(above);

      if (node.getLeft().isLeaf()) {
        updateBalanceFactorAfterDeletion(above, side);
      } else {
        updateBalanceFactorAfterDeletion(node.getLeft(), side);
      }
      //updateBalanceFactorAfterDeletion(node.getLeft(), side);
      return;
    } else {
      Node sucessor = successor(node);
      Node aboveSucessor = sucessor.getAbove();
      
      if (above.getLeft() == (node)) {
        above.setLeft(sucessor);
      } else if (above.getRight() == node) {
        above.setRight(sucessor);
      }

      sucessor.setAbove(above);

      if (hasLeft(node)) {
        sucessor.setLeft(node.getLeft());
        node.getLeft().setAbove(sucessor);
      }

      if (node != aboveSucessor) {
        sucessor.setRight(node.getRight());
        node.getRight().setAbove(sucessor);

        if (aboveSucessor.isLeft(sucessor)) {
          aboveSucessor.setLeft(null);
          updateBalanceFactorAfterDeletion(aboveSucessor, LEFT);
        } else {
          aboveSucessor.setRight(null);
          updateBalanceFactorAfterDeletion(aboveSucessor, RIGHT);
        }
      } else {
        sucessor.setRight(null);
        updateBalanceFactorAfterDeletion(sucessor, RIGHT);
      }
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
}
