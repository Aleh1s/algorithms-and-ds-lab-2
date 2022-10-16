package org.example.utils;

import java.util.List;

public class Statistic {
    private long numberOfIterations;
    private long numberOfStates;
    private long numberOfSavedStates;
    private boolean foundAnOptimalSolution;

    {
        foundAnOptimalSolution = true;
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

    public void solutionIsNotOptimal() {
        foundAnOptimalSolution = false;
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
