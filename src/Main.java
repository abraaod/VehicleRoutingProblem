import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println(args[0]);
        InstanceReader instanceReader = new InstanceReader();
        instanceReader.loadGraph(args[0]);
    }
}
