import avl.*;

public class App {
    public static void main(String[] args) throws Exception {
        AVLTree tree = new AVLTree();

        tree.insert(10, "dez");
        tree.insert(5, "cinco");
        tree.insert(15, "quinze");
        tree.insert(2, "dois");
        tree.insert(8, "oito");
        tree.insert(22, "vinte e dois");

        tree.insert(25, "vinte e cinco");
        tree.delete(5);

        tree.print();   
    }
}
