package org.example;

import org.example.algorithm.LimitDepthFirstSearch;
import org.example.algorithm.RecursiveBestFirstSearch;
import org.example.exception.CutoffException;
import org.example.exception.FailureException;
import org.example.exception.InvalidBox3x3Exception;
import org.example.node.Node;
import org.example.utils.Utils;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        int[][] goal = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        List<Integer> nums = new LinkedList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));
        Collections.shuffle(nums);
        int[][] problem = new int[3][3];
        int k = 0;
        System.out.println("Initial state:");
        for (int i = 0; i < problem.length; i++) {
            for (int j = 0; j < problem[i].length; j++) {
                problem[i][j] = nums.get(k++);
                System.out.printf("%d ", problem[i][j]);
            }
            System.out.println();
        }
        long executingTime1 = Utils.executingTime(() -> {
            System.out.println("LDFS result");
            try {
                Stack<Node> search = LimitDepthFirstSearch.search(problem, goal, 30);
                Utils.printResult(search);
            } catch (InvalidBox3x3Exception e) {
                System.err.println("Invalid box exception");
            } catch (CutoffException e) {
                System.out.println(e.getMessage());
            } catch (FailureException e) {
                System.out.println("Failed");
            }
        });
        System.out.printf("LDFS - %dms%n%n", TimeUnit.NANOSECONDS.toMillis(executingTime1));
        long executingTime2 = Utils.executingTime(() -> {
            System.out.println("RBFS result");
            try {
                Stack<Node> search = RecursiveBestFirstSearch.search(problem, goal);
                Utils.printResult(search);
            } catch (InvalidBox3x3Exception e) {
                System.out.println("Invalid box exception");
            } catch (FailureException e) {
                System.out.println("Failed");
            }
        });
        System.out.printf("RBFS - %dms%n", TimeUnit.NANOSECONDS.toMillis(executingTime2));
    }
}