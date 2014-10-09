/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.graph.isomorphism.algorithm.crossover;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import pl.edu.agh.graph.GraphGenerator;
import pl.edu.agh.graph.isomorphism.algorithm.SIPChromosome;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
/**
 *
 * @author Tomek
 */
public class OnePointCrossover<T extends Object> implements CrossoverPolicy  {

    @Override
    public ChromosomePair crossover(Chromosome chrmsm, Chromosome chrmsm1) throws MathIllegalArgumentException {
        
        SIPChromosome chrom1 = (SIPChromosome)chrmsm;
        SIPChromosome chrom2 = (SIPChromosome)chrmsm1;
        int size = GraphGenerator.getSmaller().getVertexCount();
        Random random = new Random();
        Integer half = random.nextInt(size);
        
        LinkedList<Integer> repr1 = new LinkedList<Integer>(chrom1.getChromosomeRepresentation());
        LinkedList<Integer> repr2 = new LinkedList<Integer>(chrom2.getChromosomeRepresentation());
        List<Integer> newRepr1 = new LinkedList<Integer>(repr1);
        List<Integer> newRepr2 = new LinkedList<Integer>(repr2);
        
        System.out.println("przed krzyzowaniem ");
        System.out.println(newRepr1);
        System.out.println(newRepr2);
        for (int i = 0; i < half; i++) {
            Integer replaced = newRepr1.set(i, newRepr2.get(size - half + i));
            newRepr2.set(size - half + i, replaced);
        }
        
        System.out.println("przed krzyzowaniem ");
        System.out.println(newRepr1);
        System.out.println(newRepr2);
        chrom1 = (SIPChromosome) chrom1.newFixedLengthChromosome(newRepr1);
        chrom2 = (SIPChromosome) chrom2.newFixedLengthChromosome(newRepr2);
        
        return new ChromosomePair(chrom1, chrom2);
    }
}
