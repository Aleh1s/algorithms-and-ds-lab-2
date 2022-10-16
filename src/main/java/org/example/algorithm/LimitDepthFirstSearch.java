package org.example.algorithm;

import lombok.Getter;
import org.example.node.Node;
import org.example.parser.Parser;
import org.example.utils.Statistic;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.example.node.Indicator.*;
import static org.example.utils.Utils.*;

@Getter
public class LimitDepthFirstSearch {

    private final Statistic statistic;
    private static final int[][] goal;

    static {
        goal = Parser.getGoalState();
    }

    {
        statistic = new Statistic();
    }

    public static void main(String[] args) {
        int[][] problem = generateProblem();
        LimitDepthFirstSearch ldfs = new LimitDepthFirstSearch();
        long start = System.nanoTime();
        ldfs.search(problem, 20);
        long finish = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(finish - start));
        printStatistic(ldfs.getStatistic());
        System.out.println("~~~");
    }

    public Optional<Node> search(int[][] problem, int limit) {
        if (notSolvable(problem))
            return handleResult(Result.of(NOT_SOLVABLE, null));
        Point eptTile = getEmptyTileCoordinates(problem);
        statistic.incrementNumberOfStates();
        statistic.incrementNumberOfSavedStates();
        Result result = recursiveSearch(new Node(problem, eptTile.x, eptTile.y, 0, null, null), limit, System.nanoTime());
        if (!result.hasSolution())
            statistic.decrementNumberOfSavedStates();
        return handleResult(result);
    }

    private Optional<Node> handleResult(Result result) {
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

    private Result recursiveSearch(Node node, int limit, long start) {
        statistic.incrementNumberOfIteration();
        if (timeOut(start))
            return Result.of(TERMINATED, null);

        boolean cutoffOccurred = false;
        if (node.isSolution(goal))
            return Result.of(SOLUTION, node);

        if (node.depthIsReached(limit))
            return Result.of(CUTOFF, null);

        List<Node> successors = node.getSuccessors();
        statistic.increaseNumberOfStates(successors.size());
        statistic.increaseNumberOfSavedStates(successors.size());
        for (Node successor : successors) {
            Result result = recursiveSearch(successor, limit, start);

            if (result.cutoff())
                cutoffOccurred = true;
            else if (result.hasSolution())
                return result;
            else if (result.isTerminated()) {
                statistic.reduceNumberOfSavedStates(successors.size());
                return result;
            }
        }

        statistic.reduceNumberOfSavedStates(successors.size());
        if (cutoffOccurred) {
            return Result.of(CUTOFF, null);
        } else {
            return Result.of(FAILURE, node);
        }
    }
}
