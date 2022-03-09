
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.IntStream;

public class TspBranchNBound {
    public static int[][] graph;
    public static int nbCities;
    public static long cost=0L;
    public static int startCity;
    public static Queue<Node> leafQueue=new LinkedList<>();
    public static List<Integer> finalCycle;

    //generate an oriented graph randomly
    public static void generateGraph(int N)
    {
        Random rd=new Random();
        nbCities=N;
        graph=new int[N][N];
        for (int i = 0; i < nbCities; i++) {
            graph[i][i]=Integer.MAX_VALUE;
            for (int j = 0; j < nbCities; j++)
            {
                if(i!=j)
                    graph[i][j]=1+rd.nextInt(10);
                //graph[j][i]=graph[i][j];
            }
        }
    }
    public static void showGraph(int[][] graph) {
        for (int i = 0; i < nbCities; i++) {

            for (int j = 0; j < nbCities; j++) {
                System.out.print(graph[i][j] + "\t");
            }
            System.out.println();
        }
    }
    private static int min(int[] T)
    {
        int min=T[0];
        for (int x:T) {
            if(x<min)
                min=x;
        }
        return min;
    }
    private static int[] getColumn(int[][] M, int columnIndex) {
        return IntStream.range(0,M.length)
                .map(x->M[x][columnIndex]).toArray();
    }
    //or more simply

//    public static int[] getColumn(int[][] M, int columnIndex) {
//        int[] column = new int[M.length];
//        for (int i = 0; i < M.length; i++) {
//            column[i]=M[i][columnIndex];
//        }
//        return column;
//    }
    public static int reduce(int[][] M)
    {
        int reductionCost=0;
        for (int[] row: M) {
            int m=min(row);
            if(m!=Integer.MAX_VALUE) {
                reduceArray(row, m);
                reductionCost += m;
            }
        }
//        System.out.println("Reduction Rows Result :");
//        showGraph();
        for (int i = 0; i <nbCities ; i++) {
            int[] column=getColumn(M,i);
            int m=min(column);
            if(m!=Integer.MAX_VALUE) {
                for (int j = 0; j < nbCities; j++) {
                    if (M[j][i] != Integer.MAX_VALUE)
                        M[j][i] -= m;
                }
                reductionCost += m;
            }
        }
//        System.out.println("Reduction Columns Result :");
//        showGraph();
        return reductionCost;
    }

    private static void reduceArray(int[] T,int min) {
        for (int i = 0; i < T.length; i++) {
            if(T[i]!=Integer.MAX_VALUE)
                T[i]-=min;
        }
    }
    public static Node getMinLeaf(Queue<Node> leafQueue)
    {
        Iterator<Node> it = leafQueue.iterator();
        System.out.println("size = "+leafQueue.size());
        Node min=it.next();

        while(it.hasNext())
        {
            Node n=it.next();//        System.out.println("identique(n,parent)"+(n.getReducedMatrix()==parent.getReducedMatrix()));

            if(n.compareTo(min)<0)
                min=n;
        }
        return min;
    }
    //loop the reduce Matrix and uses the courantWay of the given Node to find its children.
    public static List<Node> getPossibleChildrenNodes(Node n)
    {
        List<Node> lstChildren=new ArrayList<>();
        for (int i = 0; i <nbCities ; i++) {
            if(!n.getCurrentWay().contains(i+1))//&& n.getReducedMatrix()[n.getCityNumber()-1][i-1]!=Integer.MAX_VALUE)
            {
                Node node=new Node();
                node.setCityNumber(i+1);
                List<Integer> nodeWay=new ArrayList<>(n.getCurrentWay());
                nodeWay.add(i+1);
                node.setCurrentWay(nodeWay);
                lstChildren.add(node);
            }
        }
        return lstChildren;
    }
    public static void setNodeCost(@NotNull Node n, @NotNull Node parent)
    {
        //pretreatment
        n.setReducedMatrix(parent.getReducedMatrix().clone());
        int[][] nReducedMatrix=n.getReducedMatrix();
        for (int j = 0; j <nbCities ; j++) {
            nReducedMatrix[parent.getCityNumber()-1][j]=Integer.MAX_VALUE;
            nReducedMatrix[j][n.getCityNumber()-1]=Integer.MAX_VALUE;
        }
        nReducedMatrix[n.getCityNumber()-1][startCity-1]=Integer.MAX_VALUE;

        //reduce the node n matrix and set the cost of n
        int x=reduce(nReducedMatrix);
       n.setCost(x+parent.getCost()+graph[parent.getCityNumber()-1][n.getCityNumber()-1]);
    }
    public static void resolveTSP(int start)
    {

    }

}
