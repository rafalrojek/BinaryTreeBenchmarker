package pl.edu.wat.wcy.ita.redBlackTree;

import lombok.Data;

@Data
public class RedBlackNode{
    private RedBlackNode father;
    private RedBlackNode left;
    private RedBlackNode right;
    private Integer value;
    private Color color;

    RedBlackNode (Integer value, Color color) {
        this.value = value;
        this.father = null;
        this.left = null;
        this.right = null;
        this.color = color;
    }

    public RedBlackNode findNode(Integer value) {
        if (this.getValue().compareTo(value) < 0 && getLeft() != null) return this.getLeft().findNode(value);
        else if (this.getValue().compareTo(value) > 0 && getRight() != null) return this.getRight().findNode(value);
        else return this;
    }

    public RedBlackNode copy (RedBlackNode father) {
        RedBlackNode node = new RedBlackNode(this.getValue(),this.getColor());
        node.setFather(this.getFather());
        if (this.getLeft() != null) node.setLeft(this.getLeft().copy(node));
        if (this.getRight() != null) node.setRight(this.getRight().copy(node));
        return node;
    }

    public RedBlackNode getSuccessor(RedBlackNode nil) {
        RedBlackNode node;
        RedBlackNode tmp = this;

        if (this != nil) {
            if (this.getRight() != nil) {
                node = this.getRight();
                while (node.getLeft() != nil) node = node.getLeft();
            }
            else {
                node = this.getFather();
                while (node != nil && tmp == node.getRight()) {
                    tmp = node;
                    node = node.getFather();
                }
            }
            return node;
        }
        return nil;
    }
}
