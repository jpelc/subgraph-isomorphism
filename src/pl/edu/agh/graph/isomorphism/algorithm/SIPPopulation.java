package pl.edu.agh.graph.isomorphism.algorithm;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ListPopulation;
import org.apache.commons.math3.genetics.Population;

import java.util.List;

/**
 * Klasa reprezentująca populację.
 */
public class SIPPopulation extends ListPopulation {

    /**
     * Konstruktor ustawiający wielkość populacji
     * @param populationLimit wielkość populacji
     */
    public SIPPopulation(int populationLimit) {
        super(populationLimit);
    }

    /**
     * Konstruktor tworzący populację o konkretnych chromosomach i konkretnej wielkości
     * @param chromosomes lista chromosomów
     * @param populationLimit wielkość populacji
     */
    public SIPPopulation(List<Chromosome> chromosomes, int populationLimit) {
        super(chromosomes, populationLimit);
    }
    
    /**
     * Zwraca nową, pustą populację
     * @return nowa, pusta populacja
     */
    @Override
    public Population nextGeneration() {
        return new SIPPopulation(getPopulationLimit());
    }

    /**
     * Metoda wywoływana po stworzeniu nowej populacji, w celu ustawienia min, max, 
     * avg wartości funkcji fitness.
     */
    public void updateFitnessStats() {
        List<Chromosome> list = getChromosomes();
        FitnessStats fitnessStats = ((SIPChromosome)list.get(0)).getFitnessStatsRef();
        fitnessStats.resetStats();
        for (Chromosome ch: list) {
            double f = ch.getFitness();
            fitnessStats.addToAverageFitnessValue(f);
            if(f < fitnessStats.getWorstFitnessValue())
                fitnessStats.setWorstFitnessValue(f);
            if(f > fitnessStats.getBestFitnessValue())
                fitnessStats.setBestFitnessValue(f);
        }
        fitnessStats.calculateAverage();
    }
}
