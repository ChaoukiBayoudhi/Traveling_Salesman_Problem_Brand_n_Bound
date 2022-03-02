import lombok.*;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Node implements Comparable<Node> {

    private int cityNumber;
    @EqualsAndHashCode.Include
    private int cost;
    private int[][] reducedMatrix;
    private List<Integer> courantWay=new ArrayList<>();

    @Override
    public int compareTo(Node o) {
        if(o ==null)
            return 1;
        return this.cost-o.cost;
    }

}
