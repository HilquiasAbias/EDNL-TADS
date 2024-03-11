package avl;

public class AVLTree {
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

  private boolean onlyHaveLeftNode(Node node) {
    return this.hasLeft(node) && !this.hasRight(node);
  }

  private boolean onlyHaveRightNode(Node node) {
    return !this.hasLeft(node) && this.hasRight(node);
  }

  private boolean isUnbalanced(Node node) {
    return node.getBalanceFactor() == 2 || node.getBalanceFactor() == -2;
  }

  private Node successor(Node node) {
    if (hasRight(node)) {
      Node successor = node.getRight();
  
      while (!this.hasLeft(successor)) {
        successor = successor.getLeft();
      }
  
      return successor;
    }

    return null; // TODO: in order
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

  private void updateBalanceFactorAfterDeletion(Node node) {}

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
      Node parent = node.getAbove();

      if (node.isLeaf()) {
        deleteWhenIsLeaf(node);
      } else if (onlyHaveLeftNode(node)) {
        deleteWhenOnlyHaveLeftNode(node);
      } else if (onlyHaveRightNode(node)) {
        deleteWhenOnlyHaveRightNode(node);
      } else {
        Node successor = successor(node);
        Node successorParent = successor.getAbove();

        if (successorParent.isLeft(successor)) {
          successorParent.setLeft(successor.getRight());
        } else {
          successorParent.setRight(successor.getRight());
        }

        node.setKey(successor.getKey());
        node.setValue(successor.getValue());
      }

      size--;

      return node;
    } catch (NodeNotFoundException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  private void deleteWhenIsLeaf(Node node) {
    Node parent = node.getAbove();

    if (parent.isLeft(node)) {
      parent.setLeft(null);
    } else {
      parent.setRight(null);
    }
  }

  private void deleteWhenOnlyHaveLeftNode(Node node) {
    Node parent = node.getAbove(); // TODO: get sucessor in order
    
    if (parent.isLeft(node)) {
      parent.setLeft(node.getRight());
    } else {
      parent.setRight(node.getRight());
    }
  }

  private void deleteWhenOnlyHaveRightNode(Node node) {
    Node parent = node.getAbove(); // TODO: get sucessor in order

    if (parent.isLeft(node)) {
      parent.setLeft(node.getLeft());
    } else {
      parent.setRight(node.getLeft());
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
