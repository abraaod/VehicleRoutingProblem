public class Main {

    public static void main(String[] args) {

        InstanceReader instanceReader = new InstanceReader();
        Graph graph = instanceReader.loadGraph(args[0]);
        //int dimension = graph.dimension;

        ClarkeWright cw = new ClarkeWright();
        cw.findSolutionCW(graph);

    }
}
