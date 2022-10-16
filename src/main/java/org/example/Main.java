package org.example;

import lombok.SneakyThrows;
import org.example.algorithm.LimitDepthFirstSearch;
import org.example.node.Node;
import org.example.utils.Statistic;
import org.example.utils.Utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        final int NUM_OF_EXPERIMENTS = 20;
        int[][] goal = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        int[][] problem = {
                {2, 4, 3},
                {1, 5, 0},
                {7, 8, 6}
        };
        Statistic statistic = new Statistic();
//        Stack<Node> search = LimitDepthFirstSearch.search(problem, goal,30, statistic);
//        Utils.printResult(search);

//        System.out.println("LDFS experiments: ");
//        List<Statistic> LDFSStatistics = new LinkedList<>();
//        for (int i = 0; i < NUM_OF_EXPERIMENTS; i++) {
//            Statistic statistic = new Statistic();
//            try {
//                int[][] problem = generateProblem();
//                System.out.printf("Experiment %d: %n", i + 1);
//                System.out.println("State: ");
//                printInitialState(problem);
//                LimitDepthFirstSearch.search(problem, goal, 40, statistic);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//            LDFSStatistics.add(statistic);
//            System.out.printf("Statistic - %s%n", statistic);
//            System.out.println("~~~");
//        }
//        printStatistics(LDFSStatistics);
//        System.out.println("RBFS experiments: ");
//        List<Statistic> RBFSStatistics = new LinkedList<>();
//        for (int i = 0; i < NUM_OF_EXPERIMENTS; i++) {
//            Statistic statistic = new Statistic();
//            try {
//                int[][] problem = generateProblem();
//                System.out.printf("Experiment %d: %n", i + 1);
//                System.out.println("State: ");
//                printInitialState(problem);
//                RecursiveBestFirstSearch.search(problem, goal, statistic);
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//            RBFSStatistics.add(statistic);
//            System.out.printf("Statistic - %s%n", statistic);
//            System.out.println("~~~");
//        }
//        printStatistics(RBFSStatistics);
    }

    private static int[][] generateProblem() {
        List<Integer> nums = new LinkedList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 0));
        Collections.shuffle(nums);
        int[][] problem = new int[3][3];
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                problem[i][j] = nums.get(counter++);
            }
        }
        return problem;
    }

    private static void printInitialState(int[][] initialState) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%d ", initialState[i][j]);
            }
            System.out.println();
        }
    }

    private static void printStatistics(List<Statistic> statistics) {
        System.out.println("The whole statistic: ");
        System.out.printf("Average number of iteration - %.2f%n", Statistic.countAverageNumberOfIteration(statistics));
        System.out.printf("Average number of states - %.2f%n", Statistic.countAverageNumberOfStates(statistics));
        System.out.printf("Average number of saved states - %.2f%n", Statistic.countAverageNumberOfSavedSates(statistics));
        System.out.println("Number of times when solution is not optimal - " + Statistic.countNumberOfTimesWhenSolutionIsNotOptimal(statistics));
    }
}