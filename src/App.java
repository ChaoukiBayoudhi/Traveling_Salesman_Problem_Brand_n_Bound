import java.util.Arrays;
import java.util.Scanner;

class App {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Number of cities ? ");
        int n=sc.nextInt();
        TspBranchNBound.generateGraph(n);
        TspBranchNBound.showGraph();
        //System.out.println(Arrays.toString(TspBranchNBound.getColumns(2)));
        int reductionCost=TspBranchNBound.reduce(TspBranchNBound.graph);
        System.out.println("Reduction Cost = "+reductionCost);
        System.out.println("initial reduced matrix :");
        TspBranchNBound.showGraph();
        System.out.println("Start City ? ");
        int start=sc.nextInt();
        //TspProblem.tspCost(start);
        System.out.println(TspBranchNBound.finalCycle);
        System.out.println("Cost = "+ TspBranchNBound.cost);

    }
}