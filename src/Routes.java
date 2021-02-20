import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Routes {

    List<Route> routes;

    public Routes() {
        routes = new ArrayList<>();
    }

    public Routes(List<Route> routes) {
        this.routes = routes;
    }

    public int size(){
        return routes.size();
    }

    public double getDemand(int i){
        if(i >= routes.size())
            return 0.0;
        return routes.get(i).demand;
    }
    public int lastIndex(){
        return routes.size() - 1;
    }

    public void addRoute(List<Integer> list, double demand){
        Route route = new Route(list, demand);
        routes.add(route);
    }

    public void updateRoute(int index, List<Integer> list, double demand, double distance){
        Route route = routes.get(index);
        for(Integer e : list){
            route.hash.add(e);
            route.list.add(e);
        }

        route.demand += demand;
    }

    public boolean sameRoute(int a, int b){
        int index_a = -1, index_b = -1;
        for(int i = 0; i < routes.size(); i++){
            if(routes.get(i).hash.contains(a))
                index_a = i;
            if(routes.get(i).hash.contains(b))
                index_b = i;
        }

        if(index_a == index_b && index_a != -1 && index_b != -1)
            return true;
        return false;
    }

    public int findRoute(int a){
        for(int i = 0; i < routes.size(); i++){
            if(routes.get(i).hash.contains(a)){
                return i;
            }
        }
        return -1;
    }

    public void unifyRoutes(int a, int b){
        Route route_a = routes.get(a);
        Route route_b = routes.get(b);
        double demand = route_a.demand + route_b.demand;
        Set<Integer> hash = new HashSet<>();
        List<Integer> nodes = new ArrayList<>();

        for(Integer e : route_a.list){
            nodes.add(e);
            hash.add(e);
        }

        for(Integer e : route_b.list){
            nodes.add(e);
            hash.add(e);
        }
        //System.out.println("Tamanho atual: " + routes.size());
        routes.remove(route_a);
        routes.remove(route_b);
        routes.add(new Route(demand, hash, nodes));
    }

    @Override
    public String toString() {
        return "Routes{" +
                "routes=\n" + routes +
                '}';
    }
}

class Route{

    double demand;
    Set<Integer> hash = new HashSet<>();
    List<Integer> list = new ArrayList<>();

    public Route(List<Integer> list, double demand) {
        for(Integer e : list){
            this.hash.add(e);
            this.list.add(e);
        }
        this.demand = demand;
    }

    public Route(double demand, Set<Integer> hash, List<Integer> list) {
        this.demand = demand;
        this.hash = hash;
        this.list = list;
    }

    @Override
    public String toString() {
        return "Route{" +
                "demand=" + demand +
                ", list=" + list +
                '}' + "\n";
    }
}
