import tree.avl.*;
import tree.rb.*;

public class App {
    public static void main(String[] args) throws Exception {
        // AVLTree tree = new AVLTree();

        // tree.insert(10, "dez");
        // tree.insert(5, "cinco");
        // tree.insert(15, "quinze");
        // tree.insert(2, "dois");
        // tree.insert(8, "oito");
        // tree.insert(22, "vinte e dois");

        // tree.insert(25, "vinte e cinco");
        // tree.delete(5);

        // tree.print(); 
        
        RBTree tree = new RBTree();

        tree.insert(10, "dez");
        tree.insert(5, "cinco");
        // tree.insert(15, "quinze");
        // tree.insert(2, "dois");
        tree.insert(8, "oito");
        // tree.insert(22, "vinte e dois");
        // tree.insert(9, "nove");

        //tree.print();

        System.out.println(tree.search(10));
        System.out.println(tree.search(5));
        System.out.println(tree.search(8));
    }
}
