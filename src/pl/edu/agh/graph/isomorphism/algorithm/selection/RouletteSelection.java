package pl.edu.agh.graph.isomorphism.algorithm.selection;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.SelectionPolicy;
import pl.edu.agh.graph.isomorphism.algorithm.SIPGeneticAlgorithm;
import pl.edu.agh.graph.isomorphism.algorithm.SIPPopulation;

import java.util.List;

/**
 * Ruletkowa metoda selekcji osobników
 */
public class RouletteSelection implements SelectionPolicy {

    /**
     * Zwraca parę chromosomów wybraną według funkcji fitness. Im wyższą wartość 
     * ma dany chromosom, tym większa szansa, że zostanie wyrbany.
     * @param chromosomes populacja
     * @return para wybranych chromosomów
     * @throws MathIllegalArgumentException 
     */
    @Override
    public ChromosomePair select(Population chromosomes) throws MathIllegalArgumentException {
        SIPPopulation population = (SIPPopulation)chromosomes;
        List<Chromosome> list = population.getChromosomes();
        Chromosome first = null;
        Chromosome second = null;

        double fv[] = new double[list.size()];
        double sum = 0;
        for (int i = 0; i < fv.length; i++) {
            double fitness = list.get(i).getFitness();
            sum += fitness;
            fv[i] = sum;
        }
        double rand = SIPGeneticAlgorithm.getRandomGenerator().nextDouble() * sum;
        double rand2 = SIPGeneticAlgorithm.getRandomGenerator().nextDouble() * sum;
        int added = 0;
        for (int i = 0; i < fv.length; i++) {
            if(added >= 2)
                break;
            if(rand < fv[i] && first == null) {
                first = list.get(i);
                added++;
            }
            if(rand2 < fv[i] && second == null) {
                second = list.get(i);
                added++;
            }
        }
        return new ChromosomePair(first, second);

    }
}
