package pl.edu.wat.wcy.ita.BinaryTree;

import lombok.Data;
import java.util.LinkedList;

@Data
public class BinaryTree {
    private static LinkedList<BinaryTree> container = new LinkedList<>();
    private Node root;
    private String state;

    private Node findNode (Integer value) {
        return root.findNode(value);
    }

    public boolean addNode (Integer value) {
        Node added = new Node(value);
        Node node = this.findNode(value);
        if (node == null) this.setRoot(added);
        else {
            added.setFather(node);
            if(node.getValue().compareTo(value) < 0) node.setRight(added);
            else if (node.getValue().compareTo(value) > 0) node.setLeft(added);
            else return false;
        }
        return true;
    }

    public boolean isNodeExist(Integer value) {
        return findNode(value)!=null;
    }

    public boolean remove (Integer value) {
        Node node = findNode(value);
        if (node == null) return false;
        if (node.countChild() == 0)

        return true;
    }
}
