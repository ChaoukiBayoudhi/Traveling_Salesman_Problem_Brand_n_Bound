import java.util.*;

public class TspBranchNBound {
    public static int[][] graph;
    public static int nbCities;
    protected static long cost=0L;
    public static int startCity;
    public static List<Integer> finalCycle;
    public static Queue<Node> leafQueue=new LinkedList<>();
    //generates a graph matrix randomly
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
                    graph[i][j]=1+rd.nextInt(100);
                //graph[j][i]=graph[i][j];
            }
        }
    }
    //prints the graph matrix
    public static void showGraph() {
        for (int i = 0; i < nbCities; i++) {

            for (int j = 0; j < nbCities; j++) {
                System.out.print(graph[i][j] + "\t");
            }
            System.out.println();
        }
    }
    //find the minimum of the given array
    public static int min(int[] T)
    {
        int min=Integer.MAX_VALUE;
        for (int x:T) {
            if(x<min)
                min=x;
        }
        if(min==Integer.MAX_VALUE)
            min=0;
        return min;
    }
    //    public static int[] getColumn(int columnIndex) {
//        return IntStream.range(0,graph.length)
//                .map(x->graph[x][columnIndex]).toArray();
//    }
    //returns the column of given index from the matrix M
    public static int[] getColumn(int[][] M, int columnIndex) {
        int[] column = new int[M.length];
        for (int i = 0; i < M.length; i++) {
            column[i]=M[i][columnIndex];
        }
        return column;
    }
    //loop matrix rows and the columns and substract the min from each case.
    //returns the reduction's cost
    public static int reduce(int[][] M)
    {
        int reductionCost=0;
        for (int[] row: M) {
            int m=min(row);
            reduceArray(row,m);
            reductionCost+=m;
        }

        for (int i = 0; i <nbCities ; i++) {
            int[] column=getColumn(M,i);
            int m=min(column);
            for (int j = 0; j < nbCities; j++) {
                if(M[j][i]!=Integer.MAX_VALUE)
                    M[j][i]-=m;
            }
            reductionCost+=m;
        }

        return reductionCost;
    }
    //reduces one row or one column
    private static void reduceArray(int[] T,int min) {
        for (int i = 0; i < T.length; i++) {
            if(T[i]!=Integer.MAX_VALUE)
                T[i]-=min;
        }
    }
    //returns the node with the minimal cost
    public static Node getMinLeaf(Queue<Node> leafQueue)
    {
        Iterator<Node> it=leafQueue.iterator();
        Node minNode = it.next();
        while(it.hasNext())
        {
            Node n=it.next();
            if(n.compareTo(minNode)<0)
                minNode =n;
        }
        return minNode;
    }
    //returns possible next nodes for the node n.
    public static List<Node> getPossibleChildrenNodes(Node n){
        List<Node> lstChilderen=new ArrayList<>();
        for (int i = 1; i <=nbCities ; i++) {//loop cities numbers
            if(!n.getCurrentWay().contains(i))  //if the cities is not aleady visited
            {
                Node node=new Node();
                node.setCityNumber(i);
                List<Integer> ncurrentWay=n.getCurrentWay();
                //node.setCurrentWay(new ArrayList<>(n.getCurrentWay()));
                //node.getCurrentWay().add(i);
                //or
                List<Integer> nodeCurrentWay=new ArrayList<>(n.getCurrentWay());
                nodeCurrentWay.add(i);
                node.setCurrentWay(nodeCurrentWay);
                node.setReducedMatrix(copyGraph(n.getReducedMatrix()));
                lstChilderen.add(node);
            }
        }
        return lstChilderen;
    }
    public static void setNodeCost(Node n, Node parent){
        //pretreatment
        int[][] M=n.getReducedMatrix();
        for (int i = 0; i < nbCities; i++) {
            M[parent.getCityNumber()-1][i]=Integer.MAX_VALUE;
            M[i][n.getCityNumber()-1]=Integer.MAX_VALUE;
        }
        M[n.getCityNumber()-1][startCity-1]=Integer.MAX_VALUE;

        int reductionCost=reduce(M);
        n.setCost(reductionCost+ parent.getCost()+graph[parent.getCityNumber()-1][n.getCityNumber()-1]);
    }
    public static void resolveTSP(int start){
        startCity=start;
        //create the first node associated to the start city
        Node currentNode =new Node();
        currentNode.setCityNumber(startCity);
        currentNode.getCurrentWay().add(startCity);
        int reductionCost=reduce(graph);
        System.out.println("Reduced Graph : ");
        showGraph();;
        currentNode.setCost(reductionCost);
        int[][] graphCopy=copyGraph(graph);
        currentNode.setReducedMatrix(graphCopy);
        currentNode.setReadyNode(true);
        System.out.println("first reduction cost = "+reductionCost);
        leafQueue.offer(currentNode);//add the start node to the leaf's queue
        while(!leafQueue.isEmpty()&&currentNode.getCurrentWay().size()<nbCities)
        {
            List<Node> lstChilderen= getPossibleChildrenNodes(currentNode);
            if(!lstChilderen.isEmpty()) {
                for(Node x:lstChilderen)
                {
                    if(!x.isReadyNode()) {
                        setNodeCost(x, currentNode);
                        x.setReadyNode(true);
                    }
                }

                leafQueue.remove(currentNode);
                leafQueue.addAll(lstChilderen);//add all children as leaf to the queue
                currentNode=getMinLeaf(leafQueue);
            }
            cost=currentNode.getCost();
            finalCycle=new ArrayList<>(currentNode.getCurrentWay());
            finalCycle.add(startCity);
        }

    }

    private static int[][] copyGraph(int[][] M) {
        int[][] copy = new int[M.length][M.length];
        for (int i=0; i<M.length; i++)
            for (int j = 0; j < nbCities; j++)
                copy[i][j] = M[i][j];
        return copy;
    }
}
