package pl.edu.agh.graph.isomorphism.algorithm;

import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.genetics.*;
import org.apache.commons.math3.random.RandomGenerator;
import pl.edu.agh.graph.GraphGenerator;
import pl.edu.agh.graph.isomorphism.AlgorithmManager;
import pl.edu.agh.graph.isomorphism.Viewer;
import pl.edu.agh.math.Chart;
import pl.edu.agh.math.KnowingTheTime;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Klasa reprezentująca nasz algorytm genetyczny.
 */
public class SIPGeneticAlgorithm extends GeneticAlgorithm implements KnowingTheTime {

    private Viewer viewer;
    private FitnessStats fitnessStats;
    //czas startu algorytmu
    private long startTime;
    //numer iteracji
    private int iteration;
    //potrzebne do formatowania wyświetlanych liczb zmiennoprzecinkowych
    private DecimalFormat df;
    //wykres
    private Chart chart;
    //główna klasa zarządzająca działaniem całego algorytmu
    private AlgorithmManager algorithmManager;

    public SIPGeneticAlgorithm(CrossoverPolicy crossoverPolicy, double crossoverRate, MutationPolicy mutationPolicy,
                               double mutationRate, SelectionPolicy selectionPolicy, Viewer viewer, FitnessStats fitnessStats,
                               Chart chart, long startTime, AlgorithmManager algorithmManager)
                                throws OutOfRangeException {
        super(crossoverPolicy, crossoverRate, mutationPolicy, mutationRate, selectionPolicy);
        this.viewer = viewer;
        this.fitnessStats = fitnessStats;
        this.startTime = startTime;
        this.iteration = 0;
        this.df = new DecimalFormat("#.##");
        this.chart = chart;
        this.algorithmManager = algorithmManager;
    }

    /**
     * Rozpoczyna algorytm i zwraca wynik
     * @param initial populacja startowa
     * @param condition warunek stopu
     * @return populacja końcowa
     */
    @Override
    public Population evolve(Population initial, StoppingCondition condition) {
        viewer.updateLog(getTime() + "Rozpoczęcie ewolucji");
        Population finalPopulation = super.evolve(initial, condition);
        viewer.updateLog(getTime() + "Ewolucja zakończona, warunek zakończenia spełniony");

        Chromosome bestChromosome = finalPopulation.getFittestChromosome();

        SIPChromosome best = (SIPChromosome)bestChromosome;
        int[] isoVertices = Arrays.copyOfRange(best.getChromosome(), 0, GraphGenerator.getSmaller().getVertexCount());

        for (int i = 0; i < isoVertices.length; i++)
            viewer.updateLog("(A) V[" + i +"] <---> V[" + isoVertices[i] + "] (B)");
        viewer.updateLog("-----------------------------------------------------------------------------------");
        viewer.setButtonsState(true);
        return finalPopulation;
    }

    /**
     * Logika głównej części algorytmu
     * @param current populacja
     * @return nowa populacja
     */
    @Override
    public Population nextGeneration(Population current) {
        Population nextGeneration = current.nextGeneration();

        RandomGenerator randGen = getRandomGenerator();

        while (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit()) {
            // select parent chromosomes
            ChromosomePair pair = getSelectionPolicy().select(current);

            // crossover?
            if (randGen.nextDouble() < getCrossoverRate()) {
                // apply crossover policy to create two offspring
                pair = getCrossoverPolicy().crossover(pair.getFirst(), pair.getSecond());
            }

            // mutation?
            if (randGen.nextDouble() < getMutationRate()) {
                // apply mutation policy to the chromosomes
                pair = new ChromosomePair(
                        getMutationPolicy().mutate(pair.getFirst()),
                        getMutationPolicy().mutate(pair.getSecond()));
            }

            // add the first chromosome to the population
            nextGeneration.addChromosome(pair.getFirst());
            // is there still a place for the second chromosome?
            if (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit()) {
                // add the second chromosome to the population
                nextGeneration.addChromosome(pair.getSecond());
            }
        }
        
        //wyświetlanie informacji w logu
        iteration++;
        ((SIPPopulation)nextGeneration).updateFitnessStats();
        SIPChromosome chrom = (SIPChromosome)nextGeneration.getFittestChromosome();
        viewer.colorSubGraph(Arrays.copyOfRange(chrom.getChromosome(), 0, GraphGenerator.getSmaller().getVertexCount()));
        viewer.updateLog(getTime() + "FITNESS(" + iteration + "):  max = " + df.format(fitnessStats.getBestFitnessValue()) +
        "  |  min = " + df.format(fitnessStats.getWorstFitnessValue()) + "  |  średnia = " +
                df.format(fitnessStats.getAverageFitnessValue()));
        if(chart != null)
            chart.updateChart(fitnessStats.getBestFitnessValue(), fitnessStats.getAverageFitnessValue(), fitnessStats.getWorstFitnessValue());
        
        //sprawdzanie czy algorytm znalazł idealny podgraf, jeśli tak to niszczymy wątek
        if(fitnessStats.getBestFitnessValue() == fitnessStats.getBestPossibleFitnessValue()) {
            viewer.updateLog("Znaleziono podgraf. Algorytm zostanie zatrzymany.");
            algorithmManager.colorSubgraphAndDie(nextGeneration.getFittestChromosome());
        }
        return nextGeneration;
    }

    /**
     * Zwraca czas działania algorytmu w odpowiednim formacie
     * @return czas działania algorytmu
     */
    @Override
    public String getTime() {
        int time = (int)((System.currentTimeMillis() - startTime)/1000);
        int min = time / 60;
        int sec = time % 60;
        String m = (min < 10) ? new String("0"+min) : new String("" + min);
        String s = (sec < 10) ? new String("0"+sec) : new String("" + sec);
        return "[" + m + ":" + s + "] ";
    }
}
