package pl.edu.wat.wcy.ita.Tree;
import lombok.Data;
import pl.edu.wat.wcy.ita.avlTree.AVLTree;
import pl.edu.wat.wcy.ita.binaryTree.BinaryNode;
import pl.edu.wat.wcy.ita.binaryTree.BinaryTree;
import pl.edu.wat.wcy.ita.redBlackTree.RedBlackTree;
import pl.edu.wat.wcy.ita.splayTree.SplayTree;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Data
public class TreeContainer {
    private static LinkedList<TreeContainer> container = new LinkedList<>();
    private final Map<TreeType,Tree> trees;
    private static Integer current = 0;
    private String comment;

    public TreeContainer(OperationType operationType, int number) {
        trees = new HashMap<>();
        if (container.isEmpty()) {
            trees.put(TreeType.AVL, new AVLTree());
            trees.put(TreeType.BST, new BinaryTree());
            trees.put(TreeType.RB, new RedBlackTree());
            trees.put(TreeType.SPLAY, new SplayTree());
        } else {
            Map<TreeType, Tree> prev = container.getLast().trees;
            trees.put(TreeType.AVL, new AVLTree((AVLTree) prev.get(TreeType.AVL)));
            trees.put(TreeType.BST, new BinaryTree((BinaryTree) prev.get(TreeType.BST)));
            trees.put(TreeType.RB, new RedBlackTree((RedBlackTree) prev.get(TreeType.RB)));
            trees.put(TreeType.SPLAY, new SplayTree((SplayTree) prev.get(TreeType.SPLAY)));
        }
        if(operationType == OperationType.ADD) {
            comment = "Dodanie " + number;
            trees.forEach(((treeType, tree) -> tree.addNode(number)));
        } else if (operationType == OperationType.REMOVE) {
            comment = "Usunięcie " + number;
            trees.forEach(((treeType, tree) -> tree.removeNode(number)));
        }else if (operationType == OperationType.FIND) {
            comment = "Wyszukanie " + number;
            trees.forEach(((treeType, tree) -> tree.findNode(number)));
        }
        container.add(this);
        current = container.size()-1;
    }

    public Tree getTree(TreeType treeType) {
        return trees.get(treeType);
    }

    public int countNodes () {
        BinaryTree binaryTree = (BinaryTree) trees.get(TreeType.BST);
        if (binaryTree.getRoot()!= null) return countNodes(binaryTree.getRoot());
        else return 0;
    }

    private int countNodes (BinaryNode node) {
        int sum = 1;
        if (node.getLeft() != null) sum += countNodes(node.getLeft());
        if (node.getRight() != null) sum += countNodes(node.getRight());
        return sum;
    }

    public int getLP () {
        return container.size();
    }

    public static TreeContainer getPrev () {
        if (current > 0) {
            current--;
            while (current > 0 && container.get(current).countNodes() > 300) current--;
            return container.get(current);
        }
        else return null;
    }

    public static TreeContainer getNext () {
        if (current < container.size()-1) {
            current++;
            while (current < container.size()-1 && container.get(current).countNodes() > 300) current++;
            return container.get(current);
        }
        else return null;
    }
}
