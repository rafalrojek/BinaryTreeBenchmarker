package pl.edu.wat.wcy.ita.splayTree;

import lombok.Data;

@Data
public class SplayNode {

    private SplayNode father;
    private SplayNode left;
    private SplayNode right;
    private Integer value;
    static private SplayNode root;

    public SplayNode(Integer value) {
        this.value = value;
        this.father = null;
        this.left = null;
        this.right = null;
    }

    public SplayNode findNode(Integer value) {
        if (this.getValue().compareTo(value) < 0 && getLeft() != null) return this.getLeft().findNode(value);
        else if (this.getValue().compareTo(value) > 0 && getRight() != null) return this.getRight().findNode(value);
        else return this;
    }

    public SplayNode copy(SplayNode father) {
        SplayNode node = new SplayNode(this.getValue());
        node.setFather(father);
        if (this.getLeft() != null) node.setLeft(this.getLeft().copy(node));
        if (this.getRight() != null) node.setRight(this.getRight().copy(node));
        return node;
    }

}

