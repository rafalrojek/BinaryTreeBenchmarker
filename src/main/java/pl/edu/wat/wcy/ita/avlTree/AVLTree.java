package pl.edu.wat.wcy.ita.avlTree;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wat.wcy.ita.Tree.Tree;

@Data
@NoArgsConstructor
public class AVLTree implements Tree<AVLNode> {
    private AVLNode root = null;
    private Integer nodeDepth = 0;

    public AVLTree (AVLTree tree) {
        if (tree != null && tree.getRoot() != null) this.root = tree.getRoot().copy(null);
    }

    @Override
    public void addNode(Integer value) {
        if (findNode(value) != null) return;

        AVLNode w = new AVLNode(value);
        AVLNode p = root;

        if (p == null) {
            root = w;
            return;
        }
        while (true) {
            if (value < p.getValue()) {
                if (p.getLeft() == null){
                    p.setLeft(w);
                    break;
                }
                p = p.getLeft();

            } else {
                if (p.getRight() == null) {
                    p.setRight(w);
                    break;
                }
                p = p.getRight();
            }
        }
        w.setFather(p);
        nodeDepth = root.getNodeDepth(value);

        if (p.getBf() != 0) p.setBf(0);
        else {
            if (p.getLeft() == w) p.setBf(1);
            else p.setBf(-1);

            AVLNode r = p.getFather();
            boolean t = false;
            while (r != null) {
                if (r.getBf() != 0) {
                    t = true;
                    break;
                }
                if (r.getLeft() == p) r.setBf(1);
                else r.setBf(-1);

                p = r;
                r = r.getFather();
            }
            if (t) {
                if (r.getBf() == 1) {
                    if(r.getRight() == p) r.setBf(0);
                    else if (p.getBf() == -1) leftRightRotate(r);
                    else leftLeftRotate(r);
                }
                else if (r.getBf() == -1) {
                    if (r.getLeft() == p) r.setBf(0);
                    else if (p.getBf() == 1) rightLeftRotate(r);
                    else rightRightRotate(r);
                }
            }
        }
    }

    @Override
    public Integer getTreeHeight() {
        if (root == null) return 0;
        else return getTreeHeight(root);
    }

    @Override
    public boolean isNull(AVLNode node) {
        return node != null;
    }

    @Override
    public Integer getTreeLeafs() {
        if (root == null) return 0;
        else return root.getTreeLeafs();
    }

    private Integer getTreeHeight(AVLNode node) {
        if (node == null) return 0;
        int lh = getTreeHeight(node.getLeft());
        int rh = getTreeHeight(node.getRight());
        return Math.max(lh,rh)+1;
    }

    @Override
    public void removeNode(Integer value) {
        AVLNode node = findNode(value);
        if (node == null) return;
        nodeDepth = root.getNodeDepth(value);
        removeNode(node);
    }

    @Override
    public AVLNode findNode(Integer value) {
        AVLNode node = null;
        if (root != null) node = root.findNode(value);
        else return null;
        nodeDepth = root.getNodeDepth(value);
        if (node == null || !node.getValue().equals(value)) return null;
        else return node;
    }

