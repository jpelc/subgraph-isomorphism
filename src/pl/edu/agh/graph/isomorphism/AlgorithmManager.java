package pl.edu.agh.graph.isomorphism;

import org.apache.commons.math3.genetics.*;
import pl.edu.agh.graph.GraphGenerator;
import pl.edu.agh.graph.isomorphism.algorithm.FitnessStats;
import pl.edu.agh.graph.isomorphism.algorithm.SIPChromosome;
import pl.edu.agh.graph.isomorphism.algorithm.SIPGeneticAlgorithm;
import pl.edu.agh.graph.isomorphism.algorithm.SIPPopulation;
import pl.edu.agh.math.Chart;
import pl.edu.agh.math.KnowingTheTime;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Klasa zarządzająca działaniem całego algorytmu, RUNNABLE
 */
public class AlgorithmManager implements Runnable, KnowingTheTime {

    //algorytm
    private SIPGeneticAlgorithm ga;
    //populacja początkowa
    private Population population;
    //warunek stopu
    private StoppingCondition stoppingCondition;
    //rozmiar populacji
    private int populationSize;
    //rozmiar części chromosomu, którą bierzemy pod uwagę przy liczeniu wartości 
    //funkcji fitness (ilość wierzchołków w mniejszym grafie)
    private int chromosomeSize;
    //rozmiar dużego grafu
    private int biggerGraphSize;
    //najlepsza możliwa wartość funkcji fitness
    private double bestPossibleFitnessValue;
    //obiekt klasy przechowującej informację o wartościach funkcji fitness
    private FitnessStats fitnessStats;
    //obiekt reprezentujący główne okno programu
    private Viewer viewer;
    //obiekt wykresu
    private Chart chart;
    //czas rozpoczęcia algorytmu
    private long startTime;

    public AlgorithmManager(CrossoverPolicy crossoverPolicy, double crossoverProbability,
                            MutationPolicy mutationPolicy, double mutationProbability, SelectionPolicy selectionPolicy,
                            StoppingCondition stoppingCondition, int populationSize, double f1weight, double f2weight,
                            boolean chartDrawing, Viewer viewer) {

        this.stoppingCondition = stoppingCondition;
        this.populationSize = populationSize;
        this.chromosomeSize = GraphGenerator.getSmaller().getVertexCount();
        this.biggerGraphSize = GraphGenerator.getBigger().getVertexCount();
        this.viewer = viewer;
        this.bestPossibleFitnessValue = f1weight*GraphGenerator.getSmaller().getEdgeCount() +
                f2weight*GraphGenerator.getSmaller().getVertexCount();
        this.startTime = System.currentTimeMillis();
        this.fitnessStats = new FitnessStats(populationSize, f1weight, f2weight, bestPossibleFitnessValue);
        if(chartDrawing)
            chart = new Chart("Funkcja fitness", "Pokolenie", "Wartość");
        else
            chart = null;
        this.ga = new SIPGeneticAlgorithm(crossoverPolicy, crossoverProbability, mutationPolicy, mutationProbability,
                selectionPolicy, viewer, fitnessStats, chart, startTime, this);
        viewer.updateLog("-----------------------------------------------------------------------------------");
        viewer.updateLog(getTime() + "Start algorytmu, najlepsza możliwa wartość fitness: " +
                fitnessStats.getBestPossibleFitnessValue());
    }

    /**
     * Metoda zwracająca początkową, losową populację
     * @return początkowa populacja
     */
    private Population getInitialPopulation() {
        viewer.updateLog(getTime() + "Generowanie losowej populacji, rozmiar: " + populationSize);
        LinkedList<Integer> v = new LinkedList<Integer>();
        LinkedList<Chromosome> chromList = new LinkedList<Chromosome>();
        for (int i = 0; i < biggerGraphSize; i++)
            v.add(i);
        for(int i = 0; i < populationSize; i++)
            chromList.add(new SIPChromosome(generateRandomChromosome(v, biggerGraphSize), fitnessStats));
        SIPPopulation pop = new SIPPopulation(chromList, populationSize);
        viewer.updateLog(getTime() + "Zakończono generowanie losowej populacji");
        return pop;
    }

    /**
     * Metoda zwracająca czas działania algorytmu w odpowiednim formacie
     * @return caas działania algorytmu
     */
    public String getTime() {
        int time = (int)((System.currentTimeMillis() - startTime)/1000);
        int min = time / 60;
        int sec = time % 60;
        String m = (min < 10) ? new String("0"+min) : new String("" + min);
        String s = (sec < 10) ? new String("0"+sec) : new String("" + sec);
        return "[" + m + ":" + s + "] ";
    }

    /**
     * Generator losowego chromosomu, bez powtarzania wartości
     * @param values możliwe wartości do wykorzystania (czyli liczby od 0 do 
     * ilości wierzchołków większego grafu
     * @param howMany wielkość chromosomu, wielkość dużego grafu
     * @return chromosom
     */
    private List<Integer> generateRandomChromosome(LinkedList<Integer> values, int howMany) {
        LinkedList<Integer> temp = new LinkedList<Integer>(values);
        List<Integer> list = new LinkedList<Integer>();
        Random random = new Random();
        for (int i = 0; i < howMany; i++)
            list.add(temp.remove(random.nextInt(temp.size())));
        return list;
    }

    /**
     * Metoda używana do wywołania metody kolorującej wierzchołki, oraz 
     * metody niszczącej wątek. Stosowana w przypadku znalezienia podgrafu przed 
     * wystąpieniem warunku stopu.
     * @param bestChromosome chromosom reprezentujący idealny podgraf
     */
    public void colorSubgraphAndDie(Chromosome bestChromosome) {
        SIPChromosome best = (SIPChromosome)bestChromosome;
        viewer.colorSubGraph(Arrays.copyOfRange(best.getChromosome(), 0, chromosomeSize));
        int[] isoVertices = best.getChromosome();
        for (int i = 0; i < GraphGenerator.getSmaller().getVertexCount(); i++) {
            viewer.updateLog("V[" + i +"] <---> V[" + isoVertices[i] + "]");
        }
        viewer.updateLog("-----------------------------------------------------------------------------------");
        viewer.killThread();
    }

    @Override
    public void run() {
        population = getInitialPopulation();
        Population finalPopulation = ga.evolve(population, stoppingCondition);

    }
    
    

}
