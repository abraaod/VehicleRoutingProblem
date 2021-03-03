import java.util.*;

public class ClarkeWright {

    public void findSolutionCW(Graph graph){
        long start = System.currentTimeMillis();
        for(int j = 1; j < graph.dimension; j++){
            Double distanceToDepot = graph.distance(graph.nodes.get(0), graph.nodes.get(j));
            graph.matrix.add(distanceToDepot);
        }
        List<Edge> savings = calculateSavings(graph);
        Routes routes =  generateRoutes(savings, graph);
        long end = System.currentTimeMillis() - start;
        System.out.println(routes.toString());
        System.out.println(routes.getDistance(graph));
        System.out.println("Time(ms): " + end);
        System.out.println("Number of trucks: " + routes.size());
    }

    private List<Edge> calculateSavings(Graph graph){

        List<Edge> list = new ArrayList<>();

        for(int i = 1; i < graph.dimension; i++){
            for(int j = i + 1; j < graph.dimension; j++){
                Double saving =  graph.matrix.get(i) +  graph.matrix.get(j) - graph.distance(graph.nodes.get(i), graph.nodes.get(j));
                Edge e = new Edge(i, j, saving);
                list.add(e);
            }
        }

        Collections.sort(list, new EdgeComparator());
        return list;
    }

    private Routes generateRoutes(List<Edge> pq, Graph graph){

        Routes routes = new Routes();

        while(!pq.isEmpty()){
            Edge e = pq.remove(0);
            if(routes.findRoute(e.a) == -1 && routes.findRoute(e.b) == -1) {
                Node a = graph.nodes.get(e.a);
                Node b = graph.nodes.get(e.b);
                double demand = a.demand + b.demand;
                List<Integer> list = new ArrayList<>();
                if(demand <= graph.capacity){
                    list.add(e.a);
                    list.add(e.b);
                    routes.addRoute(list, demand);
                } else {
                    List<Integer> list_a = new ArrayList<>();
                    list_a.add(e.a);
                    routes.addRoute(list_a, a.demand);

                    List<Integer> list_b = new ArrayList<>();
                    list_b.add(e.a);
                    routes.addRoute(list_b, a.demand);
                }
            } else if (!routes.sameRoute(e.a, e.b)){
                int i_a = routes.findRoute(e.a);
                int i_b = routes.findRoute(e.b);

                if(i_a != -1 || i_b != -1){
                    if(i_a == -1){
                        List<Integer> list = new ArrayList<>();
                        list.add(e.a);
                        routes.addRoute(list, graph.nodes.get(e.a).demand);
                        i_a = routes.lastIndex();
                    }
                    if(i_b == -1){
                        List<Integer> list = new ArrayList<>();
                        list.add(e.b);
                        routes.addRoute(list, graph.nodes.get(e.b).demand);
                        i_b = routes.lastIndex();
                    }
                }
                if(routes.getDemand(i_a) + routes.getDemand(i_b) <= graph.capacity){
                    routes.unifyRoutes(i_a, i_b);
                }

            }
        }

        return routes;
    }

}

class EdgeComparator implements Comparator<Edge>{

    public int compare(Edge a, Edge b) {
        return Double.compare(b.weight,a.weight);
    }

}
