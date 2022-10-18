package org.example.utils;

import org.example.algorithm.LimitDepthFirstSearch;
import org.example.algorithm.RecursiveBestFirstSearch;
import org.example.algorithm.Result;

import java.util.List;

import static org.example.utils.Utils.*;

public class Statistic {
    private long numberOfIterations;
    private long numberOfStates;
    private long numberOfSavedStates;
    private boolean foundAnOptimalSolution;
    private static final int NUMBER_OF_EXPERIMENTS = 20;

    {
        foundAnOptimalSolution = true;
    }

    public static void main(String[] args) {
        System.out.println("LDFS:");
        for (int i = 0; i < NUMBER_OF_EXPERIMENTS; i++) {
            System.out.printf("Experiment number %d%n", i + 1);
            int[][] problem = generateProblem();
            LimitDepthFirstSearch ldfs = new LimitDepthFirstSearch();
            Result result = ldfs.search(problem, 25);
            handleResult(result);
            printStatistic(ldfs.getStatistic());
            System.out.println("~~~");
        }

        System.out.println("RBFS:");
        for (int i = 0; i < NUMBER_OF_EXPERIMENTS; i++) {
            System.out.printf("Experiment number %d%n", i + 1);
            int[][] problem = generateProblem();
            RecursiveBestFirstSearch rbfs = new RecursiveBestFirstSearch();
            Result result = rbfs.search(problem);
            handleResult(result);
            printStatistic(rbfs.getStatistic());
            System.out.println("~~~");
        }
    }

    public void increaseNumberOfStates(int number) {
        numberOfStates += number;
    }

    public void increaseNumberOfSavedStates(int number) {
        numberOfSavedStates += number;
    }

    public void reduceNumberOfSavedStates(int number) {
        numberOfSavedStates -= number;
    }

    public void incrementNumberOfIteration() {
        numberOfIterations++;
    }

    public void incrementNumberOfStates() {
        numberOfStates++;
    }

    public void incrementNumberOfSavedStates() {
        numberOfSavedStates++;
    }

    public void decrementNumberOfSavedStates() {
        numberOfSavedStates--;
    }

    public long getNumberOfIterations() {
        return numberOfIterations;
    }

    public long getNumberOfStates() {
        return numberOfStates;
    }

    public long getNumberOfSavedStates() {
        return numberOfSavedStates;
    }

    public boolean isFoundAnOptimalSolution() {
        return foundAnOptimalSolution;
    }

    public static double countAverageNumberOfIteration(List<Statistic> statistics) {
        return statistics.stream()
                .mapToLong(Statistic::getNumberOfIterations)
                .average()
                .orElse(0);
    }

    public static double countAverageNumberOfStates(List<Statistic> statistics) {
        return statistics.stream()
                .mapToLong(Statistic::getNumberOfStates)
                .average()
                .orElse(0);
    }

    public static double countAverageNumberOfSavedSates(List<Statistic> statistics) {
        return statistics.stream()
                .mapToLong(Statistic::getNumberOfSavedStates)
                .average()
                .orElse(0);
    }

    public static int countNumberOfTimesWhenSolutionIsNotOptimal(List<Statistic> statistics) {
        return (int) statistics.stream()
                .filter(statistic -> !statistic.isFoundAnOptimalSolution())
                .count();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Statistic{");
        sb.append("numberOfIterations=").append(numberOfIterations);
        sb.append(", numberOfStates=").append(numberOfStates);
        sb.append(", numberOfSavedStates=").append(numberOfSavedStates);
        sb.append(", foundAnOptimalSolution=").append(foundAnOptimalSolution);
        sb.append('}');
        return sb.toString();
    }
}
