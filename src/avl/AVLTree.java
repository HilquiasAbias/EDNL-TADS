package avl;

import java.util.ArrayList;
import java.util.List;

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
    if (side.equals(LEFT)) {
      node.decreaseBalanceFactor();
    } else if (side.equals(RIGHT)) {
      node.increaseBalanceFactor();
    }

    if (node.getBalanceFactor() != 0 || isRoot(node)) {
      return;
    } else if (this.isUnbalanced(node.getAbove())) {
      this.rotate(node.getAbove());

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
      if (above.getLeft() == node) {
        System.out.println("left");
        above.setLeft(node.getLeft());
        side = LEFT;
      } else {
        System.out.println("right");
        above.setRight(node.getLeft());
        side = RIGHT;
      }
      node.getLeft().setAbove(above);
      updateBalanceFactorAfterDeletion(above, side);
      node.setAbove(null);
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

  public void print() {
    List<List<String>> lines = new ArrayList<List<String>>();

    List<Node> level = new ArrayList<Node>();
    List<Node> next = new ArrayList<Node>();

    level.add(root);
    int nn = 1;

    int widest = 0;

    while (nn != 0) {
      List<String> line = new ArrayList<String>();

      nn = 0;

      for (Node n : level) {
        if (n == null) {
          line.add(null);

          next.add(null);
          next.add(null);
        } else {
          String aa = String.valueOf(n.getKey()) + "(" + n.getBalanceFactor() + ")";
          line.add(aa);
          if (aa.length() > widest) widest = aa.length();

          next.add(n.getLeft());
          next.add(n.getRight());

          if (n.getLeft() != null) nn++;
          if (n.getRight() != null) nn++;
        }
      }

      if (widest % 2 == 1) widest++;

      lines.add(line);

      List<Node> tmp = level;
      level = next;
      next = tmp;
      next.clear();
    }

    int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
    for (int i = 0; i < lines.size(); i++) {
      List<String> line = lines.get(i);
      int hpw = (int) Math.floor(perpiece / 2f) - 1;

      if (i > 0) {
        for (int j = 0; j < line.size(); j++) {

          // split node
          char c = ' ';
          if (j % 2 == 1) {
            if (line.get(j - 1) != null) {
              c = (line.get(j) != null) ? '┴' : '┘';
            } else {
              if (j < line.size() && line.get(j) != null) c = '└';
            }
          }
          System.out.print(c);

          // lines and spaces
          if (line.get(j) == null) {
            for (int k = 0; k < perpiece - 1; k++) {
              System.out.print(" ");
            }
          } else {
            for (int k = 0; k < hpw; k++) {
                System.out.print(j % 2 == 0 ? " " : "─");
            }
            System.out.print(j % 2 == 0 ? "┌" : "┐");
            for (int k = 0; k < hpw; k++) {
              System.out.print(j % 2 == 0 ? "─" : " ");
            }
          }
        }
        System.out.println();
      }

      // print line of numbers
      for (int j = 0; j < line.size(); j++) {
        String f = line.get(j);
        if (f == null) f = "";
        int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
        int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

        // a number
        for (int k = 0; k < gap1; k++) {
          System.out.print(" ");
        }
        System.out.print(f);
        for (int k = 0; k < gap2; k++) {
          System.out.print(" ");
        }
      }
      System.out.println();

      perpiece /= 2;
    }
  }
}
