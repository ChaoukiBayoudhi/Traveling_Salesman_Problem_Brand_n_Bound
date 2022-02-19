import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TspBranchNBound {
    public static int[][] graph;
    public static int nbCities;
    public static long cost=0L;
    public static int startCity;
    public static List<Integer> finalCycle=new ArrayList<>();

    public static void generateGraph(int N)
    {
        Random rd=new Random();
        nbCities=N;
        graph=new int[N][N];
        for (int i = 0; i < nbCities; i++) {
            graph[i][i]=Integer.MAX_VALUE;
            for (int j = 0; j < i; j++)
            {
                graph[i][j]=1+rd.nextInt(50);
                graph[j][i]=graph[i][j];
            }
        }
    }
    public static void showGraph() {
        for (int i = 0; i < nbCities; i++) {

            for (int j = 0; j < nbCities; j++) {
                System.out.print(graph[i][j] + "\t");
            }
            System.out.println();
        }
    }
    public static int min(int[] T)
    {
        int min=T[0];
        for (int x:T) {
            if(x<min)
                min=x;
        }
        return min;
    }
    public static int[] getColumns(int columnIndex) {
        return IntStream.range(0,graph.length)
                .map(x->graph[x][columnIndex]).toArray();
    }
    public static int reduce()
    {
        int reductionCost=0;
        for (int[] row:graph) {
            int m=min(row);
            reduceArray(row,m);
            reductionCost+=m;
        }
        for (int i = 0; i <nbCities ; i++) {
            int[] column=getColumns(i);
            int m=min(column);
            reduceArray(column,m);
            reductionCost+=m;
        }
        return reductionCost;
    }

    private static void reduceArray(int[] T,int min) {
        for (int i = 0; i < T.length; i++) {
            T[i]-=min;
        }
    }
}
