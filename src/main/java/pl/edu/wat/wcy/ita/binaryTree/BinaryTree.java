package pl.edu.wat.wcy.ita.binaryTree;

import lombok.Data;
import pl.edu.wat.wcy.ita.Tree.Tree;
import java.util.LinkedList;

@Data
public class BinaryTree implements Tree<BinaryNode> {
    private static LinkedList<BinaryTree> container = new LinkedList<>();
    private BinaryNode root;
    private String state;

    @Override
    public BinaryNode findNode (Integer value) {
        if (root != null) return root.findNode(value);
        else return null;
    }

    BinaryTree (String state) {
       BinaryTree last = container.getLast();
       if (last != null && last.root != null) this.root = last.getRoot().copy(null);
       this.state = state;
        container.addLast(this);
    }

    @Override
    public boolean addNode (Integer value) {
        BinaryNode parent = null;
        BinaryNode node = root;

        while (node != null) {
            parent = node;
            if (value < node.getValue()) node = node.getLeft();
            else if (value > node.getValue()) node = node.getRight();
            else return false;
        }

        BinaryNode added = new BinaryNode(value);
        added.setFather(parent);

        if (parent == null) root = added;
        else if (value < parent.getValue()) parent.setLeft(added);
        else parent.setRight(added);

        return true;
    }

    @Override
    public boolean removeNode (Integer value) {
        BinaryNode node = findNode(value);
        if (node == null) return false;

        if (node.getLeft() == null) transplant(node, node.getRight());
        else if (node.getRight() == null) transplant(node, node.getLeft());
        else {
            BinaryNode tmp = getMinimum();
            if (tmp == null) return false;
            if (tmp.getFather() != node) {
                transplant(tmp, tmp.getRight());
                tmp.setRight(node.getRight());
                tmp.getRight().setFather(tmp);
            }
            transplant(node, tmp);
            tmp.setLeft(node.getLeft());
            tmp.getLeft().setFather(tmp);
        }
        return true;
    }

    private void transplant (BinaryNode node1, BinaryNode node2) {
        if (node1.getFather() == null) root = node2;
        else if (node1 == node1.getFather().getLeft()) node1.getFather().setLeft(node2);
        else node1.getFather().setRight(node2);
        if (node2 != null) node2.setFather(node1.getFather());
    }

    private BinaryNode getMinimum() {
        BinaryNode node = root;
        while (node.getLeft() != null) node = node.getLeft();
        return node;
    }
}
