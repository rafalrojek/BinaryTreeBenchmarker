package pl.edu.wat.wcy.ita.avlTree;

import lombok.Data;

@Data
public class AVLNode {
    private AVLNode father;
    private AVLNode left;
    private AVLNode right;
    private Integer value;
    private Integer bf;

    public AVLNode(Integer value) {
        this.value = value;
        this.father = null;
        this.left = null;
        this.right = null;
        this.bf = 0;
    }

    public AVLNode getPredecessor () {
        AVLNode node;
        AVLNode tmp = this;

        if (this.getLeft() != null) {
            tmp = tmp.getLeft();
            while (tmp.getRight() != null) tmp = tmp.getRight();
        }
        else {
            do {
                node = tmp;
                tmp = tmp.getFather();
            } while (tmp != null && tmp.getRight() != node);
        }
        return tmp;
    }

    public AVLNode findNode(Integer value) {
        if (this.getValue().compareTo(value) < 0 && getLeft() != null) return this.getLeft().findNode(value);
        else if (this.getValue().compareTo(value) > 0 && getRight() != null) return this.getRight().findNode(value);
        else return this;
    }

    public AVLNode copy(AVLNode father) {
        AVLNode node = new AVLNode(this.getValue());
        node.setFather(father);
        node.setBf(bf);
        if (this.getLeft() != null) node.setLeft(this.getLeft().copy(node));
        if (this.getRight() != null) node.setRight(this.getRight().copy(node));
        return node;
    }
}