    private AVLNode removeNode (AVLNode x) {
        AVLNode y;
        boolean nest;

        if (x.getLeft() != null && x.getRight() != null) {
            y = removeNode(x.getPredecessor());
            nest = false;
        }
        else {
            if (x.getLeft() != null) {
                y = x.getLeft();
                x.setLeft(null);
            } else {
                y = x.getRight();
                x.setRight(null);
            }
            x.setBf(0);
            nest = true;
        }

        if (y != null) {
            y.setFather(x.getFather());
            y.setLeft(x.getLeft());
            if (y.getLeft() != null) y.getLeft().setFather(y);
            y.setRight(x.getRight());
            if (y.getRight() != null) y.getRight().setFather(y);
            y.setBf(x.getBf());
        }

        if (x.getFather() == null) root = y;
        else if (x.getFather().getLeft() == x) x.getFather().setLeft(y);
        else x.getFather().setRight(y);


        if (nest) {
            AVLNode z = y;
            AVLNode t;
            y = x.getFather();
            while (y != null) {
                if (y.getBf() == 0) {
                    if (y.getLeft() == z) y.setBf(-1);
                    else y.setBf(1);
                    break;
                }
                else {
                    if ((y.getBf() == 1 && y.getLeft() == z) || (y.getBf() == -1 && y.getRight() == z)) {
                        y.setBf(0);
                        z = y;
                        y = y.getFather();
                    }
                    else {
                        if (y.getLeft() == z) t= y.getRight();
                        else t = y.getLeft();

                        if (t.getBf() == 0) {
                            if (y.getBf() == 1) leftLeftRotate(y);
                            else rightRightRotate(y);
                            break;
                        }
                        else if (y.getBf().equals(t.getBf())) {
                            if (y.getBf() == 1) leftLeftRotate(y);
                            else rightRightRotate(y);
                            z = t;
                            y = t.getFather();
                        }
                        else {
                            if (y.getBf() == 1) leftRightRotate(y);
                            else rightLeftRotate(y);
                            z = y.getFather();
                            y = z.getFather();
                        }
                    }
                }
            }
        }
        return x;
    }

    private void rightRightRotate (AVLNode a) {
        AVLNode b = a.getRight();
        AVLNode p = a.getFather();

        a.setRight(b.getLeft());
        if (a.getRight() != null) a.getRight().setFather(a);

        b.setLeft(a);
        b.setFather(p);
        a.setFather(b);

        if (p == null) root = b;
        else if (b.getFather().getLeft() == a) p.setLeft(b);
        else p.setRight(b);

        if (b.getBf() == -1) {
            a.setBf(0);
            b.setBf(0);
        } else {
            a.setBf(-1);
            b.setBf(1);
        }
    }

    private void leftLeftRotate (AVLNode a) {
        AVLNode b = a.getLeft();
        AVLNode p = a.getFather();

        a.setLeft(b.getRight());
        if (a.getLeft() != null) a.getLeft().setFather(a);

        b.setRight(a);
        b.setFather(p);
        a.setFather(b);

        if (b.getFather() == null) root = b;
        else if (b.getFather().getLeft() == a)p.setLeft(b);
        else p.setRight(b);

        if (b.getBf() == 1) {
            a.setBf(0);
            b.setBf(0);
        } else {
            a.setBf(1);
            b.setBf(-1);
        }
    }

    private void rightLeftRotate (AVLNode a) {
        AVLNode b = a.getRight();
        AVLNode c = b.getLeft();
        AVLNode p = a.getFather();

        b.setLeft(c.getRight());
        if (b.getLeft() != null) b.getLeft().setFather(b);

        a.setRight(c.getLeft());
        if (a.getRight() != null) a.getRight().setFather(a);

        c.setLeft(a);
        c.setRight(b);
        a.setFather(c);
        b.setFather(c);
        c.setFather(p);

        if (p == null) root = c;
        else if (p.getLeft() == a) p.setLeft(c);
        else p.setRight(c);

        if (c.getBf() == -1) a.setBf(1);
        else a.setBf(0);

        if (c.getBf() == 1) b.setBf(-1);
        else b.setBf(0);

        c.setBf(0);
    }

    private void leftRightRotate (AVLNode a) {
        AVLNode b = a.getLeft();
        AVLNode c = b.getRight();
        AVLNode p = a.getFather();

        b.setRight(c.getLeft());
        if (b.getRight() != null) b.getRight().setFather(b);

        a.setLeft(c.getRight());
        if (a.getLeft() != null) a.getLeft().setFather(a);

        c.setRight(a);
        c.setLeft(b);
        a.setFather(c);
        b.setFather(c);
        c.setFather(p);

        if (p == null) root = c;
        else if (p.getLeft() == a) p.setLeft(c);
        else p.setRight(c);

        if (c.getBf() == 1) a.setBf(-1);
        else a.setBf(0);

        if (c.getBf() == -1) b.setBf(1);
        else b.setBf(0);

        c.setBf(0);
    }
}
