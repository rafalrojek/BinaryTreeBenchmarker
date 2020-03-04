package pl.edu.wat.wcy.ita.avlTree;
import lombok.Data;
import pl.edu.wat.wcy.ita.Tree.Node;

@Data
public class AVLNode implements Node {
    private AVLNode father;
    private AVLNode left;
    private AVLNode right;
    private Integer value;
    private Integer bf;

    AVLNode(Integer value) {
        this.value = value;
        this.father = null;
        this.left = null;
        this.right = null;
        this.bf = 0;
    }

    AVLNode getPredecessor() {
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

    Integer getNodeDepth(Integer value) {
        if (this.value.equals(value)) return 1;
        else if (this.value.compareTo(value) > 0 && getLeft() != null) return 1+getLeft().getNodeDepth(value);
        else if (this.value.compareTo(value) < 0 && getRight() != null) return 1+getRight().getNodeDepth(value);
        else return 1;
    }

    public AVLNode findNode(Integer value) {
        if (this.getValue().compareTo(value) > 0 && getLeft() != null) return this.getLeft().findNode(value);
        else if (this.getValue().compareTo(value) < 0 && getRight() != null) return this.getRight().findNode(value);
        else return this;
    }

    public Integer getTreeLeafs() {
        if (this.left == null && this.right == null) return 1;
        int leafs = 0;
        if (getLeft() != null) leafs += getLeft().getTreeLeafs();
        if (getRight() != null) leafs += getRight().getTreeLeafs();
        return leafs;
    }

    AVLNode copy(AVLNode father) {
        AVLNode node = new AVLNode(this.getValue());
        node.setFather(father);
        node.setBf(bf);
        if (this.getLeft() != null) node.setLeft(this.getLeft().copy(node));
        if (this.getRight() != null) node.setRight(this.getRight().copy(node));
        return node;
    }

    @Override
    public String toString() {
        return "AVLNode{" +
                "value=" + value +
                '}';
    }
}
