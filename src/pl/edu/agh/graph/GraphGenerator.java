package pl.edu.agh.graph;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Generator grafów
 */
public class GraphGenerator {

    //mały graf
    private static Graph<Vertex, Edge> smaller;
    //duży graf
    private static Graph<Vertex, Edge> bigger;
    //lista wierzchołków wygenerowana podczas tworzenia grafu
    private static LinkedList<Vertex> vertexList;
    //wierzchołki małego grafu
    private static LinkedList<Vertex> smallerVertex;
    //wierzchołki dużego grafu
    private static LinkedList<Vertex> biggerVertex;
    //zmienna stosowana to zabezpieczenia przed startem algorytmu bez wygenerowania grafu
    private static boolean isGenerated;

    public GraphGenerator() {
        isGenerated = false;
    }

    public boolean isGenerated() {
        return isGenerated;
    }

    public void generatePairOfUndirectedGraphs(int low, int high) {
        smaller = generateUndirectedGraph(low);
        smallerVertex = vertexList;
        bigger = generateUndirectedGraph(high);
        biggerVertex = vertexList;
        isGenerated = true;
    }

    public void generatePairOfDirectedGraphs(int low, int high) {
        smaller = generateDirectedGraph(low);
        smallerVertex = vertexList;
        bigger = generateDirectedGraph(high);
        biggerVertex = vertexList;
        isGenerated = true;

    }

    public static Graph<Vertex, Edge> getSmaller() {
        return smaller;
    }

    public static Graph<Vertex, Edge> getBigger() {
        return bigger;
    }

    private UndirectedSparseGraph<Vertex, Edge> generateUndirectedGraph(int vertexCount) {
        UndirectedSparseGraph<Vertex, Edge> g = new UndirectedSparseGraph<Vertex, Edge>();
        Random random = new Random();
        vertexList = new LinkedList<Vertex>();

        for(int i = 0; i < vertexCount; i++) {
            Vertex v = new Vertex("V[" + i + "]", i);
            vertexList.add(v);
            g.addVertex(v);
            if(g.getVertexCount() == 1) continue;
            if(g.getVertexCount() == 2) {
                g.addEdge(new Edge(1, 0), v, vertexList.get(0));
                continue;
            }
            
            int howManyTimes;
            if(i > 0.8*vertexCount)
                howManyTimes = 3;
            else if(i > 0.4*vertexCount)
                howManyTimes = 2;
            else
                howManyTimes = 1;

            for (int j = 0; j < howManyTimes; j++) {
                int whichOne = 0;
                do {
                    whichOne = random.nextInt(g.getVertexCount());
                } while(whichOne == i);
                g.addEdge(new Edge(i, vertexList.get(whichOne).getId()), v, vertexList.get(whichOne));
            }

        }

        return g;
    }
    
    private DirectedSparseGraph<Vertex, Edge> generateDirectedGraph(int vertexCount) {
        DirectedSparseGraph<Vertex, Edge> g = new DirectedSparseGraph<Vertex, Edge>();
        Random random = new Random();
        vertexList = new LinkedList<Vertex>();

        for(int i = 0; i < vertexCount; i++) {
            Vertex v = new Vertex("V[" + i + "]", i);
            vertexList.add(v);
            g.addVertex(v);
            if(g.getVertexCount() == 1) continue;
            if(g.getVertexCount() == 2) {
                g.addEdge(new Edge(1, 0), v, vertexList.get(0));
                continue;
            }

            int howManyTimes;
            if(i > 0.8*vertexCount)
                howManyTimes = 3;
            else if(i > 0.4*vertexCount)
                howManyTimes = 2;
            else
                howManyTimes = 1;

            for (int j = 0; j < howManyTimes; j++) {
                int whichOne = 0;
                do {
                    whichOne = random.nextInt(g.getVertexCount());
                } while(whichOne == i);
                g.addEdge(new Edge(i, vertexList.get(whichOne).getId()), v, vertexList.get(whichOne));
            }
        }

        return g;
    }

    public static ArrayList<Vertex> getSmallerVertex() {
        return new ArrayList<Vertex>(smallerVertex);
    }

    public static ArrayList<Vertex> getBiggerVertex() {
        return new ArrayList<Vertex>(biggerVertex);
    }
}
