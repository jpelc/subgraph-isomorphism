package pl.edu.agh.graph.isomorphism.algorithm;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import pl.edu.agh.graph.Edge;
import pl.edu.agh.graph.GraphGenerator;
import pl.edu.agh.graph.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca chromosom. W klasie, po której dziedziczy znajduje się
 * lista Integerów.
 */
public class SIPChromosome extends AbstractListChromosome<Integer> {

    //referencja do obiektu klasy przechowującej informacje o FF
    private FitnessStats fitnessStats;
    //graf mniejszy
    private Graph<Vertex, Edge> smaller;
    //graf większy
    private Graph<Vertex, Edge> bigger;
    //wierzchołki grafu mniejszego
    private ArrayList<Vertex> smallerVertex;
    //wierzchołki grafu większego
    private ArrayList<Vertex> biggerVertex;

    //blok inicjalizacyjny, wykonuje się podczas tworzenia nowego obiektu, zaraz
    //po konstruktorze klasy nadrzędnej
    {
        this.smaller = GraphGenerator.getSmaller();
        this.bigger = GraphGenerator.getBigger();
        this.smallerVertex = GraphGenerator.getSmallerVertex();
        this.biggerVertex = GraphGenerator.getBiggerVertex();
    }

    public SIPChromosome(final Integer[] representation, FitnessStats fitnessStats) {
        super(representation);
        this.fitnessStats = fitnessStats;
    }

    public SIPChromosome(final List<Integer> representation, FitnessStats fitnessStats) {
        super(representation);
        this.fitnessStats = fitnessStats;
    }


    /**
     * Funkcja klasy nadrzędnej, którą trzeba było zaimplementować, dla nas nieistotna
     * @param integers
     * @throws InvalidRepresentationException
     */
    @Override
    protected void checkValidity(List<Integer> integers) throws InvalidRepresentationException {
        //not important
    }

    /**
     * Tworzy nowy chromosom o podanej reprezentacji
     * @param chromosomeRepresentation chromosom - lista integerów
     * @return nowy chromosom
     */
    @Override
    public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> chromosomeRepresentation) {
        return new SIPChromosome(chromosomeRepresentation, fitnessStats);
    }

    /**
     * Funkcja fitness
     * @return wartość funkcji fitness dla tego chromosomu
     */
    @Override
    public double fitness() {
        int fitnessValuef1 = 0;
        ArrayList<Edge> edges = new ArrayList<Edge>(smaller.getEdges());
        List<Integer> chromosome = getRepresentation();
        if(bigger instanceof UndirectedSparseGraph) {
            for(Edge e: edges) {
                if( bigger.isNeighbor(biggerVertex.get(chromosome.get(e.getV1())), biggerVertex.get(chromosome.get(e.getV2()))) )
                    fitnessValuef1 += 1;
            }
        } else {
            for(Edge e: edges) {
                if( bigger.isSuccessor(biggerVertex.get(chromosome.get(e.getV1())), biggerVertex.get(chromosome.get(e.getV2()))) )
                    fitnessValuef1 += 1;
            }
        }

        int fitnessValuef2 = 0;
        for(Vertex v: smallerVertex) {
            if(smaller.inDegree(v) <= bigger.inDegree(biggerVertex.get(chromosome.get(v.getId()))) ||
                    smaller.outDegree(v) <= bigger.outDegree(biggerVertex.get(chromosome.get(v.getId()))))
                fitnessValuef2 += 1;
        }

        return fitnessStats.getF1weight()*fitnessValuef1 + fitnessStats.getF2weight()*fitnessValuef2;
    }

    /**
     * Zwraca chromosom w postaci tablicy
     * @return chromosom
     */
    public int[] getChromosome() {
        List<Integer> list = getRepresentation();
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * Zwraca chromosom w postaci listy Integerów
     * @return chromosom
     */
    public List<Integer> getChromosomeRepresentation() {
        return super.getRepresentation();
    }

    /**
     * Zwraca referencję do obiektu klasy przechowującej informacje o FF
     * @return
     */
    public FitnessStats getFitnessStatsRef() {
        return fitnessStats;
    }
}
