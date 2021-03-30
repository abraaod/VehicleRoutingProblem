import java.util.*;

public class VRP {

    private Double min_cost = Double.MAX_VALUE;

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

    public void findSolutionBacktracking(Graph graph){

        Long start = System.currentTimeMillis();
        //Inicializa listas para lookup
        LinkedHashSet<Integer> visited = new LinkedHashSet<>();

        LinkedHashSet<Integer> elements = new LinkedHashSet<>();
        HashSet<Integer> added = new HashSet<>();

        Double demand = 0.0;
        Double totalCost = 0.0;

        //Aplica op algoritmo de backtraacking para todos os nós ou até que todos estejam em uma rota
        for(int i = 1; i < graph.dimension; i++){
            elements = backtracking(graph, visited, i, demand, elements, added);

            System.out.println("Elementos atuais: " + elements);
            System.out.println("Cost: " + min_cost);
            totalCost += min_cost;
            min_cost = Double.MAX_VALUE;
            demand = 0.0;

            visited.clear();
            visited.addAll(elements);
            added.addAll(elements);

            //Quebra o laço se todos os nós já estiverem em suas rotas
            if(added.size() + 1 == graph.dimension){
                break;
            }
        }
        Long end = (System.currentTimeMillis() - start);
        System.out.println(end);
        System.out.println(totalCost);

    }

    public LinkedHashSet<Integer> backtracking(Graph graph, LinkedHashSet<Integer> visited, int currentPos, Double demand, LinkedHashSet<Integer> elements, HashSet<Integer> added){

        //Verifica se a restrição de capacidade é quebrada ou se todos os nós foram visitados
        if(demand + graph.nodes.get(currentPos).demand > graph.capacity || visited.size()+1 == graph.dimension){

            Double actualCost = 0.0;
            int actualNode = 0;

            if(visited.size()+1 < graph.dimension){
                visited.remove(currentPos);
            }

            LinkedHashSet<Integer> aux = new LinkedHashSet<>(visited);
            aux.removeAll(added);

            for(Integer node : aux){
                actualCost += graph.distance(graph.nodes.get(actualNode), graph.nodes.get(node));
                actualNode = node;
            }

            actualCost += graph.distance(graph.nodes.get(actualNode), graph.nodes.get(0));
            //Verifica se a rota encontrada é a menor, se sim atualiza
            if(actualCost < min_cost){
                min_cost = actualCost;
                return aux;
            }
            else {
                return aux;
            }
        } else{
            demand += graph.nodes.get(currentPos).demand;
            visited.add(currentPos);
        }

        //Algoritmo de backtracking
        for(int i = 1; i < graph.dimension; i++){
            if(!visited.contains(i)){
                visited.add(i);
                elements = backtracking(graph, visited, i, demand, elements, added);
                visited.remove(i);
            }
        }

        return elements;
    }
    //Método para cálculo dos savings
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

    //Método para gerar as possíveis rotas. O número de rotas geradas é igual ao número de caminhões necessários.
    private Routes generateRoutes(List<Edge> pq, Graph graph){

        Routes routes = new Routes();
        //Roda enquanto houverem elemento na lista
        while(!pq.isEmpty()){
            Edge e = pq.remove(0);
            //Testa se os nós não pertencem a nenhuma rota
            if(routes.findRoute(e.a) == -1 && routes.findRoute(e.b) == -1) {
                Node a = graph.nodes.get(e.a);
                Node b = graph.nodes.get(e.b);
                double demand = a.demand + b.demand;
                List<Integer> list = new ArrayList<>();
                //Tenta combinar os dois nós em uma única rota, senão cria rotas para cada um dos nós
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
                //Testa se os nós estão em rotas diferentes ou se há um deles que ainda não possui rota
            } else if (!routes.sameRoute(e.a, e.b)){
                int i_a = routes.findRoute(e.a);
                int i_b = routes.findRoute(e.b);
                //Verifica se algum dos nós ainda não tem rota e adiciona o mesmo a uma
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
                //Tenta unificar as rotas cujos dois nós fazem parte
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