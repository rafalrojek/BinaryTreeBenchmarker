package pl.edu.wat.wcy.ita.gui;
import lombok.Data;
import pl.edu.wat.wcy.ita.Tree.*;

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
    private final Integer avlRotates;
    private final Integer rbRotates;
    private final Integer splayRotates;

    private SettingsSingleton settings = SettingsSingleton.getSingleton();

    Entry(TreeContainer treeContainer) {
        lp = treeContainer.getLP();
        operation = treeContainer.getComment();
        numberOfNodes = treeContainer.countNodes();
        if (settings.isAVL() && settings.isHeight()) avlHeight = treeContainer.getTree(TreeType.AVL).getTreeHeight();
        else avlHeight = null;
        if (settings.isBST() && settings.isHeight()) bstHeight = treeContainer.getTree(TreeType.BST).getTreeHeight();
        else bstHeight = null;
        if (settings.isRB() && settings.isHeight()) rbHeight = treeContainer.getTree(TreeType.RB).getTreeHeight();
        else rbHeight = null;
        if (settings.isSPLAY() && settings.isHeight()) splayHeight = treeContainer.getTree(TreeType.SPLAY).getTreeHeight();
        else splayHeight = null;


        if (settings.isAVL() && settings.isLeafs()) avlLeafs = treeContainer.getTree(TreeType.AVL).getTreeLeafs();
        else avlLeafs = null;
        if (settings.isBST() && settings.isLeafs()) bstLeafs = treeContainer.getTree(TreeType.BST).getTreeLeafs();
        else bstLeafs = null;
        if (settings.isRB() && settings.isLeafs()) rbLeafs = treeContainer.getTree(TreeType.RB).getTreeLeafs();
        else rbLeafs = null;
        if (settings.isSPLAY() && settings.isLeafs()) splayLeafs = treeContainer.getTree(TreeType.SPLAY).getTreeLeafs();
        else splayLeafs = null;

        if (settings.isAVL() && settings.isDepth()) avlDepth = treeContainer.getTree(TreeType.AVL).getNodeDepth();
        else avlDepth = null;
        if (settings.isBST() && settings.isDepth()) bstDepth = treeContainer.getTree(TreeType.BST).getNodeDepth();
        else bstDepth = null;
        if (settings.isRB() && settings.isDepth()) rbDepth = treeContainer.getTree(TreeType.RB).getNodeDepth();
        else rbDepth = null;
        if (settings.isSPLAY() && settings.isDepth()) splayDepth = treeContainer.getTree(TreeType.SPLAY).getNodeDepth();
        else splayDepth = null;

        if (settings.isAVL() && settings.isRotates()) avlRotates = treeContainer.getTree(TreeType.AVL).getRotates();
        else avlRotates = null;
        if (settings.isRB() && settings.isRotates())rbRotates = treeContainer.getTree(TreeType.RB).getRotates();
        else rbRotates = null;
        if (settings.isSPLAY() && settings.isRotates()) splayRotates = treeContainer.getTree(TreeType.SPLAY).getRotates();
        else splayRotates = null;
    }

    public static String getNames () {
        SettingsSingleton settings = SettingsSingleton.getSingleton();
        StringBuilder stringBuilder = new StringBuilder("lp;Operacja;Liczba;Liczba węzłów;");
        if (settings.isBST() && settings.isHeight()) stringBuilder.append("Głębokość węzła BST;");
        if (settings.isAVL() && settings.isHeight()) stringBuilder.append("Głębokość węzła AVL;");
        if (settings.isRB() && settings.isHeight()) stringBuilder.append("Głębokość węzła RB;");
        if (settings.isSPLAY() && settings.isHeight()) stringBuilder.append("Głębokość węzła Splay;");
        if (settings.isBST() && settings.isLeafs()) stringBuilder.append("Wysokość drzewa BST;");
        if (settings.isAVL() && settings.isLeafs()) stringBuilder.append("Wysokość drzewa AVL;");
        if (settings.isRB() && settings.isLeafs()) stringBuilder.append("Wysokość drzewa RB;");
        if (settings.isSPLAY() && settings.isLeafs()) stringBuilder.append("Wysokość drzewa Splay;");
        if (settings.isBST() && settings.isDepth()) stringBuilder.append("Liczba liści drzewa BST;");
        if (settings.isAVL() && settings.isDepth()) stringBuilder.append("Liczba liści drzewa AVL;");
        if (settings.isRB() && settings.isDepth()) stringBuilder.append("Liczba liści drzewa RB;");
        if (settings.isSPLAY() && settings.isDepth()) stringBuilder.append("Liczba liści drzewa Splay;");
        if (settings.isAVL() && settings.isRotates()) stringBuilder.append("Liczba rotacji drzewa AVL;");
        if (settings.isRB() && settings.isRotates()) stringBuilder.append("Liczba rotacji drzewa RB;");
        if (settings.isSPLAY() && settings.isRotates()) stringBuilder.append("Liczba rotacji drzewa Splay;");
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        String[] s = operation.split(" ");
        StringBuilder stringBuilder = new StringBuilder(lp + ";" + s[0] + ";" + s[1] +  ";" + numberOfNodes);
        if (settings.isBST() && settings.isHeight()) stringBuilder.append(";").append(bstDepth);
        if (settings.isAVL() && settings.isHeight()) stringBuilder.append(";").append(avlDepth);
        if (settings.isRB() && settings.isHeight()) stringBuilder.append(";").append(rbDepth);
        if (settings.isSPLAY() && settings.isHeight()) stringBuilder.append(";").append(splayDepth);
        if (settings.isBST() && settings.isLeafs()) stringBuilder.append(";").append(bstHeight);
        if (settings.isAVL() && settings.isLeafs()) stringBuilder.append(";").append(avlHeight);
        if (settings.isRB() && settings.isLeafs()) stringBuilder.append(";").append(rbHeight);
        if (settings.isSPLAY() && settings.isLeafs()) stringBuilder.append(";").append(splayHeight);
        if (settings.isBST() && settings.isDepth()) stringBuilder.append(";").append(bstLeafs);
        if (settings.isAVL() && settings.isDepth()) stringBuilder.append(";").append(avlLeafs);
        if (settings.isRB() && settings.isDepth()) stringBuilder.append(";").append(rbLeafs);
        if (settings.isSPLAY() && settings.isDepth()) stringBuilder.append(";").append(splayLeafs);
        if (settings.isAVL() && settings.isRotates()) stringBuilder.append(";").append(avlRotates);
        if (settings.isRB() && settings.isRotates()) stringBuilder.append(";").append(rbRotates);
        if (settings.isSPLAY() && settings.isRotates()) stringBuilder.append(";").append(splayRotates);
        return stringBuilder.toString();
    }
}
