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

        // caso 1
        // tree.insert(10, "dez");
        // tree.insert(15, "quinze");
        // System.out.println(tree.search(10));
        // System.out.println(tree.search(15));

        // caso 2
        // tree.createNodesForTest();
        // tree.insert(96, "");
        // System.out.println(tree.search(30));
        // System.out.println(tree.search(13));
        // System.out.println(tree.search(53));
        // System.out.println(tree.search(83));
        // System.out.println(tree.search(63));
        // System.out.println(tree.search(93));

        // caso 3a (esquerda simples)
        // tree.insert(10, "dez");
        // tree.insert(15, "quinze");
        // tree.insert(22, "vinte e dois");
        // System.out.println(tree.search(10));
        // System.out.println(tree.search(15));
        // System.out.println(tree.search(22));

        // caso 3b (direita simples)
        // tree.insert(10, "dez");
        // tree.insert(5, "cinco");
        // tree.insert(3, "três");
        // System.out.println(tree.search(10));
        // System.out.println(tree.search(5));
        // System.out.println(tree.search(3));

        // caso 3c (esquerda dupla)
        // tree.insert(10, "dez");
        // tree.insert(12, "doze");
        // tree.insert(15, "quinze");

        // System.out.println(tree.search(10));
        // System.out.println(tree.search(12));
        // System.out.println(tree.search(15));

        // caso 3d (direita dupla)
        tree.insert(10, "dez");
        tree.insert(8, "oito");
        tree.insert(9, "nove");

        System.out.println(tree.search(10));
        System.out.println(tree.search(8));
        System.out.println(tree.search(9));
    }
}