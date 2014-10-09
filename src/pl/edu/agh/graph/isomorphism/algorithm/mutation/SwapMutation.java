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
 * Mutacja - losuje dwa elementy i zamienia je miejscami
 */
public class SwapMutation implements MutationPolicy{

    @Override
    public Chromosome mutate(Chromosome chromosome) throws MathIllegalArgumentException {
        SIPChromosome sipChromosome = (SIPChromosome)chromosome;
        List<Integer> representation = sipChromosome.getChromosomeRepresentation();
        int chromosomeSize = GraphGenerator.getSmaller().getVertexCount();
        int first = SIPGeneticAlgorithm.getRandomGenerator().nextInt(chromosomeSize);
        int second = SIPGeneticAlgorithm.getRandomGenerator().nextInt(representation.size());
        List<Integer> newRepr = new ArrayList<Integer>(representation);
        Collections.swap(newRepr, first, second);
        return sipChromosome.newFixedLengthChromosome(newRepr);
    }

}
