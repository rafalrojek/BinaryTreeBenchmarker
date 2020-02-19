package pl.edu.wat.wcy.ita.splayTree;
import pl.edu.wat.wcy.ita.Tree.Tree;
import java.util.LinkedList;

public class SplayTree implements Tree<SplayNode> {
    private static LinkedList<SplayTree> container = new LinkedList<>();
    private SplayNode root;

    @Override
    public boolean addNode(Integer value) {
        SplayNode node = splay(value);
        SplayNode newNode = new SplayNode(value);
        if (node == null) this.root = newNode;
        else if (node.getValue().compareTo(value)< 0) adAsLeftRoot(newNode,newNode);
        else if (node.getValue().compareTo(value)>0) adAsRightRoot(newNode,node);
        else return false;
        return true;
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
    public boolean removeNode(Integer value) {
        SplayNode node = splay(value);
        if (node == null || !node.getValue().equals(value)) return false;

        if (node.getLeft() != null) {
            SplayNode right = node.getRight();
            this.root = node.getLeft();
            node.setFather(null);
            splay(Integer.MAX_VALUE);
            node.setRight(right);
            if (right != null) right.setFather(node);
        }
        else {
            this.root = node.getRight();
            this.root.setFather(null);
        }
        return true;
    }

    @Override
    public SplayNode findNode(Integer value) {
        if (root != null) return root.findNode(value);
        else return null;
    }

    private SplayNode splay (Integer key) {
        SplayNode x = this.root, tmp = null;

        if (x == null) return null;

        do {
            if (x.getValue().equals(key)) break;
            tmp = x;
            x = (key < x.getValue() ? x.getLeft() : x.getRight());
        } while (x != null);

        if (x == null) x = tmp;

        while (x.getFather() != null) {
            if (x.getFather().getFather() != null) {
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
