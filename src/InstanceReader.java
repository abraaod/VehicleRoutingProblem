import java.io.*;
import java.util.Arrays;

public class InstanceReader {

    Graph loadGraph(String path){
        File file = new File(path);
        Graph graph = new Graph();

        int dimension = 0;

        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while((st = br.readLine()) != null){
                if(i < 6){
                    String [] sp = st.split(" : ");
                    if(sp[0].equals("NAME")){
                        graph.setName(sp[1]);
                    } else if(sp[0].equals("DIMENSION")){
                        dimension = Integer.valueOf(sp[1]);
                        graph.setDimension(dimension);
                    } else if(sp[0].equals("CAPACITY")){
                        graph.setCapacity(Integer.valueOf(sp[1]));
                    }
                } else if (i > 6 && i < dimension + 7){
                    //System.out.println(st);
                    String [] coord = st.split(" ");
                    Node e =  new Node();
                    e.setCoord_x(Integer.valueOf(coord[2]));
                    e.setCoord_y(Integer.valueOf(coord[3]));
                    graph.nodes.add(e);
                } else if(i > dimension + 7 && i < 2*dimension + 8){
                    String [] demand = st.split(" ");
                    Node aux = graph.nodes.get(Integer.valueOf(demand[0]) - 1);
                    aux.setDemand(Integer.valueOf(demand[1]));
                    graph.nodes.set(Integer.valueOf(demand[0]) - 1, aux);
                } else if (i == 2 * dimension + 9){
                    String [] depot = st.split(" ");
                    graph.setDeposit(Integer.valueOf(depot[1]));
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }
}
