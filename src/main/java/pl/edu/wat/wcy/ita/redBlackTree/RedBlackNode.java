package pl.edu.wat.wcy.ita.redBlackTree;
import lombok.Data;
import pl.edu.wat.wcy.ita.Tree.Node;

@Data
public class RedBlackNode implements Node {
    private RedBlackNode father;
    private RedBlackNode left;
    private RedBlackNode right;
    private Integer value;
    private Color color;

    RedBlackNode (Integer value, Color color) {
        this.value = value;
        this.father = RedBlackTree.nil;
        this.left = RedBlackTree.nil;
        this.right = RedBlackTree.nil;
        this.color = color;
    }

    RedBlackNode () {
        this.value = null;
        this.color = Color.Black;
        this.father = this;
        this.left = this;
        this.right = this;
    }

    public RedBlackNode findNode(Integer value) {
        if (this == RedBlackTree.nil) return RedBlackTree.nil;
        else if (this.getValue().compareTo(value) > 0 && getLeft() != RedBlackTree.nil) return this.getLeft().findNode(value);
        else if (this.getValue().compareTo(value) < 0 && getRight() != RedBlackTree.nil) return this.getRight().findNode(value);
        else if (!this.value.equals(value)) return RedBlackTree.nil;
        else return this;
    }

    RedBlackNode copy(RedBlackNode father) {
        RedBlackNode node = new RedBlackNode(this.getValue(),this.getColor());
        if (father != null) node.setFather(father);
        else node.setFather(RedBlackTree.nil);
        if (this.getLeft() != RedBlackTree.nil) node.setLeft(this.getLeft().copy(node));
        if (this.getRight() != RedBlackTree.nil) node.setRight(this.getRight().copy(node));
        return node;
    }

    private RedBlackNode getMinimum () {
        RedBlackNode p = this;

        if (p != RedBlackTree.nil) while (p.getLeft() != RedBlackTree.nil) p = p.getLeft();
        return p;
    }

    RedBlackNode getSuccessor() {
        RedBlackNode p = this;
        RedBlackNode r;

        if (p == RedBlackTree.nil) return RedBlackTree.nil;
        else if (p.getRight() != RedBlackTree.nil) return p.getRight().getMinimum();
        else {
            r = p.getFather();
            while ((r != RedBlackTree.nil) && (p == r.getRight())) {
                p = r;
                r = r.getFather();
            }
            return r;
        }
    }

    Integer getNodeDepth(Integer value) {
        if (this == RedBlackTree.nil) return 0;
        else if (this.value.compareTo(value) > 0 && getLeft() != RedBlackTree.nil) return 1+getLeft().getNodeDepth(value);
        else if (this.value.compareTo(value) < 0 && getRight() != RedBlackTree.nil) return 1+getRight().getNodeDepth(value);
        else return 1;
    }

    public Integer getTreeLeafs() {
        if (this.left == RedBlackTree.nil && this.right == RedBlackTree.nil) return 1;
        int leafs = 0;
        if (getLeft() != RedBlackTree.nil) leafs += getLeft().getTreeLeafs();
        if (getRight() != RedBlackTree.nil) leafs += getRight().getTreeLeafs();
        return leafs;
    }

    @Override
    public String toString() {
        return "RedBlackNode{" +
                "value=" + value +
                '}';
    }
}
