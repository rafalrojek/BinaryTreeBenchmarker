package pl.edu.wat.wcy.ita.splayTree;
import lombok.Data;
import pl.edu.wat.wcy.ita.Tree.Node;

@Data
public class SplayNode implements Node {

    private SplayNode father;
    private SplayNode left;
    private SplayNode right;
    private Integer value;
    static private SplayNode root;

    SplayNode(Integer value) {
        this.value = value;
        this.father = null;
        this.left = null;
        this.right = null;
    }

    @Override
    public String toString() {
        return "SplayNode{" +
                "value=" + value +
                '}';
    }

    SplayNode copy(SplayNode father) {
        SplayNode node = new SplayNode(this.getValue());
        node.setFather(father);
        if (this.getLeft() != null) node.setLeft(this.getLeft().copy(node));
        if (this.getRight() != null) node.setRight(this.getRight().copy(node));
        return node;
    }

    Integer getNodeDepth(Integer value) {
        if (this.value.equals(value)) return 1;
        else if (this.value.compareTo(value) > 0 && getLeft() != null) return 1+getLeft().getNodeDepth(value);
        else if (this.value.compareTo(value) < 0 && getRight() != null) return 1+getRight().getNodeDepth(value);
        else return 1;
    }

    public Integer getTreeLeafs() {
        if (this.left == null && this.right == null) return 1;
        int leafs = 0;
        if (getLeft() != null) leafs += getLeft().getTreeLeafs();
        if (getRight() != null) leafs += getRight().getTreeLeafs();
        return leafs;
    }
}

