import avl.*;

public class App {
    public static void main(String[] args) throws Exception {
        AVLTree tree = new AVLTree();
        tree.insert(10, "10");
        tree.insert(5, "5");
        tree.insert(15, "15");
        tree.insert(3, "3");
        tree.insert(7, "7");
        // tree.insert(12, "12");
        // tree.insert(17, "17");
        // tree.insert(2, "2");
        // tree.insert(4, "4");
        // tree.insert(6, "6");
        // tree.insert(9, "9");
        // tree.insert(11, "11");
        // tree.insert(13, "13");

        // System.out.println("Procurando por 10, 5, 15, 3 e 7: ");
        System.out.println(tree.search(10));
        System.out.println(tree.search(5));
        System.out.println(tree.search(15));
        System.out.println(tree.search(3));
        System.out.println(tree.search(7));

        // System.out.println("Removendo 10, 5 e 15: ");
        // System.out.println(tree.delete(10).getKey());
        // System.out.println(tree.delete(5).getKey());
        // System.out.println(tree.delete(15).getKey());
    }
}
