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
        int n1 = 0;
        for (int i = 0; i < NUMBER_OF_EXPERIMENTS + n1; i++) {
            System.out.printf("Experiment number %d%n", i + 1);
            int[][] problem = generateProblem();
            printInitialState(problem);
            LimitDepthFirstSearch ldfs = new LimitDepthFirstSearch();
            Result search = ldfs.search(problem, 40);
            if (search.isTerminated()) {
                System.out.println("Terminated");
                printStatistic(ldfs.getStatistic());
            } else if (search.hasSolution()) {
                System.out.println("Has solution");
                printStatistic(ldfs.getStatistic());
            } else {
                System.out.println(search.getIndicator().name());
                n1++;
            }
            System.out.println("~~~");
        }
        System.out.println("RBFS:");
        int n2 = 0;
        for (int i = 0; i < NUMBER_OF_EXPERIMENTS + n2; i++) {
            System.out.printf("Experiment number %d%n", i + 1);
            int[][] problem = generateProblem();
            printInitialState(problem);
            RecursiveBestFirstSearch rbfs = new RecursiveBestFirstSearch();
            Result search = rbfs.search(problem);
            if (search.isTerminated()) {
                System.out.println("Terminated");
                printStatistic(rbfs.getStatistic());
            } else if (search.hasSolution()) {
                System.out.println("Has solution");
                printStatistic(rbfs.getStatistic());
            } else {
                System.out.println(search.getIndicator().name());
                n2++;
            }
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
