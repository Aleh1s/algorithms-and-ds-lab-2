package org.example.algorithm;

import lombok.Getter;
import lombok.Setter;
import org.example.node.Node;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.example.utils.Utils.getEmptyTileCoordinates;
import static org.example.utils.Utils.printSolution;

public class LimitDepthFirstSearch {

    public static void main(String[] args) {
        int[][] problem = {
                {2, 4, 3},
                {1, 5, 0},
                {7, 8, 6}
        };
        int[][] expected = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        long start = System.nanoTime();
        search(problem, expected, 7);
        long finish = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(finish - start));
    }

    public static Optional<Node> search(int[][] problem, int[][] goal, int limit) {
        Point eptTile = getEmptyTileCoordinates(problem);
        Result result = recursiveSearch(new Node(problem, eptTile.x, eptTile.y, 0, null, null), goal, limit);
        if (result.isCutoff()) {
            System.err.println("There is no solution on this depth level");
            return Optional.empty();
        } else if (result.isFailure()) {
            System.err.println("Failure");
            return Optional.empty();
        } else {
            printSolution(result.getSolution().orElseThrow());
            return result.getSolution();
        }
    }
    private static Result recursiveSearch(Node node, int[][] goal, int limit) {
        boolean cutoffOccurred = false;
        if (Arrays.deepEquals(node.getState(), goal))
            return Result.of(false, false, node);

        if (node.getDepth() >= limit)
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
