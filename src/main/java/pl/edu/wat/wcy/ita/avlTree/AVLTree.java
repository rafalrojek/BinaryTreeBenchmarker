package pl.edu.wat.wcy.ita.avlTree;

import lombok.Data;
import pl.edu.wat.wcy.ita.Tree.Tree;
import java.util.LinkedList;

@Data
public class AVLTree implements Tree<AVLNode> {
    private static LinkedList<AVLTree> container = new LinkedList<>();
    private AVLNode root;
    private String state;

    AVLTree (String state) {
        AVLTree last = container.getLast();
        if (last != null && last.root != null) this.root = last.getRoot().copy(null);
        this.state = state;
        container.addLast(this);
    }

    @Override
    public boolean addNode(Integer value) {
        if (findNode(value) != null) return false;

        AVLNode node = new AVLNode(value);
        AVLNode father = root;

        if (father == null) {
            root = node;
            return true;
        }
        while (true)
            if (value < father.getValue()) {
                if (father.getLeft() != null) father = father.getLeft();
                else  {
                    father.setLeft(node);
                    break;
                }
            }
        else {
            if (father.getRight() != null) father = father.getRight();
            else {
                father.setLeft(node);
                break;
            }
        }
        node.setFather(father);

        if (father.getBf() != 0) father.setBf(0);
        else {
            if (father.getLeft() == node) father.setBf(1);
            else father.setBf(-1);

            AVLNode looper = father.getFather();
            boolean rotate = false;
            while (looper != null) {
                if (looper.getBf() != 0) {
                    rotate = true;
                    break;
                }

                if (looper.getLeft() == father) looper.setBf(1);
                else looper.setBf(-1);

                father = looper;
                looper = looper.getFather();
            }
            if (rotate) {
                if (looper.getBf() == 1) {
                    if(looper.getRight() == father) looper.setBf(0);
                    else if (father.getBf() == -1) leftRightRotate(looper);
                    else leftLeftRotate(looper);
                }
                else {
                    if (looper.getLeft() == father) looper.setBf(0);
                    else if (father.getBf() == 1) rightLeftRotate(looper);
                    else rightRightRotate(looper);
                }
            }
        }
        return true;
    }

    @Override
    public boolean removeNode(Integer value) {
        //TODO Implement removing AVL node
        return false;
    }

    @Override
    public AVLNode findNode(Integer value) {
        if (root != null) return root.findNode(value);
        else return null;
    }

    private void rightRightRotate (AVLNode node) {
        AVLNode b = node.getRight();

        node.setRight(b.getLeft());
        if (node.getRight() != null) node.getRight().setFather(node);

        b.setLeft(node);
        b.setFather(node.getFather());
        node.setFather(b);

        finishRotate(node, b);
    }

    private void leftLeftRotate (AVLNode node) {
        AVLNode b = node.getLeft();

        node.setLeft(b.getRight());
        if (node.getLeft() != null) node.getLeft().setFather(node);

        b.setRight(node);
        b.setFather(node.getFather());
        node.setFather(b);

        finishRotate(node, b);
    }

    private void finishRotate(AVLNode node, AVLNode b) {
        if (b.getFather() == null) root = b;
        else if (b.getFather().getLeft() == node) b.getFather().setLeft(b);
        else b.getFather().setRight(b);

        if (b.getBf() == -1) {
            node.setBf(0);
            b.setBf(0);
        } else {
            node.setBf(-1);
            b.setBf(-1);
        }
    }

    private void rightLeftRotate (AVLNode node) {
        AVLNode b = node.getRight();
        AVLNode c = node.getLeft();

        b.setLeft(c.getRight());
        if (b.getLeft() != null) b.getLeft().setFather(b);

        node.setRight(c.getLeft());
        if (node.getRight() != null) node.getRight().setFather(node);

        c.setLeft(node);
        c.setRight(b);
        c.setFather(node.getFather());
        node.setFather(c);
        b.setFather(c);

        if (c.getFather() == null) root = c;
        else if (c.getFather().getLeft() == c) c.getFather().setLeft(c);
        else c.getFather().setRight(c);

        if (c.getBf() == -1) node.setBf(1);
        else node.setBf(0);

        if (c.getBf() == 1) b.setBf(-1);
        else b.setBf(0);

        c.setBf(0);
    }

    private void leftRightRotate (AVLNode node) {
        AVLNode b = node.getLeft();
        AVLNode c = node.getRight();

        b.setRight(c.getLeft());
        if (b.getRight() != null) b.getRight().setFather(b);

        node.setLeft(c.getRight());
        if (node.getLeft() != null) node.getLeft().setFather(node);

        c.setRight(node);
        c.setLeft(b);
        c.setFather(node.getFather());
        node.setFather(c);
        b.setFather(c);

        if (c.getFather() == null) root = c;
        else if (c.getFather().getLeft() == c) c.getFather().setLeft(c);
        else c.getFather().setRight(c);

        if (c.getBf() == 1) node.setBf(-1);
        else node.setBf(0);

        if (c.getBf() == -1) b.setBf(1);
        else b.setBf(0);

        c.setBf(0);
    }
}
