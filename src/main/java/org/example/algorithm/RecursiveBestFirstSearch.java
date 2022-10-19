package org.example.algorithm;

import lombok.Getter;
import org.example.node.Node;
import org.example.parser.Parser;
import org.example.utils.Statistic;
import org.example.utils.Utils;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.example.node.Indicator.*;
import static org.example.utils.Utils.*;

@Getter
public class RecursiveBestFirstSearch {
    private final Statistic statistic;
    private static final int[][] goal;

    static {
        goal = Parser.getGoalState();
    }

    {
        statistic = new Statistic();
    }

    public static void main(String[] args) {
        int[][] problem = Utils.generateProblem();
        printExecutionTimeOf(() -> {
            var rbfs = new RecursiveBestFirstSearch();
            handleResult(rbfs.search(problem));
            rbfs.getStatistic().printStatistic();
        });
    }

    public Result search(int[][] problem) {
        if (notSolvable(problem))
            return Result.of(0, NOT_SOLVABLE, null);

        Point eptTile = getEmptyTileCoordinates(problem);
        Node root = new Node(problem, eptTile.x, eptTile.y, 0, null, null);

        statistic.addUniqueState(root);
        Result result = recursiveSearch(root, Integer.MAX_VALUE, System.nanoTime());
        statistic.addUniqueStateInMemory(root);

        if (result.isTerminated())
            statistic.setAlgorithmTerminated(true);

        return result;
    }

    private Result recursiveSearch(Node node, int fLimit, long start) {
        statistic.incrementNumberOfIteration();
        if (timeOut(start) || memoryLimitIsReached())
            return Result.of(Integer.MAX_VALUE, TERMINATED, null);

        if (node.isSolution(goal))
            return Result.of(fLimit, SOLUTION, node);

        List<Node> successors = node.getSuccessors();
        statistic.addUniqueStates(successors);

        if (successors.isEmpty())
            return Result.of(Integer.MAX_VALUE, FAILURE, null);

        for (Node successor : successors)
            successor.setF(max(successor.misplaced(goal) + successor.getDepth(), node.getF()));

        while (true) {
            successors.sort(Comparator.comparing(Node::getF));

            Node best = successors.get(0);
            if (best.getF() > fLimit)
                return Result.of(best.getF(), FAILURE, null);

            Node alt = successors.get(1);

            Result result = recursiveSearch(best, min(alt.getF(), fLimit), start);
            best.setF(result.getFBest());

            if (result.hasSolution() || result.isTerminated()) {
                statistic.addUniqueStatesInMemory(successors);
                return result;
            }
        }
    }
}
