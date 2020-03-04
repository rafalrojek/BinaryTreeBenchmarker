package pl.edu.wat.wcy.ita.redBlackTree;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wat.wcy.ita.Tree.Tree;

@Data
@NoArgsConstructor
public class RedBlackTree implements Tree<RedBlackNode> {
    public static RedBlackNode nil = new RedBlackNode();
    private RedBlackNode root = nil;
    private Integer nodeDepth = 0;

    public RedBlackTree(RedBlackTree redBlackTree) {
        if (redBlackTree != null && redBlackTree.getRoot() != nil) this.root = redBlackTree.getRoot().copy(null);
        else this.root = nil;
        nodeDepth = 0;
    }

    @Override
    public void addNode (Integer value) {
        if (findNode(value) != nil) return;

        RedBlackNode x = new RedBlackNode(value, Color.Red);
        x.setFather(root);
        if (x.getFather() == nil) root = x;
        else while (true) {
            if (value < x.getFather().getValue()) {
                if (x.getFather().getLeft() == nil) {
                    x.getFather().setLeft(x);
                    break;
                }
                x.setFather(x.getFather().getLeft());
            }
            else {
                if(x.getFather().getRight() == nil) {
                    x.getFather().setRight(x);
                    break;
                }
                x.setFather(x.getFather().getRight());
            }
        }
        nodeDepth = root.getNodeDepth(value);
        RedBlackNode y;
        while (x != root && x.getFather().getColor() == Color.Red) {
            if (x.getFather() == x.getFather().getFather().getLeft()) {
                y = x.getFather().getFather().getRight();

                if (y.getColor() == Color.Red) {
                    x.getFather().setColor(Color.Black);
                    y.setColor(Color.Black);
                    x.getFather().getFather().setColor(Color.Red);
                    x = x.getFather().getFather();
                    continue;
                }

                if (x == x.getFather().getRight()) {
                    x = x.getFather();
                    leftRotate(x);
                }

                x.getFather().setColor(Color.Black);
                x.getFather().getFather().setColor(Color.Red);
                rightRotate(x.getFather().getFather());
                break;
            }
            else {
                y = x.getFather().getFather().getLeft();

                if (y.getColor() == Color.Red) {
                    x.getFather().setColor(Color.Black);
                    y.setColor(Color.Black);
                    x.getFather().getFather().setColor(Color.Red);
                    x = x.getFather().getFather();
                    continue;
                }

                if (x == x.getFather().getLeft()) {
                    x = x.getFather();
                    rightRotate(x);
                }

                x.getFather().setColor(Color.Black);
                x.getFather().getFather().setColor(Color.Red);
                leftRotate(x.getFather().getFather());
                break;
            }
        }
        root.setColor(Color.Black);

    }

    @Override
    public void removeNode (Integer value) {
        RedBlackNode X = findNode(value);
        nodeDepth = root.getNodeDepth(value);
        if (X == nil) return;

        RedBlackNode W, Y, Z;
        if (X.getLeft() == nil || X.getRight() == nil) Y = X;
        else Y = X.getSuccessor();

        if (Y.getLeft() != nil) Z = Y.getLeft();
        else Z = Y.getRight();

        Z.setFather(Y.getFather());

        if (Y.getFather() == nil) root = Z;
        else if ( Y == Y.getFather().getLeft()) Y.getFather().setLeft(Z);
        else Y.getFather().setRight(Z);

        if (Y != X) X.setValue(Y.getValue());

        if (Y.getColor() == Color.Black) while (Z != root && Z.getColor() == Color.Black) {
            if (Z == Z.getFather().getLeft()) {
                W = Z.getFather().getRight();

                if(W.getColor() == Color.Red) {
                    W.setColor(Color.Black);
                    Z.getFather().setColor(Color.Red);
                    leftRotate(Z.getFather());
                    W = Z.getFather().getRight();
                }

                if (W.getLeft().getColor() == Color.Black && W.getRight().getColor() == Color.Black) {
                    W.setColor(Color.Red);
                    Z = Z.getFather();
                    continue;
                }

                if (W.getRight().getColor() == Color.Black) {
                    W.getLeft().setColor(Color.Black);
                    W.setColor(Color.Red);
                    rightRotate(W);
                    W = Z.getFather().getRight();
                }

                W.setColor(Z.getFather().getColor());
                Z.getFather().setColor(Color.Black);
                W.getRight().setColor(Color.Black);
                leftRotate(Z.getFather());
                Z = root;
            }
            else {
                W = Z.getFather().getLeft();

                if (W.getColor() == Color.Red) {
                    W.setColor(Color.Black);
                    Z.getFather().setColor(Color.Red);
                    rightRotate(Z.getFather());
                    W = Z.getFather().getLeft();
                }

                if (W.getLeft().getColor() == Color.Black && W.getRight().getColor() == Color.Black) {
                    W.setColor(Color.Red);
                    Z = Z.getFather();
                    continue;
                }

                if (W.getLeft().getColor() == Color.Black) {
                    W.getRight().setColor(Color.Black);
                    W.setColor(Color.Red);
                    leftRotate(W);
                    W = Z.getFather().getLeft();
                }

                W.setColor(Z.getFather().getColor());
                Z.getFather().setColor(Color.Black);
                W.getLeft().setColor(Color.Black);
                rightRotate(Z.getFather());
                Z = root;
            }
        }
        Z.setColor(Color.Black);
    }

    @Override
    public Integer getTreeHeight() {
        if (root == nil) return 0;
        else return this.getTreeHeight(root);
    }

    @Override
    public boolean isNull(RedBlackNode node) {
        return node != nil;
    }

    @Override
    public Integer getTreeLeafs() {
        if (root == nil) return 0;
        else return root.getTreeLeafs();
    }

    private Integer getTreeHeight(RedBlackNode node) {
        if (node == nil) return 0;
        int lh = getTreeHeight(node.getLeft());
        int rh = getTreeHeight(node.getRight());
        return Math.max(lh,rh)+1;
    }

    @Override
    public RedBlackNode findNode (Integer value) {
        nodeDepth = root.getNodeDepth(value);
        if (root != nil) {
            return root.findNode(value);
        }
        else return nil;
    }

    private void leftRotate (RedBlackNode a) {
        RedBlackNode b = a.getRight();
        if (b == nil) return;
        RedBlackNode p = a.getFather();
        a.setRight(b.getLeft());
        if (a.getRight() != nil) a.getRight().setFather(a);

        b.setLeft(a);
        b.setFather(p);
        a.setFather(b);

        if (p == nil) root = b;
        else if (p.getLeft() == a) p.setLeft(b);
        else p.setRight(b);
    }

    private void rightRotate (RedBlackNode a) {
        RedBlackNode b = a.getLeft();
        if (b == nil) return;
        RedBlackNode p = a.getFather();
        a.setLeft(b.getRight());
        if (a.getLeft() != nil) a.getLeft().setFather(a);
        b.setRight(a);
        b.setFather(p);
        a.setFather(b);
        if (p == nil) root = b;
        else if (a == p.getRight()) p.setRight(b);
        else p.setLeft(b);
    }
}
