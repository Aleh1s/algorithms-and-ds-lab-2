package org.example.utils;

import lombok.Getter;
import org.example.algorithm.LimitDepthFirstSearch;
import org.example.algorithm.RecursiveBestFirstSearch;
import org.example.node.Node;
import org.example.property.Property;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static org.example.utils.Utils.*;

@Getter
public class Statistic {
    private long numberOfIterations;
    private final Set<Node> uniqueStates;
    private final Set<Node> uniqueStatesInMemory;
    private boolean isAlgorithmTerminated;
    private static final int NUMBER_OF_EXPERIMENTS;

    static {
        Properties properties = Property.getInstance().getProperties();
        NUMBER_OF_EXPERIMENTS = Integer.parseInt(properties.getProperty("number.of.experiments"));
    }

    {
        uniqueStates = new HashSet<>();
        uniqueStatesInMemory = new HashSet<>();
    }

    public static void main(String[] args) {
        testLimitedDepthFirstSearch();
        testRecursiveBestFirstSearch();
    }

    public void addUniqueState(Node node) {
        uniqueStates.add(node);
    }

    public void addUniqueStates(Collection<Node> collection) {
        uniqueStates.addAll(collection);
    }

    public void addUniqueStateInMemory(Node node) {
        uniqueStatesInMemory.add(node);
    }

    public void addUniqueStatesInMemory(Collection<Node> collection) {
        uniqueStatesInMemory.addAll(collection);
    }

    public void incrementNumberOfIteration() {
        numberOfIterations++;
    }


    public void setAlgorithmTerminated(boolean algorithmTerminated) {
        isAlgorithmTerminated = algorithmTerminated;
    }

    public void printStatistic() {
        System.out.printf("Number of iterations - %d%n", numberOfIterations);
        System.out.printf("Number of unique states - %d%n", uniqueStates.size());
        System.out.printf("Number of unique states in memory - %d%n", uniqueStatesInMemory.size());
        System.out.printf("Algorithm is %s TERMINATED%n", isAlgorithmTerminated ? "" : "not");
    }

    public static void testRecursiveBestFirstSearch() {
        System.out.println("RBFS:");
        int n = 0;
        for (int i = 0; i < NUMBER_OF_EXPERIMENTS + n; i++) {
            System.out.printf("Experiment number %d%n", i + 1);
            var problem = generateProblem();
            printInitialState(problem);
            var rbfs = new RecursiveBestFirstSearch();
            var result = rbfs.search(problem);
            var statistic = rbfs.getStatistic();
            if (!result.hasSolution() && !result.isTerminated())
                n++;
            handleResult(result);
            statistic.printStatistic();
            System.out.println("~~~");
        }
    }

    public static void testLimitedDepthFirstSearch() {
        System.out.println("LDFS:");
        int n = 0;
        for (int i = 0; i < NUMBER_OF_EXPERIMENTS + n; i++) {
            System.out.printf("Experiment number %d%n", i + 1);
            var problem = generateProblem();
            printInitialState(problem);
            var ldfs = new LimitDepthFirstSearch();
            var result = ldfs.search(problem, 30);
            var statistic = ldfs.getStatistic();
            if (!result.hasSolution() && !result.isTerminated())
                n++;
            handleResult(result);
            statistic.printStatistic();
            System.out.println("~~~");
        }
    }
}
