package pl.edu.wat.wcy.ita.binaryTree;
import lombok.Data;
import pl.edu.wat.wcy.ita.Tree.Node;

@Data
public class BinaryNode implements Node {
    private BinaryNode father;
    private BinaryNode left;
    private BinaryNode right;
    private Integer value;

    @Override
    public String toString() {
        return "BinaryNode{" +
                "value=" + value +
                '}';
    }

    BinaryNode(Integer value) {
        this.value = value;
        this.father = null;
        this.left = null;
        this.right = null;
    }

    public Integer getTreeLeafs() {
        if (this.left == null && this.right == null) return 1;
        int leafs = 0;
        if (getLeft() != null) leafs += getLeft().getTreeLeafs();
        if (getRight() != null) leafs += getRight().getTreeLeafs();
        return leafs;
    }

    public BinaryNode findNode(Integer value) {
        if (this.getValue().compareTo(value) > 0 && getLeft() != null) return this.getLeft().findNode(value);
        else if (this.getValue().compareTo(value) < 0 && getRight() != null) return this.getRight().findNode(value);
        else return this;
    }

    BinaryNode copy (BinaryNode father) {
        BinaryNode node = new BinaryNode(this.getValue());
        node.setFather(father);
        if (this.getLeft() != null) node.setLeft(this.getLeft().copy(node));
        if (this.getRight() != null) node.setRight(this.getRight().copy(node));
        return node;
    }

    private BinaryNode minimum() {
        BinaryNode p = this;
        while (p.getLeft() != null) p =p.getLeft();
        return p;
    }

    Integer getNodeDepth(Integer value) {
        if (this.value.equals(value)) return 1;
        else if (this.value.compareTo(value) > 0 && getLeft() != null) return 1+getLeft().getNodeDepth(value);
        else if (this.value.compareTo(value) < 0 && getRight() != null) return 1+getRight().getNodeDepth(value);
        else return 1;
    }

    BinaryNode getSuccessor () {
        BinaryNode p = this;
        BinaryNode r = p.getFather();

        if(p.getRight() != null) return p.getRight().minimum();
        while (r != null && p == r.getRight()) {
            p = r;
            r = r.getFather();
        }
        return r;
    }
}
