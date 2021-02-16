public class Node {

    float coord_x;
    float coord_y;
    float demand;

    public Node(){

    }

    public Node(float coord_x, float coord_y, float demand) {
        this.coord_x = coord_x;
        this.coord_y = coord_y;
        this.demand = demand;
    }

    public float getCoord_x() {
        return coord_x;
    }

    public void setCoord_x(float coord_x) {
        this.coord_x = coord_x;
    }

    public float getCoord_y() {
        return coord_y;
    }

    public void setCoord_y(float coord_y) {
        this.coord_y = coord_y;
    }

    public float getDemand() {
        return demand;
    }

    public void setDemand(float demand) {
        this.demand = demand;
    }

    @Override
    public String toString() {
        return "Node{" +
                "coord_x=" + coord_x +
                ", coord_y=" + coord_y +
                ", demand=" + demand +
                '}';
    }
}