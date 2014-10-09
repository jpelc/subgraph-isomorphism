package pl.edu.agh.graph;

/**
 * Klasa reprezentująca krawędź. v1 i v2 to numery wierzchołków, które łączy ta krawędź.
 * @author Jakub
 */
public class Edge {

    private int v1;
    private int v2;

    public Edge(int v1, int v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public int getV1() {
        return v1;
    }

    public void setV1(int v1) {
        this.v1 = v1;
    }

    public int getV2() {
        return v2;
    }

    public void setV2(int v2) {
        this.v2 = v2;
    }

    public String toString() {
        return v1 + " <---> " + v2;
    }

}
