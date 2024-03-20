package tree.rb;

import java.util.ArrayList;
import java.util.List;

public class RBTree {
  private final int RED = 0;
  private final int BLACK = 1;
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

  private void rotate(Node node) {
    if (hasLeft(node)) {
      if (hasRight(node.getLeft())) {
        doubleRightRotation(node);
        recolorAfterDoubleRightRotation(node);
      } else {
        rightRotation(node);
        recolorAfterRightRotation(node);
      }
    } else {
      if (hasLeft(node.getRight())) {
        doubleLeftRototion(node);
        recolorAfterDoubleLeftRotation(node);
      } else {
        leftRotation(node);
        recolorAfterLeftRotation(node);
      }
    }
  }

  private void recolorAfterRightRotation(Node node) {
    node.setColor(RED);
    node.getAbove().setColor(BLACK);
    node.getAbove().getLeft().setColor(RED);
  }

  private void recolorAfterLeftRotation(Node node) {
    node.setColor(RED);
    node.getAbove().setColor(BLACK);
    node.getAbove().getRight().setColor(RED);
  }

  private void recolorAfterDoubleRightRotation(Node node) {
    node.setColor(RED);
    node.getAbove().setColor(BLACK);
    node.getAbove().getRight().setColor(RED);
  }

  private void recolorAfterDoubleLeftRotation(Node node) {
    node.setColor(RED);
    node.getAbove().setColor(BLACK);
    node.getAbove().getLeft().setColor(RED);
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
      if (node.getAbove().isLeft(node)) {
        node.getAbove().setLeft(right);
      } else {
        node.getAbove().setRight(right);
      }
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
      if (node.getAbove().isLeft(node)) {
        node.getAbove().setLeft(left);
      } else {
        node.getAbove().setRight(left);
      }
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

  private void deleteWhenIsNotLeaf(Node node) {
    Node above = node.getAbove();
    Node successor = null;

    if (!hasRight(node)) {
      if (above.getLeft() == node) {
        above.setLeft(node.getLeft());
      } else {
        above.setRight(node.getLeft());
      }

      node.getLeft().setAbove(above);
      node.setAbove(null);

      checkIntegrityOfRulesAfterDeletion(node, node.getLeft());
      
      return;
    } else {
      successor = successor(node);
      Node aboveSuccessor = successor.getAbove();
      
      if (above.getLeft() == (node)) {
        above.setLeft(successor);
      } else if (above.getRight() == node) {
        above.setRight(successor);
      }

      successor.setAbove(above);

      if (hasLeft(node)) {
        successor.setLeft(node.getLeft());
        node.getLeft().setAbove(successor);
      }

      if (node != aboveSuccessor) {
        successor.setRight(node.getRight());
        node.getRight().setAbove(successor);

        if (aboveSuccessor.isLeft(successor)) {
          aboveSuccessor.setLeft(null);
        } else {
          aboveSuccessor.setRight(null);
        }
      } else {
        successor.setRight(null);
      }
    }

    checkIntegrityOfRulesAfterDeletion(node, successor);
  }

  private void deleteWhenIsLeaf(Node node) {
    Node above = node.getAbove();

    if (above.isLeft(node)) {
      above.setLeft(null);
    } else {
      above.setRight(null);
    }
  }

  private void checkIntegrityOfRulesAfterInsertion(Node insertedNode) {
    Node above = insertedNode.getAbove();
    if (above.getColor() == BLACK) {
      return;
    }

    recolorAboveAndNextToIt(above);
  }

  private void checkIntegrityOfRulesAfterDeletion(Node removedNode, Node successorNode) {
    // first situation
    if (removedNode.getColor() == RED && successorNode.getColor() == RED) {
      return;
    }
    
    // second situation
    if (removedNode.getColor() == BLACK && successorNode.getColor() == RED) {
      successorNode.setColor(BLACK);
      return;
    }

    Node above = successorNode.getAbove();

    // third situation
    if (removedNode.getColor() == BLACK && successorNode.getColor() == BLACK) {
      Node nextToNode = getNextToNode(successorNode);

      if ( // case 1
        nextToNode != null && nextToNode.getColor() == RED 
        && above != null && above.getColor() == BLACK
      ) {
        nextToNode.setColor(BLACK);
        leftRotation(above);
        successorNode.getAbove().setColor(RED);
        return;
      }

      if ( // case 2a
        nextToNode != null && nextToNode.getColor() == BLACK
        && above != null && above.getColor() == BLACK
        && isLeftBlack(nextToNode) && isRightBlack(nextToNode)
      ) {
        nextToNode.setColor(RED);
        return;
      }

      if ( // case 2b
        nextToNode != null && nextToNode.getColor() == BLACK
        && above != null && above.getColor() == RED
        && isLeftBlack(nextToNode) && isRightBlack(nextToNode)
      ) {
        nextToNode.setColor(RED);
        above.setColor(BLACK);
        return;
      }

      if ( // case 3
        nextToNode != null && nextToNode.getColor() == BLACK
        && isLeftRed(nextToNode) && isRightBlack(nextToNode)
      ) {
        int nextToNodeColor = nextToNode.getColor();
        nextToNode.setColor(nextToNode.getLeft().getColor());
        nextToNode.getLeft().setColor(nextToNodeColor);
        rightRotation(nextToNode);
        return;
      }

      if ( // case 4
        nextToNode != null && nextToNode.getColor() == BLACK
        && isRightRed(nextToNode)
      ) {
        nextToNode.setColor(above.getColor());
        nextToNode.getRight().setColor(BLACK);
        above.setColor(BLACK);
        leftRotation(above);
        return;
      }
    }
  
    if (removedNode.getColor() == RED && successorNode.getColor() == BLACK) {
      successorNode.setColor(RED);
      return;
    }
  }

  private void recolorAboveAndNextToIt(Node node) {
    Node above = node.getAbove();
    Node nextToAbove = getNextToAbove(node);
    
    if (nextToAbove != null && nextToAbove.getColor() == RED) {
      node.setColor(BLACK);
      nextToAbove.setColor(BLACK);

      if (isRoot(above)) {
        return;
      } 
      
      above.setColor(RED);

      recolorAboveAndNextToIt(above.getAbove());
    } else {
      rotate(above);
    }
  }

  private boolean isLeftBlack(Node node) {
    if (!hasLeft(node)) {
      return true;
    }

    return node.getLeft().getColor() == BLACK;
  }

  private boolean isRightBlack(Node node) {
    if (!hasRight(node)) {
      return true;
    }

    return node.getRight().getColor() == BLACK;
  }

  private boolean isLeftRed(Node node) {
    if (!hasLeft(node)) {
      return false;
    }

    return node.getLeft().getColor() == RED;
  }

  private boolean isRightRed(Node node) {
    if (!hasRight(node)) {
      return false;
    }

    return node.getRight().getColor() == RED;
  }

  private Node getNextToAbove(Node node) {
    Node above = node.getAbove();
    if (above.isLeft(node)) {
      return above.getRight();
    } else {
      return above.getLeft();
    }
  }

  private Node getNextToNode(Node node) {
    Node above = node.getAbove();
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

    //print();
  }

  public void createNodesForTestDeletionAtFirstSituation() {
    root = new Node(7, "", BLACK);
    Node node2red = new Node(2, "", RED);
    root.setLeft(node2red);
    node2red.setAbove(root);

    Node node1black = new Node(1, "", BLACK);
    Node node5black = new Node(5, "", BLACK);
    Node node4red = new Node(4, "", RED);

    node2red.setLeft(node1black);
    node1black.setAbove(node2red);
    node2red.setRight(node5black);
    node5black.setAbove(node2red);

    node5black.setLeft(node4red);
    node4red.setAbove(node5black);
    print();
    System.err.println("\n-------------------------------------------------");
  }

  public void createNodesForTestDeletionAtSecondSituation() {
    root = new Node(7, "", BLACK);
    Node node2black = new Node(2, "", BLACK);
    root.setLeft(node2black);
    node2black.setAbove(root);

    Node node1black = new Node(1, "", RED);
    Node node5black = new Node(5, "", RED);

    node2black.setLeft(node1black);
    node1black.setAbove(node2black);
    node2black.setRight(node5black);
    node5black.setAbove(node2black);

    print();
    System.err.println("\n-------------------------------------------------");
  }

  public void createNodesForTestDeletionAtThirdSituationCaseOne() {
    root = new Node(7, "", BLACK);

    Node node6black = new Node(6, "", BLACK);
    Node node5black = new Node(5, "", BLACK);
    Node node9red = new Node(9, "", RED);
    node6black.setLeft(node5black);
    node5black.setAbove(node6black);
    root.setLeft(node6black);
    root.setRight(node9red);
    node9red.setAbove(root);
    node6black.setAbove(root);

    Node node8black = new Node(8, "", BLACK);
    Node node10black = new Node(10, "", BLACK);

    node9red.setLeft(node8black);
    node8black.setAbove(node9red);
    node9red.setRight(node10black);
    node10black.setAbove(node9red);
    
    print();
    System.err.println("\n-------------------------------------------------");
  }

  public void createNodesForTestDeletionAtThirdSituationCaseTwoA() {
    root = new Node(7, "", BLACK);

    Node node6black = new Node(6, "", BLACK);
    Node node5black = new Node(5, "", BLACK);
    Node node9black = new Node(9, "", BLACK);
    node6black.setLeft(node5black);
    node5black.setAbove(node6black);
    root.setLeft(node6black);
    root.setRight(node9black);
    node9black.setAbove(root);
    node6black.setAbove(root);

    Node node8black = new Node(8, "", BLACK);
    Node node10black = new Node(10, "", BLACK);

    node9black.setLeft(node8black);
    node8black.setAbove(node9black);
    node9black.setRight(node10black);
    node10black.setAbove(node9black);
    
    print();
    System.err.println("\n-------------------------------------------------");
  }

  public void createNodesForTestDeletionAtThirdSituationCaseTwoB() {
    root = new Node(7, "", RED);

    Node node6black = new Node(6, "", BLACK);
    Node node5black = new Node(5, "", BLACK);
    Node node9black = new Node(9, "", BLACK);
    node6black.setLeft(node5black);
    node5black.setAbove(node6black);
    root.setLeft(node6black);
    root.setRight(node9black);
    node9black.setAbove(root);
    node6black.setAbove(root);

    Node node8black = new Node(8, "", BLACK);
    Node node10black = new Node(10, "", BLACK);

    node9black.setLeft(node8black);
    node8black.setAbove(node9black);
    node9black.setRight(node10black);
    node10black.setAbove(node9black);
    
    print();
    System.err.println("\n-------------------------------------------------");
  }

  public void createNodesForTestDeletionAtThirdSituationCaseThree() {
    root = new Node(7, "", BLACK);

    Node node6black = new Node(6, "", BLACK);
    Node node5black = new Node(5, "", BLACK);
    Node node9black = new Node(9, "", BLACK);
    node6black.setLeft(node5black);
    node5black.setAbove(node6black);
    root.setLeft(node6black);
    root.setRight(node9black);
    node9black.setAbove(root);
    node6black.setAbove(root);

    Node node8red = new Node(8, "", RED);
    Node node10black = new Node(10, "", BLACK);

    node9black.setLeft(node8red);
    node8red.setAbove(node9black);
    node9black.setRight(node10black);
    node10black.setAbove(node9black);
    
    print();
    System.err.println("\n-------------------------------------------------");
  }

  public void createNodesForTestDeletionAtThirdSituationCaseFour() {
    root = new Node(7, "", BLACK);

    Node node6black = new Node(6, "", BLACK);
    Node node5black = new Node(5, "", BLACK);
    Node node9black = new Node(9, "", BLACK);
    node6black.setLeft(node5black);
    node5black.setAbove(node6black);
    root.setLeft(node6black);
    root.setRight(node9black);
    node9black.setAbove(root);
    node6black.setAbove(root);

    Node node8red = new Node(8, "", RED);
    Node node10red = new Node(10, "", RED);

    node9black.setLeft(node8red);
    node8red.setAbove(node9black);
    node9black.setRight(node10red);
    node10red.setAbove(node9black);
    
    print();
    System.err.println("\n-------------------------------------------------");
  }

  public void createNodesForTestDeletionAtFourthSituation() {
    root = new Node(7, "", BLACK);

    Node node5black = new Node(5, "", BLACK);
    Node node9red = new Node(9, "", RED);
    node5black.setAbove(root);
    root.setLeft(node5black);
    root.setRight(node9red);
    node9red.setAbove(root);

    Node node8black = new Node(8, "", BLACK);
    Node node10black = new Node(10, "", BLACK);

    node9red.setLeft(node8black);
    node8black.setAbove(node9red);
    node9red.setRight(node10black);
    node10black.setAbove(node9red);
    
    print();
    System.err.println("\n-------------------------------------------------");
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