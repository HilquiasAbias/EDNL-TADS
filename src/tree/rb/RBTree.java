package tree.rb;

import java.util.ArrayList;
import java.util.List;

public class RBTree {
  private final String LEFT = "left";
  private final String RIGHT = "right";
  private final int RED = 0;
  private final int BLACK = 1;
  private final int DOUBLE_BLACK = 2;
  //private final Node GLOBAL_BLACK_LEAF = new Node(0, null, BLACK);
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

  private void rotate(Node node) { // node = t
    if (hasLeft(node)) {
      if (hasRight(node.getLeft())) {
        doubleRightRotation(node);
      } else {
        rightRotation(node);
      }
    } else {
      if (hasLeft(node.getRight())) {
        doubleLeftRototion(node);
      } else {
        leftRotation(node);
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
  }

  private void doubleLeftRototion(Node node) {
    rightRotation(node.getRight());
    leftRotation(node);
  }

  private void doubleRightRotation(Node node) {
    leftRotation(node.getLeft());
    rightRotation(node);
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
      root = new Node(key, value, BLACK);
      size++;
      return root;
    }

    try {
      Node lastNode = findForInsert(key);
      Node node = new Node(key, value, RED);

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
    System.out.println("above2: " + above.getColor());
    System.out.println(BLACK);
    if (above.getColor() == BLACK) {
      System.out.println("inserção caso 1");
      return;
    }

    recolorAboveAndNextToIt(above);
  }

  private void recolorAboveAndNextToIt(Node node) {
    System.out.println("recolorAboveAndNextToIt");
    System.out.println(node);
    Node above = node.getAbove();
    Node nextToAbove = getNextToAbove(node);

    System.out.println("above: " + above);
    System.out.println("nextToAbove: " + nextToAbove);
    
    if (nextToAbove != null && nextToAbove.getColor() == RED) {
      System.out.println("inserção caso 2");
      node.setColor(BLACK);
      nextToAbove.setColor(BLACK);

      if (isRoot(above)) {
        return;
      } 
      
      above.setColor(RED);

      recolorAboveAndNextToIt(above.getAbove());
    } else {
      System.out.println("inserção caso 3");
      //System.out.println("rotate: " + above);
      rotate(above);
    }
  }

  private Node getNextToAbove(Node node) {
    Node above = node.getAbove();
    System.out.println("above: " + above);
    if (above.isLeft(node)) {
      return above.getRight();
    } else {
      return above.getLeft();
    }
  }

  public void createNodesForTest() {
    root = new Node(30, "", BLACK);
    Node node13red = new Node(13, "", RED);
    Node node53red = new Node(53, "", RED);
    root.setLeft(node13red);
    root.setRight(node53red);
    node13red.setAbove(root);
    node53red.setAbove(root);

    Node node8black = new Node(8, "", BLACK);
    Node node23black = new Node(23, "", BLACK);
    Node node43black = new Node(43, "", BLACK);
    Node node83black = new Node(83, "", BLACK);

    node13red.setLeft(node8black);
    node13red.setRight(node23black);
    node8black.setAbove(node13red);
    node23black.setAbove(node13red);

    node53red.setLeft(node43black);
    node53red.setRight(node83black);

    Node node63red = new Node(63, "", RED);
    Node node93red = new Node(93, "", RED);

    node83black.setLeft(node63red);
    node83black.setRight(node93red);

    node43black.setAbove(node53red);
    node83black.setAbove(node53red);

    node63red.setAbove(node83black);
    node93red.setAbove(node83black);

    print();
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
          String aa = String.valueOf(n.getKey()) + "(" + (n.getColor() == 0 ? "RED" : "BLACK" ) + ")";
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