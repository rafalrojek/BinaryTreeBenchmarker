package pl.edu.wat.wcy.ita.Tree;

public interface Tree<T> {
    boolean addNode(Integer value);
    boolean removeNode(Integer value);
    T findNode(Integer value);
}
