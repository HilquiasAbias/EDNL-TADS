import avl.*;

public class App {
    public static void main(String[] args) throws Exception {
        AVLTree tree = new AVLTree();
        tree.insert(6, "6");
        tree.insert(8, "8");
        tree.insert(9, "9");

        System.out.println(tree.search(6));
        System.out.println(tree.search(8));
        System.out.println(tree.search(9));
    }
}
