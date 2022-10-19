package org.example.algorithm;

import lombok.Getter;
import org.example.node.Node;
import org.example.parser.Parser;
import org.example.utils.Statistic;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.example.node.Indicator.*;
import static org.example.utils.Utils.*;

@Getter
public class LimitDepthFirstSearch {

    private int memoryUsed;
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
        Result search = ldfs.search(problem, 20);
        long finish = System.nanoTime();
        handleResult(search);
        System.out.println(TimeUnit.NANOSECONDS.toMillis(finish - start));
        printStatistic(ldfs.getStatistic());
    }

    public Result search(int[][] problem, int limit) {
        memoryUsed = 0;
        if (notSolvable(problem))
            return Result.of(NOT_SOLVABLE, null);
        Point eptTile = getEmptyTileCoordinates(problem);
        statistic.incrementNumberOfStates();
        statistic.incrementNumberOfSavedStates();
        memoryUsed += Integer.BYTES + Node.BYTES;
        Result result = recursiveSearch(new Node(problem, eptTile.x, eptTile.y, 0, null, null), limit, System.nanoTime());
        if (!result.hasSolution())
            statistic.decrementNumberOfSavedStates();
        return result;
    }

    private Result recursiveSearch(Node node, int limit, long start) {
        memoryUsed += Integer.BYTES;
        statistic.incrementNumberOfIteration();
        if (timeOut(start) || memoryLimitIsReached(memoryUsed)) {
            return Result.of(TERMINATED, null);
        }

        boolean cutoffOccurred = false;
        if (node.isSolution(goal)) {
            return Result.of(SOLUTION, node);
        }

        if (node.depthIsReached(limit)) {
            return Result.of(CUTOFF, null);
        }

        List<Node> successors = node.getSuccessors();
        statistic.increaseNumberOfStates(successors.size());
        statistic.increaseNumberOfSavedStates(successors.size());
        memoryUsed += successors.size() * Node.BYTES;
        System.out.println("Increment");

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
            System.out.println("Reduce");
            memoryUsed -= (Integer.BYTES + successors.size() * Node.BYTES);
            return Result.of(CUTOFF, null);
        } else {
            return Result.of(FAILURE, null);
        }
    }
}
