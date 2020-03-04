package pl.edu.wat.wcy.ita.splayTree;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wat.wcy.ita.Tree.Tree;

@Data
@NoArgsConstructor
public class SplayTree implements Tree<SplayNode> {
    private SplayNode root = null;
    private Integer nodeDepth = 0;

    public SplayTree(SplayTree tree) {
        if (tree != null && tree.getRoot() != null) this.root = tree.getRoot().copy(null);
    }

    @Override
    public void addNode(Integer value) {
        SplayNode node = splay(value, true);
        SplayNode newNode = new SplayNode(value);
        if (node == null) this.root = newNode;
        else if (node.getValue().compareTo(value)< 0) adAsLeftRoot(newNode,node);
        else if (node.getValue().compareTo(value)>0) adAsRightRoot(newNode,node);
    }

    private void adAsRightRoot(SplayNode newNode, SplayNode root) {
        SplayNode tmp = root.getLeft();
        newNode.setRight(root);
        newNode.getRight().setFather(newNode);
        root.setLeft(null);
        newNode.setLeft(tmp);
        if (tmp != null) newNode.getLeft().setFather(newNode);
        this.root = newNode;
    }

    private void adAsLeftRoot(SplayNode newNode, SplayNode root) {
        SplayNode tmp = root.getRight();
        newNode.setLeft(root);
        newNode.getLeft().setFather(newNode);
        root.setRight(null);
        newNode.setRight(tmp);
        if (tmp != null) newNode.getRight().setFather(newNode);
        this.root = newNode;
    }

    @Override
    public void removeNode(Integer value) {
        if (root == null) return;
        SplayNode tl, tr;

        splay(value, true);
        if (!root.getValue().equals(value)) return;
        tl = root.getLeft();
        tr = root.getRight();

        if(tl != null) {
            tl.setFather(null);
            if (tr != null) tr.setFather(null);
            root = tl;
            splay(value, false);
            while (root.getRight() != null) rotateLeft(root);
            root.setRight(tr);
            if (tr != null) tr.setFather(root);
        }
        else if (tr != null) {
            tr.setFather(null);
            root = tr;
            splay(value, false);
            while (root.getLeft() != null) rotateRight(root);
            root.setLeft(null);
        }
        else root = null;

    }

    @Override
    public SplayNode findNode(Integer value) {
        if (root == null) return null;
        else {
            splay(value,true);
            if (root.getValue().equals(value)) return root;
            else return null;
        }
    }

    @Override
    public Integer getTreeHeight() {
        if (root == null) return 0;
        else return getTreeHeight(root);
    }

    @Override
    public boolean isNull(SplayNode node) {
        return node != null;
    }

    @Override
    public Integer getTreeLeafs() {
        if (root == null) return 0;
        else return root.getTreeLeafs();
    }



    private Integer getTreeHeight(SplayNode node) {
        if (node == null) return 0;
        int lh = getTreeHeight(node.getLeft());
        int rh = getTreeHeight(node.getRight());
        return Math.max(lh,rh)+1;
    }

    private SplayNode splay (Integer key, boolean countDepth) {
        SplayNode x = this.root, tmp = null;

        if (x == null) return null;

        do {
            if (x.getValue().equals(key)) break;
            tmp = x;
            x = (key < x.getValue() ? x.getLeft() : x.getRight());
        } while (x != null);

        if (x == null) x = tmp;
        if(countDepth)
            nodeDepth = root.getNodeDepth(x.getValue());


        while (x.getFather() != null) {
            if (x.getFather().getFather() == null) {
                zig(x);
                return root;
            }
            else if ((x.getFather().getFather().getLeft() == x.getFather()) && (x.getFather().getLeft() == x)) rightZigZig(x);
            else if((x.getFather().getFather().getRight() == x.getFather()) && (x.getFather().getRight() == x)) leftZigZig(x);
            else if(x.getFather().getRight() == x)leftZigRightZag(x);
            else rightZigLeftZag(x);
        }
        return root;
    }

    private void rightZigLeftZag (SplayNode x) {
        rotateRight(x.getFather());
        rotateLeft(x.getFather());
    }

    private void leftZigRightZag (SplayNode x) {
        rotateLeft(x.getFather());
        rotateRight(x.getFather());
    }

    private void leftZigZig (SplayNode x) {
        rotateLeft(x.getFather().getFather());
        rotateLeft(x.getFather());
    }

    private void rightZigZig (SplayNode x) {
        rotateRight(x.getFather().getFather());
        rotateRight(x.getFather());
    }

    private void zig (SplayNode x) {
        if (x.getFather().getLeft() == x) rotateRight(x.getFather());
        else rotateLeft(x.getFather());
    }

    private void rotateLeft (SplayNode a) {
        SplayNode b = a.getRight(), p = a.getFather();
        if (b != null) {
            a.setRight(b.getLeft());
            if (a.getRight() != null) a.getRight().setFather(a);
            b.setLeft(a);
            rotateSetFather(a, b, p);
        }
    }

    private void rotateRight (SplayNode a){
        SplayNode b = a.getLeft(), p = a.getFather();
        if (b != null) {
            a.setLeft(b.getRight());
            if (a.getLeft() != null) a.getLeft().setFather(a);
            b.setRight(a);
            rotateSetFather(a, b, p);
        }
    }

    private void rotateSetFather(SplayNode a, SplayNode b, SplayNode p) {
        b.setFather(p);
        a.setFather(b);
        if (p == null) root = b;
        else if (p.getLeft() == a) p.setLeft(b);
        else p.setRight(b);
    }
}
