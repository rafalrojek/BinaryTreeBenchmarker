package pl.edu.wat.wcy.ita.redBlackTree;

import lombok.Data;
import pl.edu.wat.wcy.ita.Tree.Tree;
import java.util.LinkedList;

@Data
public class RedBlackTree implements Tree<RedBlackNode> {
    private static LinkedList<RedBlackTree> container = new LinkedList<>();
    private  RedBlackNode nil = new RedBlackNode(null,Color.Black);
    private RedBlackNode root;
    private String state;

    RedBlackTree (String state) {
        RedBlackTree last = container.getLast();
        if (last != null && last.root != null) this.root = last.getRoot().copy(null);
        this.state = state;
        container.addLast(this);
    }

    @Override
    public boolean addNode (Integer value) {
        if (findNode(value) != null) return false;

        RedBlackNode node = new RedBlackNode(value, Color.Red);
        node.setLeft(nil);
        node.setRight(nil);
        node.setFather(root);

        if (node.getFather() == nil) root = node;
        else while (true) {
            if (value < node.getFather().getValue()) {
                if (node.getFather().getLeft() == nil) {
                    node.getFather().setLeft(node);
                    break;
                }
                node.setFather(node.getFather().getLeft());
            }
            else {
                if(node.getFather().getRight() == nil) {
                    node.getFather().setRight(node);
                    break;
                }
                node.setFather(node.getFather().getRight());
            }
        }

        RedBlackNode tmp;
        while (node != root && node.getFather().getColor() == Color.Red) {
            if (node.getFather() == node.getFather().getFather().getLeft()) {
                tmp = node.getFather().getFather().getRight();

                if (tmp.getColor() == Color.Red) {
                    node.getFather().setColor(Color.Black);
                    tmp.setColor(Color.Black);
                    node.getFather().getFather().setColor(Color.Red);
                    continue;
                }

                if (node == node.getFather().getRight()) {
                    node = node.getFather();
                    leftRotate(node);
                }

                node.getFather().setColor(Color.Black);
                node.getFather().getFather().setColor(Color.Red);
                rightRotate(node.getFather().getFather());
            }
            else {
                tmp = node.getFather().getFather().getLeft();

                if (tmp.getColor() == Color.Red) {
                    node.getFather().setColor(Color.Black);
                    tmp.setColor(Color.Black);
                    node.getFather().getFather().setColor(Color.Red);
                    continue;
                }

                if (node == node.getFather().getLeft()) {
                    node = node.getFather();
                    rightRotate(node);
                }

                node.getFather().setColor(Color.Black);
                node.getFather().getFather().setColor(Color.Red);
                leftRotate(node.getFather().getFather());
            }
            break;
        }
        root.setColor(Color.Black);
        return true;
    }

    @Override
    public boolean removeNode (Integer value) {
        RedBlackNode X = findNode(value);
        if (X == null) return false;

        RedBlackNode W, Y, Z;
        if (X.getLeft() == nil || X.getRight() == nil) Y = X;
        else Y = X.getSuccessor(nil);

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
            }
            Z = root;
        }
        Z.setColor(Color.Black);
        return true;
    }

    @Override
    public RedBlackNode findNode (Integer value) {
        if (root != null) return root.findNode(value);
        else return null;
    }

    private void leftRotate (RedBlackNode node) {
        RedBlackNode tmp = node.getRight();
        node.setRight(tmp.getLeft());
        if (tmp.getLeft() != nil) tmp.getLeft().setFather(node);
        tmp.setFather(node.getFather());
        if (node.getFather() == nil) root = tmp;
        else if (node == node.getFather().getLeft()) node.getFather().setLeft(tmp);
        else node.getFather().setRight(tmp);
        tmp.setLeft(node);
        node.setFather(tmp);
    }

    private void rightRotate (RedBlackNode node) {
        RedBlackNode tmp = node.getLeft();
        node.setLeft(tmp.getRight());
        if (tmp.getRight() != nil) tmp.getRight().setFather(node);
        tmp.setFather(node.getFather());
        if (node.getFather() == nil) root = tmp;
        else if (node == node.getFather().getRight()) node.getFather().setRight(tmp);
        else node.getFather().setLeft(tmp);
        tmp.setRight(node);
        node.setFather(tmp);
    }
}
