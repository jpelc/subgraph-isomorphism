/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.graph.isomorphism.algorithm.crossover;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import pl.edu.agh.graph.isomorphism.algorithm.SIPChromosome;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Tomek
 */
public class PartiallyMapedCrossover<T extends Object> implements CrossoverPolicy {

    @Override
    public ChromosomePair crossover(Chromosome chrmsm, Chromosome chrmsm1) throws MathIllegalArgumentException {
        SIPChromosome chrom1 = (SIPChromosome)chrmsm;
        SIPChromosome chrom2 = (SIPChromosome)chrmsm1;
        Random random = new Random();
        LinkedList<Integer> repr1 = new LinkedList<Integer>(chrom1.getChromosomeRepresentation());
        LinkedList<Integer> repr2 = new LinkedList<Integer>(chrom2.getChromosomeRepresentation());
        List<Integer> newRepr1 = new LinkedList<Integer>(repr1);
        List<Integer> newRepr2 = new LinkedList<Integer>(repr2);
        
        
        int i = random.nextInt(chrom1.getLength());
        System.out.println("przed krzyzowaniem "+ i);
        System.out.println(newRepr1);
        System.out.println(newRepr2);

        Integer swap1 = newRepr1.get(i);
        Integer swap2 = newRepr2.get(i);

        int index = newRepr1.indexOf(swap2);
        newRepr1.set(i, swap2);
        newRepr1.set(index, swap1);

        index = newRepr2.indexOf(swap1);
        newRepr2.set(i, swap1);
        newRepr2.set(index, swap2);

        System.out.println("po krzyzowaniu");
        System.out.println(newRepr1);
        System.out.println(newRepr2);
        
        return new ChromosomePair(chrom1.newFixedLengthChromosome(newRepr1), chrom1.newFixedLengthChromosome(newRepr2));
    }
    
    
    
}
