public class Main {

    public static void main(String[] args) {

        InstanceReader instanceReader = new InstanceReader();
        Graph graph = instanceReader.loadGraph(args[0]);
        //int dimension = graph.dimension;

        VRP vrp = new VRP();
        vrp.findSolutionCW(graph);

    }
}
