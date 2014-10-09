package pl.edu.agh.graph.isomorphism.algorithm;

/**
 * Klasa przechowująca informacje o wartościach funkcji fitness
 */
public class FitnessStats {
    
    //najlepsza wartość FF w danej populacji
    private double bestFitnessValue;
    //najgorsza wartość FF w danej populacji
    private double worstFitnessValue;
    //średnia wartość FF w danej populacji
    private double averageFitnessValue;
    //maksymalna wartość FF (idealnie dopasowany podgraf)
    private double bestPossibleFitnessValue;
    //rozmiar populacji
    private int populationSize;
    //waga FF1 (krawędzie)
    private double f1weight;
    //waga FF2 (stopnie wierzchołków)
    private double f2weight;

    public FitnessStats(int populationSize, double f1weight, double f2weight, double bestPossibleFitnessValue) {
        this.bestFitnessValue = 0;
        this.worstFitnessValue = Integer.MAX_VALUE;
        this.averageFitnessValue = 0;
        this.populationSize = populationSize;
        this.bestPossibleFitnessValue = bestPossibleFitnessValue;
        this.f1weight = f1weight;
        this.f2weight = f2weight;
    }

    public double getBestFitnessValue() {
        return bestFitnessValue;
    }

    public void setBestFitnessValue(double bestFitnessValue) {
        this.bestFitnessValue = bestFitnessValue;
    }

    public double getWorstFitnessValue() {
        return worstFitnessValue;
    }

    public void setWorstFitnessValue(double worstFitnessValue) {
        this.worstFitnessValue = worstFitnessValue;
    }

    public double getAverageFitnessValue() {
        return averageFitnessValue;
    }

    public void resetStats() {
        this.bestFitnessValue = 0;
        this.worstFitnessValue = Integer.MAX_VALUE;
        this.averageFitnessValue = 0;
    }

    public void addToAverageFitnessValue(double averageFitnessValue) {
        this.averageFitnessValue += averageFitnessValue;
    }

    public void calculateAverage() {
        this.averageFitnessValue /= populationSize;
    }

    public double getF2weight() {
        return f2weight;
    }

    public double getF1weight() {
        return f1weight;
    }

    public double getBestPossibleFitnessValue() {
        return bestPossibleFitnessValue;
    }
}
