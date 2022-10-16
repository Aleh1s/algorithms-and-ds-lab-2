package org.example.algorithm;

import org.example.node.Node;
import org.example.parser.Parser;

import java.awt.*;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.example.node.Indicator.*;
import static org.example.utils.Utils.*;

public class LimitDepthFirstSearch {

    public static void main(String[] args) {
        int[][] problem = {
                {2, 4, 3},
                {1, 5, 0},
                {7, 8, 6}
        };
        long start = System.nanoTime();
        search(problem, 7);
        long finish = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(finish - start));
    }

    public static Optional<Node> search(int[][] problem, int limit) {
        if (!isSolvable(problem))
            return handleResult(Result.of(NOT_SOLVABLE, null));
        Point eptTile = getEmptyTileCoordinates(problem);
        return handleResult(
                recursiveSearch(
                        new Node(problem, eptTile.x, eptTile.y, 0, null, null), Parser.getGoalState(), limit, System.nanoTime()));
    }

    private static Optional<Node> handleResult(Result result) {
        Optional<Node> solution = result.getSolution();
        switch (result.getIndicator()) {
            case CUTOFF -> System.out.println("There is no solution on this depth level");
            case FAILURE -> System.out.println("Failure");
            case NOT_SOLVABLE -> System.out.println("Not solvable");
            case TERMINATED -> System.out.println("Terminated");
            case SOLUTION -> printSolution(solution.orElseThrow());
            default -> throw new IllegalArgumentException("Invalid indicator");
        }
        return solution;
    }

    private static Result recursiveSearch(Node node, int[][] goal, int limit, long start) {
        if (timeOut(start))
            return Result.of(TERMINATED, null);

        boolean cutoffOccurred = false;
        if (node.isSolution(goal))
            return Result.of(SOLUTION, node);

        if (node.depthIsReached(limit))
            return Result.of(CUTOFF, null);

        for (Node successor : node.getSuccessors()) {
            Result result = recursiveSearch(successor, goal, limit, start);

            if (result.cutoff())
                cutoffOccurred = true;
            else if (result.hasSolution() || result.isTerminated())
                return result;
        }

        if (cutoffOccurred)
            return Result.of(CUTOFF, null);
        else
            return Result.of(FAILURE,  node);
    }
}
