package pl.edu.wat.wcy.ita.gui;
import lombok.Data;
import pl.edu.wat.wcy.ita.Tree.TreeContainer;
import pl.edu.wat.wcy.ita.Tree.TreeType;

@Data
public class Entry {
    private final Integer lp;
    private final String operation;
    private final Integer numberOfNodes;
    private final Integer avlHeight;
    private final Integer bstHeight;
    private final Integer rbHeight;
    private final Integer splayHeight;
    private final Integer avlLeafs;
    private final Integer bstLeafs;
    private final Integer rbLeafs;
    private final Integer splayLeafs;
    private final Integer avlDepth;
    private final Integer bstDepth;
    private final Integer rbDepth;
    private final Integer splayDepth;
    public static String Names = "lp;Operacja;Liczba;Liczba węzłów;" +
            "Głębokość węzła AVL;Głębokość węzła BST;Głębokość węzła RB;Głębokość węzła Splay;" +
            "Wysokość drzewa AVL;Wysokość drzewa BST;Wysokość drzewa RB;Wysokość drzewa Splay;" +
            "Liczba liści drzewa AVL;Liczba liści drzewa BST;Liczba liści drzewa RB;Liczba liści drzewa Splay";

    Entry(TreeContainer treeContainer) {
        lp = treeContainer.getLP();
        operation = treeContainer.getComment();
        numberOfNodes = treeContainer.countNodes();
        avlHeight = treeContainer.getTree(TreeType.AVL).getTreeHeight();
        bstHeight = treeContainer.getTree(TreeType.BST).getTreeHeight();
        rbHeight = treeContainer.getTree(TreeType.RB).getTreeHeight();
        splayHeight = treeContainer.getTree(TreeType.SPLAY).getTreeHeight();
        avlLeafs = treeContainer.getTree(TreeType.AVL).getTreeLeafs();
        bstLeafs = treeContainer.getTree(TreeType.BST).getTreeLeafs();
        rbLeafs = treeContainer.getTree(TreeType.RB).getTreeLeafs();
        splayLeafs = treeContainer.getTree(TreeType.SPLAY).getTreeLeafs();
        avlDepth = treeContainer.getTree(TreeType.AVL).getNodeDepth();
        bstDepth = treeContainer.getTree(TreeType.BST).getNodeDepth();
        rbDepth = treeContainer.getTree(TreeType.RB).getNodeDepth();
        splayDepth = treeContainer.getTree(TreeType.SPLAY).getNodeDepth();
    }

    @Override
    public String toString() {
        String[] s = operation.split(" ");
        return lp +
                ";" + s[0] + ";" + s[1] +
                ";" + numberOfNodes +
                ";" + avlDepth +
                ";" + bstDepth +
                ";" + rbDepth +
                ";" + splayDepth +
                ";" + avlHeight +
                ";" + bstHeight +
                ";" + rbHeight +
                ";" + splayHeight +
                ";" + avlLeafs +
                ";" + bstLeafs +
                ";" + rbLeafs +
                ";" + splayLeafs;
    }
}
