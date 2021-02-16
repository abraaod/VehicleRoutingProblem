public class Edge {

    int a;
    int b;
    float weight;

    public Edge() {
    }

    public Edge(int a, int b, float weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
