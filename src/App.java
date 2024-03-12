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

        // System.out.println(tree.search(10));
        // System.out.println(tree.search(5));
        // System.out.println(tree.search(15));
        // System.out.println(tree.search(2));
        // System.out.println(tree.search(8));
        // System.out.println(tree.search(22));

        tree.insert(25, "vinte e cinco");
        //System.out.println(tree.search(25));

        tree.delete(5);
        tree.delete(8);
        //System.out.println(tree.search(8));
        //System.out.println(tree.search(5));

        System.out.println(tree.search(10));
        //System.out.println(tree.search(8));
        System.out.println(tree.search(22));
        System.out.println(tree.search(2));
        System.out.println(tree.search(15));
        System.out.println(tree.search(25));

        System.out.println("--------------------------------------------");
        System.out.println(tree.search(5));

    }
}
