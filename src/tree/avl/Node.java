package tree.avl;

public class Node {
  private Node left, right, above;
  private int key, balanceFactor;
  private Object value;

  public Node(int key, Object value) {
    this.above = null;
    this.left = null;
    this.right = null;
    this.value = value;
    this.key = key;
    this.balanceFactor = 0;
  }

  public Node getAbove() {
    return this.above;
  }

  public void setAbove(Node above) {
    this.above = above;
  }

  public Node getLeft() {
    return this.left;
  }

  public void setLeft(Node left) {
    this.left = left;
  }

  public Node getRight() {
    return this.right;
  }

  public void setRight(Node right) {
    this.right = right;
  }

  public int getKey() {
    return this.key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public int getBalanceFactor() {
    return this.balanceFactor;
  }

  public void setBalanceFactor(int balanceFactor) {
    this.balanceFactor = balanceFactor;
  }

  public void increaseBalanceFactor() {
    this.balanceFactor++;
  }

  public void decreaseBalanceFactor() {
    this.balanceFactor--;
  }

  public Object getValue() {
    return this.value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public Boolean isLeft(Node node) {
    return node.left == node;
  }

  public Boolean isRight(Node node) {
    return this.right == node;
  }

  public Boolean isLeaf() {
    return this.left == null && this.right == null;
  }

  @Override
  public String toString() {
    Integer left = this.left == null ? 0 : this.left.getKey();
    Integer right = this.right == null ? 0 : this.right.getKey();
    Integer above = this.above == null ? 0 : this.above.getKey();
    return "Node {\n key=" + key +
        ",\n balanceFactor=" + balanceFactor +
        ",\n value=" + value +
        ",\n left=" + left +
        ",\n right=" + right +
        ",\n above=" + above +
        "\n}";
  }
}
