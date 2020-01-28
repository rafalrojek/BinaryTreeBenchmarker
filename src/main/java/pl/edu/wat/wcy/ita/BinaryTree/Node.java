package pl.edu.wat.wcy.ita.BinaryTree;

import lombok.Data;

@Data
public class Node {
    private Node father;
    private Node left;
    private Node right;
    private Integer value;

    public Node(Integer value) {
        this.value = value;
        this.father = null;
        this.left = null;
        this.right = null;
    }

    Node findNode(Integer value) {
        if (this.value.compareTo(value) < 0 && left != null) return left.findNode(value);
        else if (this.value.compareTo(value) > 0 && right != null) return right.findNode(value);
        else return this;
    }

    public int countChild() {
        int value = 0;
        if (left != null) value++;
        if (right != null) value++;
        return value;
    }

    private void changeChild (Node node, Node newNode) {
        if (this.right == node) this.right = newNode;
        else this.left = newNode;
    }

    public Node remove(BinaryTree root) {
        return this;
    }
}
