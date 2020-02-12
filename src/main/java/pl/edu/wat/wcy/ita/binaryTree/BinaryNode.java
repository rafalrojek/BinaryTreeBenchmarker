package pl.edu.wat.wcy.ita.binaryTree;
import lombok.Data;

@Data
public class BinaryNode {
    private BinaryNode father;
    private BinaryNode left;
    private BinaryNode right;
    private Integer value;

    public BinaryNode(Integer value) {
        this.value = value;
        this.father = null;
        this.left = null;
        this.right = null;
    }


    public BinaryNode findNode(Integer value) {
        if (this.getValue().compareTo(value) < 0 && getLeft() != null) return this.getLeft().findNode(value);
        else if (this.getValue().compareTo(value) > 0 && getRight() != null) return this.getRight().findNode(value);
        else return this;
    }

    public BinaryNode copy(BinaryNode father) {
        BinaryNode node = new BinaryNode(this.getValue());
        node.setFather(father);
        if (this.getLeft() != null) node.setLeft(this.getLeft().copy(node));
        if (this.getRight() != null) node.setRight(this.getRight().copy(node));
        return node;
    }
}
