package pl.edu.agh.graph;

/**
 * Klasa reprezentująca wierzchołek
 */
public class Vertex {

    private String name;
    private int id;

    public Vertex(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return name;
    }
}
