package pl.edu.agh.graph.isomorphism.algorithm.mutation;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.MutationPolicy;
import pl.edu.agh.graph.GraphGenerator;
import pl.edu.agh.graph.isomorphism.algorithm.SIPChromosome;
import pl.edu.agh.graph.isomorphism.algorithm.SIPGeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mutacja - losuje element, losuje dla niego miejsce i wstawia go tam.
 */
public class InsertMutation implements MutationPolicy {

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
        while(greater > lower) {
            Collections.swap(newRepr, greater, greater-1);
            greater--;
        }
        return sipChromosome.newFixedLengthChromosome(newRepr);
    }
}
