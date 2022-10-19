package org.example.algorithm;

import lombok.Getter;
import org.example.node.Node;
import org.example.parser.Parser;
import org.example.utils.Statistic;

import java.awt.*;
import java.util.List;

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
        printExecutionTimeOf(() -> {
            var ldfs = new LimitDepthFirstSearch();
            handleResult(ldfs.search(problem, 25));
            ldfs.getStatistic().printStatistic();
        });
    }

    public Result search(int[][] problem, int limit) {
        if (notSolvable(problem))
            return Result.of(NOT_SOLVABLE, null);

        Point eptTile = getEmptyTileCoordinates(problem);
        Node root = new Node(problem, eptTile.x, eptTile.y, 0, null, null);

        statistic.addUniqueState(root);
        Result result = recursiveSearch(root, limit, System.nanoTime());
        statistic.addUniqueStateInMemory(root);

        if (result.isTerminated())
            statistic.setAlgorithmTerminated(true);

        return result;
    }

    private Result recursiveSearch(Node node, int limit, long start) {
        statistic.incrementNumberOfIteration();
        if (timeOut(start) || memoryLimitIsReached())
            return Result.of(TERMINATED, null);

        boolean cutoffOccurred = false;
        if (node.isSolution(goal))
            return Result.of(SOLUTION, node);

        if (node.depthIsReached(limit))
            return Result.of(CUTOFF, null);

        List<Node> successors = node.getSuccessors();
        statistic.addUniqueStates(successors);

        for (Node successor : successors) {
            Result result = recursiveSearch(successor, limit, start);

            if (result.cutoff())
                cutoffOccurred = true;
            else if (result.hasSolution() || result.isTerminated()) {
                statistic.addUniqueStatesInMemory(successors);
                return result;
            }
        }

        if (cutoffOccurred)
            return Result.of(CUTOFF, null);
        else
            return Result.of(FAILURE, null);
    }
}
