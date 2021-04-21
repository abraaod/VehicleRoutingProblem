import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {

        InstanceReader instanceReader = new InstanceReader();
        Graph graph = instanceReader.loadGraph(args[0]);
        //int dimension = graph.dimension;

        VRP vrp = new VRP();
        //vrp.findSolutionCW(graph);
        vrp.findSolutionGRASPExperiment(graph);
        System.out.println("\n");
        //vrp.findSolutionBacktracking(graph);
        Double maxCost = Double.MIN_VALUE, minCost = Double.MAX_VALUE, totalCost = 0.0;
        long minTime = Long.MAX_VALUE, maxTime = Long.MIN_VALUE, totalTime = 0L;

        List<Double> mediaCusto = new ArrayList<>();
        List<Long> mediaTempo = new ArrayList<>();

        for(int i = 0; i < 1000; i++){
            Long start = System.currentTimeMillis();
            Double cost =vrp.findSolutionGRASP(graph);
            Long end = System.currentTimeMillis() - start;

            if(cost < minCost)
                minCost = cost;
            if(cost > maxCost)
                maxCost = cost;

            if(end < minTime)
                minTime = end;
            if(end > maxTime)
                maxTime = end;

            mediaCusto.add(cost);
            mediaTempo.add(end);

        }

        System.out.println("Média de custo de: ");
        Double desvio = desvioPadraoCusto(mediaCusto);
        System.out.println("Com desvio de: " + desvio);

        System.out.println("Média de tempo de: ");
        desvio = desvioPadrao(mediaTempo);
        System.out.println("Com desvio de: " + desvio);

        System.out.println("Menor tempo de: " + minTime + " e maior tempo de: " + maxTime);
        System.out.println("Menor custo de: " + minCost + " e maior custo de: " + maxCost);
        System.out.println("\n");
    }

    public static double desvioPadraoCusto(List<Double> numeros) {
        double media = numeros.stream().mapToInt(Double::intValue).average().orElse(0);
        double mediaDesvios = numeros.stream().mapToDouble(i -> Math.pow(i - media, 2)).average().orElse(0);
        System.out.println(media);
        return Math.sqrt(mediaDesvios);
    }

    public static double desvioPadrao(List<Long> numeros) {
        double media = numeros.stream().mapToInt(Long::intValue).average().orElse(0);
        double mediaDesvios = numeros.stream().mapToDouble(i -> Math.pow(i - media, 2)).average().orElse(0);
        System.out.println(media + " (ms)");
        return Math.sqrt(mediaDesvios);
    }
}
