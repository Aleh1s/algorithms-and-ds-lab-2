package org.example;

import org.example.algorithm.LimitDepthFirstSearch;
import org.example.algorithm.RecursiveBestFirstSearch;
import org.example.exception.CutoffException;
import org.example.exception.FailureException;
import org.example.exception.InvalidBox3x3Exception;
import org.example.node.Node;
import org.example.utils.Utils;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        int[][] curr = {
                {2, 1, 3},
                {0, 4, 6},
                {7, 8, 5}
        };
        int[][] problem = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };

//        try {
//            Stack<Node> search = LimitDepthFirstSearch.search(curr, problem, 20);
//            Utils.printResult(search);
//        } catch (InvalidBox3x3Exception e) {
//            throw new RuntimeException(e);
//        } catch (CutoffException e) {
//            throw new RuntimeException(e);
//        } catch (FailureException e) {
//            throw new RuntimeException(e);
//        }

        long executingTime = Utils.executingTime(() -> {
            Stack<Node> search;
            try {
                search = RecursiveBestFirstSearch.search(curr, problem);
            } catch (InvalidBox3x3Exception e) {
                throw new RuntimeException(e);
            } catch (FailureException e) {
                throw new RuntimeException(e);
            }
            Utils.printResult(search);
        });
        System.out.println("Time to execute - " + TimeUnit.NANOSECONDS.toMillis(executingTime));
    }
}