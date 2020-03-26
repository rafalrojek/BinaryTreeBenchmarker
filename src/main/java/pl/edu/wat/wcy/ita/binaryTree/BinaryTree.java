package pl.edu.wat.wcy.ita.binaryTree;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wat.wcy.ita.Tree.Tree;

@Data
@NoArgsConstructor
public class BinaryTree implements Tree<BinaryNode> {
    private BinaryNode root = null;
    private Integer nodeDepth = 0;
    private Integer rotates;

    @Override
    public BinaryNode findNode (Integer value) {
        nodeDepth = root.getNodeDepth(value);
        BinaryNode node = null;
        if (root != null) node = root.findNode(value);
        if (node == null || !node.getValue().equals(value)) return null;
        return node;
    }

    public BinaryTree(BinaryTree tree) {
        if (tree != null && tree.getRoot() != null) this.root = tree.getRoot().copy(null);
    }

    @Override
    public void addNode (Integer value) {
        BinaryNode parent = null;
        BinaryNode node = root;

        while (node != null) {
            parent = node;
            if (value < node.getValue()) node = node.getLeft();
            else if (value > node.getValue()) node = node.getRight();
            else return;
        }

        BinaryNode added = new BinaryNode(value);
        added.setFather(parent);

        if (parent == null) root = added;
        else if (value < parent.getValue()) parent.setLeft(added);
        else parent.setRight(added);
        nodeDepth = root.getNodeDepth(value);
    }

    @Override
    public Integer getTreeHeight() {
        if (root == null) return 0;
        else return getTreeHeight(root);
    }

    @Override
    public boolean isNull(BinaryNode node) {
        return node != null;
    }

    @Override
    public Integer getTreeLeafs() {
        if (root == null) return 0;
        else return root.getTreeLeafs();
    }

    private Integer getTreeHeight(BinaryNode node) {
        if (node == null) return 0;
        int lh = getTreeHeight(node.getLeft());
        int rh = getTreeHeight(node.getRight());
        return Math.max(lh,rh)+1;
    }

    @Override
    public void removeNode (Integer value) {
        nodeDepth = root.getNodeDepth(value);
        BinaryNode x = findNode(value);
        if (x == null) return;

        BinaryNode y, z;

        y = x.getLeft() == null || x.getRight() == null ? x : x.getSuccessor();
        z = y.getLeft() != null ? y.getLeft() : y.getRight();

        if (z != null) z.setFather(y.getFather());

        if(y.getFather() == null) root = z;
        else if (y == y.getFather().getLeft()) y.getFather().setLeft(z);
        else y.getFather().setRight(z);

        if (y != x) x.setValue(y.getValue());
    }
}
