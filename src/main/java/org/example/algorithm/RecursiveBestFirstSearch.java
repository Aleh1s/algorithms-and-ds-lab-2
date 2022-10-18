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
import static java.util.concurrent.TimeUnit.NANOSECONDS;
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
        RecursiveBestFirstSearch rbfs = new RecursiveBestFirstSearch();
        long start = System.nanoTime();
        rbfs.search(problem);
        long finish = System.nanoTime();
        System.out.println(NANOSECONDS.toMillis(finish - start));
        printStatistic(rbfs.getStatistic());
        System.out.println("~~~");
    }

    public Result search(int[][] problem) {
        if (notSolvable(problem))
            return Result.of(0, NOT_SOLVABLE, null);
        Point eptTile = getEmptyTileCoordinates(problem);
        statistic.incrementNumberOfStates();
        statistic.incrementNumberOfSavedStates();
        Result result = recursiveSearch(new Node(problem, eptTile.x, eptTile.y, 0, null, null), Integer.MAX_VALUE, System.nanoTime());
        if (!result.hasSolution())
            statistic.decrementNumberOfSavedStates();
        return result;
    }

    private Result recursiveSearch(Node node, int fLimit, long start) {
        statistic.incrementNumberOfIteration();
        if (timeOut(start))
            return Result.of(Integer.MAX_VALUE, TERMINATED, null);

        if (node.isSolution(goal))
            return Result.of(fLimit, SOLUTION, node);

        List<Node> successors = node.getSuccessors();
        statistic.increaseNumberOfStates(successors.size());
        statistic.increaseNumberOfSavedStates(successors.size());

        if (successors.isEmpty())
            return Result.of(Integer.MAX_VALUE, FAILURE, null);

        for (Node successor : successors)
            successor.setF(max(successor.misplaced(goal) + successor.getDepth(), node.misplaced(goal) + node.getDepth()));

        while (true) {
            successors.sort(Comparator.comparing(Node::getF));

            Node best = successors.get(0);
            if (best.getF() > fLimit) {
                statistic.reduceNumberOfSavedStates(successors.size());
                return Result.of(best.getF(), FAILURE, null);
            }

            Node alt = successors.get(1);

            Result result = recursiveSearch(best, min(alt.getF(), fLimit), start);
            best.setF(result.getFBest());

            if (result.hasSolution())
                return result;

            if (result.isTerminated()) {
                statistic.reduceNumberOfSavedStates(successors.size());
                return result;
            }
        }
    }
}
