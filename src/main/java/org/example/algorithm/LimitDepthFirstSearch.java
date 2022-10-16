package org.example.algorithm;

import org.example.node.Node;
import org.example.parser.Parser;

import java.awt.*;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
            return handleResult(Result.of(true, false, null));
        Point eptTile = getEmptyTileCoordinates(problem);
        return handleResult(
                recursiveSearch(
                        new Node(problem, eptTile.x, eptTile.y, 0, null, null), Parser.getGoalState(), limit));
    }

    private static Optional<Node> handleResult(Result result) {
        Optional<Node> solution = result.getSolution();
        if (result.isCutoff())
            System.err.println("There is no solution on this depth level");
        else if (result.isFailure())
            System.err.println("Failure");
        else
            printSolution(solution.orElseThrow());
        return solution;
    }

    private static Result recursiveSearch(Node node, int[][] goal, int limit) {
        boolean cutoffOccurred = false;
        if (node.isSolution(goal))
            return Result.of(false, false, node);

        if (node.depthIsReached(limit))
            return Result.of(false, true, null);

        for (Node successor : node.getSuccessors()) {
            Result result = recursiveSearch(successor, goal, limit);

            if (result.isCutoff())
                cutoffOccurred = true;

            else if (!result.isFailure())
                return result;
        }
        if (cutoffOccurred)
            return Result.of(false, true, null);
        else
            return Result.of(true, false, node);
    }
}
