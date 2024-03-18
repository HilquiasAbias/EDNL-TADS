package tree.rb;

public class Node {
  private Node left, right, above;
  private int key, color;
  private Object value;

  public Node(int key, Object value, Node leaf) {
    this.above = null;
    this.left = leaf;
    this.right = leaf;
    this.value = value;
    this.key = key;
    this.color = 0;
  }

  // public Node(int key, Object value, int color, Node leaf) {
  //   this.above = null;
  //   this.left = leaf;
  //   this.right = leaf;
  //   this.value = value;
  //   this.key = key;
  //   this.balanceFactor = 0;
  //   this.color = color;
  // }

  public Node(int key, Object value, int color) {
    this.above = null;
    this.left = null;
    this.right = null;
    this.value = value;
    this.key = key;
    this.color = color;
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

  public Object getValue() {
    return this.value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public int getColor() {
    return this.color;
  }

  public Boolean isLeft(Node node) {
    return this.left == node;
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
    String color = this.color == 0 ? "RED" : "BLACK";
    return "Node {\n key=" + key +
        ",\n color=" + color +
        ",\n value=" + value +
        ",\n left=" + left +
        ",\n right=" + right +
        ",\n above=" + above +
        "\n}";
  }
}
