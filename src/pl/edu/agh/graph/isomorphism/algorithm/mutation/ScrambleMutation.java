package pl.edu.agh.graph.isomorphism.algorithm.mutation;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.MutationPolicy;
import pl.edu.agh.graph.GraphGenerator;
import pl.edu.agh.graph.isomorphism.algorithm.SIPChromosome;
import pl.edu.agh.graph.isomorphism.algorithm.SIPGeneticAlgorithm;

import java.util.*;

/**
 * Mutacja - losuje dwie pozycje w chromosomie, następnie miesza elementy zawarte
 * pomiędzy nimi
 */
public class ScrambleMutation implements MutationPolicy{

    @Override
    public Chromosome mutate(Chromosome chromosome) throws MathIllegalArgumentException {
        SIPChromosome sipChromosome = (SIPChromosome)chromosome;
        List<Integer> representation = sipChromosome.getChromosomeRepresentation();
        int chromosomeSize = GraphGenerator.getSmaller().getVertexCount();
        int first = SIPGeneticAlgorithm.getRandomGenerator().nextInt(chromosomeSize);
        int second = SIPGeneticAlgorithm.getRandomGenerator().nextInt(representation.size());
        List<Integer> newRepr = new ArrayList<Integer>(representation);
        int lower, greater;
        if(first <= second) {
            lower = first;
            greater = second;
        } else {
            lower = second;
            greater = first;
        }

        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = lower; i <= greater; i++)
            list.add(newRepr.get(i));
        for (int i = lower; i <= greater; i++)
            newRepr.set(i, list.remove(SIPGeneticAlgorithm.getRandomGenerator().nextInt(list.size())));
        return sipChromosome.newFixedLengthChromosome(newRepr);
    }
}
