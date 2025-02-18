package shortestpath;

public class Cell {
    private int type; // 0 - caminho vazio, 1 - parede, 2 - ponto inicial, 3 - saída
    private Cell up;
    private Cell down;
    private Cell left;
    private Cell right;
    private Cell previous;
    private int cost;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Cell getUp() {
        return up;
    }

    public void setUp(Cell up) {
        this.up = up;
    }

    public Cell getDown() {
        return down;
    }

    public void setDown(Cell down) {
        this.down = down;
    }

    public Cell getLeft() {
        return left;
    }

    public void setLeft(Cell left) {
        this.left = left;
    }

    public Cell getRight() {
        return right;
    }

    public void setRight(Cell right) {
        this.right = right;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
