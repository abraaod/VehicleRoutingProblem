import java.util.ArrayList;
import java.util.List;

public class ClarkWright {

    public void findSolutionCW(Graph graph){

        for(int j = 1; j < graph.dimension; j++){
            Double distanceToDepot = graph.distance(graph.nodes.get(0), graph.nodes.get(j));
            graph.matrix.add(distanceToDepot);
        }

        //System.out.println(graph);
        List<Edge> savings = calculateSavings(graph);
    }

    private List<Edge> calculateSavings(Graph graph){

        List<Edge>  savings = new ArrayList<>();
        for(int i = 1; i < graph.dimension; i++){
            for(int j = i + 1; j < graph.dimension; j++){
                Double saving =  graph.matrix.get(i) +  graph.matrix.get(j) - graph.distance(graph.nodes.get(i), graph.nodes.get(j));
                Edge e = new Edge(i, j, saving);
                savings.add(e);
            }
        }

        return savings;
    }
}
