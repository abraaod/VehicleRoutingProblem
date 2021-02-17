import java.util.ArrayList;
import java.util.List;

public class Graph {

    String name;

    int capacity;

    int dimension;

    int deposit;

    List<Node> nodes;

    List<Double> matrix;

    public Graph(){
        nodes = new ArrayList<>();
        matrix = new ArrayList<>();
        //the first element is always the depot and it has -1 distance
        matrix.add(-1.0);
    }

    public Graph(String name, int capacity, int dimension, int deposit, List<Node> nodes) {
        this.name = name;
        this.capacity = capacity;
        this.dimension = dimension;
        this.deposit = deposit;
        this.nodes = nodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", dimension=" + dimension +
                ", deposit=" + deposit +
                ", nodes=" + nodes +
                ", matrix=" + matrix +
                '}';
    }

    public Double distance(Node a, Node b){
        return Math.sqrt(Math.pow(a.coord_x - b.coord_x, 2) + Math.pow(a.coord_y - b.coord_y, 2));
    }
}
