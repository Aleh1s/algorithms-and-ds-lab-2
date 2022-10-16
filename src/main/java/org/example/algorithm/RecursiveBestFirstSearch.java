package org.example.algorithm;

import org.example.node.Node;
import org.example.parser.Parser;
import org.example.utils.Utils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.example.utils.Utils.*;

public class RecursiveBestFirstSearch {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int[][] problem = Utils.generateProblem();
            long start = System.nanoTime();
            search(problem);
            long finish = System.nanoTime();
            System.out.println(TimeUnit.NANOSECONDS.toMillis(finish - start));
            System.out.println("~~~");
        }
    }

    public static Optional<Node> search(int[][] problem) {
        if (!isSolvable(problem))
            return handleResult(Result.of(0, true, null));
        Point eptTile = getEmptyTileCoordinates(problem);
        return handleResult(recursiveSearch(
                new Node(problem, eptTile.x, eptTile.y, 0, null, null), Parser.getGoalState(), Integer.MAX_VALUE));
    }

    private static Optional<Node> handleResult(Result result) {
        Optional<Node> solution = result.getSolution();
        if (result.isFailure())
            System.out.println("Failure");
        else
            printSolution(solution.orElseThrow());
        return solution;
    }

    private static Result recursiveSearch(Node node, int[][] goal, int fLimit) {
        if (node.isSolution(goal))
            return Result.of(fLimit, false, node);

        List<Node> successors = node.getSuccessors();

        if (successors.isEmpty())
            return Result.of(Integer.MAX_VALUE, true, null);

        for (Node successor : successors)
            successor.setF(max(successor.misplaced(goal) + successor.getDepth(), node.misplaced(goal) + node.getDepth()));

        while (true) {
            successors.sort(Comparator.comparing(Node::getF));

            Node best = successors.get(0);
            if (best.getF() > fLimit)
                return Result.of(best.getF(), true, null);

            Node alt = successors.get(1);

            Result result = recursiveSearch(best, goal, min(alt.getF(), fLimit));
            best.setF(result.getFBest());

            if (!result.isFailure())
                return result;
        }
    }
}
