import java.util.SortedSet;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {

        InstanceReader instanceReader = new InstanceReader();
        Graph graph = instanceReader.loadGraph(args[0]);
        //int dimension = graph.dimension;

        VRP vrp = new VRP();
        vrp.findSolutionCW(graph);
        vrp.findSolutionBacktracking(graph);

//        SortedSet<Integer> teste = new TreeSet<>();
//        teste.add(3);
//        teste.add(1);
//        teste.add(5);
//
//        System.out.println(teste);

    }
}
