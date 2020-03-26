package pl.edu.wat.wcy.ita.Tree;
import lombok.Data;
import pl.edu.wat.wcy.ita.avlTree.AVLTree;
import pl.edu.wat.wcy.ita.binaryTree.BinaryTree;
import pl.edu.wat.wcy.ita.gui.SettingsSingleton;
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
    private static Integer counter = 1;
    private String comment;
    private SettingsSingleton settings = SettingsSingleton.getSingleton();

    public TreeContainer(OperationType operationType, int number) {
        trees = new HashMap<>();
        if (container.isEmpty()) {
            if (settings.isAVL()) trees.put(TreeType.AVL, new AVLTree());
            if (settings.isBST()) trees.put(TreeType.BST, new BinaryTree());
            if (settings.isRB()) trees.put(TreeType.RB, new RedBlackTree());
            if (settings.isSPLAY()) trees.put(TreeType.SPLAY, new SplayTree());
        } else {
            Map<TreeType, Tree> prev = container.getLast().trees;
            if (settings.isAVL()) trees.put(TreeType.AVL, new AVLTree((AVLTree) prev.get(TreeType.AVL)));
            if (settings.isBST()) trees.put(TreeType.BST, new BinaryTree((BinaryTree) prev.get(TreeType.BST)));
            if (settings.isRB()) trees.put(TreeType.RB, new RedBlackTree((RedBlackTree) prev.get(TreeType.RB)));
            if (settings.isSPLAY()) trees.put(TreeType.SPLAY, new SplayTree((SplayTree) prev.get(TreeType.SPLAY)));
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

        if (!container.isEmpty() && container.getLast().countNodes() > 300)
            container.removeLast();
        container.add(this);
    }

    public Tree getTree(TreeType treeType) {
        return trees.get(treeType);
    }

    public int countNodes () {
        Tree tree = null;
        if (settings.isAVL()) tree = trees.get(TreeType.AVL);
        else if (settings.isBST()) tree = trees.get(TreeType.BST);
        else tree = trees.get(TreeType.SPLAY);
        if (tree != null && tree.getRoot()!= null) return countNodes(tree.getRoot());
        else return 0;
    }

    private int countNodes (Node node) {
        int sum = 1;
        if (node.getLeft() != null) sum += countNodes(node.getLeft());
        if (node.getRight() != null) sum += countNodes(node.getRight());
        return sum;
    }

    public int getLP () {
        return counter++;
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

    public static TreeContainer getLast() {
        current = container.size() -1;
        return container.getLast();
    }
}
