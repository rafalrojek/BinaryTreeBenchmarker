package pl.edu.wat.wcy.ita.Tree;

public interface Tree<T> {
    void addNode(Integer value);
    void removeNode(Integer value);
    T findNode(Integer value);
    Node getRoot();
    Integer getTreeHeight();
    Integer getNodeDepth();
    boolean isNull (T node);
    Integer getTreeLeafs();
    Integer getRotates();
}
