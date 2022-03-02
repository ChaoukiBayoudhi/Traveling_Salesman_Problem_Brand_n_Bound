import java.util.List;
import java.util.Queue;

public interface ItspBB {
    void generateGraph(int N);
    void showGraph();
    int min(int[] T);
    int[] getColumn(int[][] M, int columnIndex);
    int reduce(int[][] M);
    void reduceArray(int[] T,int min);
    Node getMinLeaf(Queue<Node> leafQueue);
    List<Node> getPossibleChildrenNodes(Node n);
    void setNodeCost(Node n, Node parent);
    void resolveTSP(int nbC);
}
