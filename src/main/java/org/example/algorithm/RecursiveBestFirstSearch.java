package org.example.algorithm;

import org.example.node.Node;
import org.example.parser.Parser;
import org.example.utils.Statistic;
import org.example.utils.Utils;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.example.node.Indicator.*;
import static org.example.utils.Utils.*;

public class RecursiveBestFirstSearch {

    private Statistic statistic;
    private static final int[][] goal;

    static {
        goal = Parser.getGoalState();
    }

    {
        statistic = new Statistic();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            RecursiveBestFirstSearch recursiveBestFirstSearch = new RecursiveBestFirstSearch();
            int[][] problem = Utils.generateProblem();
            long start = System.nanoTime();
            recursiveBestFirstSearch.search(problem);
            long finish = System.nanoTime();
            System.out.println(NANOSECONDS.toMillis(finish - start));
            System.out.println("~~~");
        }
    }

    public Optional<Node> search(int[][] problem) {
        if (notSolvable(problem))
            return handleResult(Result.of(0, NOT_SOLVABLE, null));
        Point eptTile = getEmptyTileCoordinates(problem);
        return handleResult(
                recursiveSearch(
                        new Node(problem, eptTile.x, eptTile.y, 0, null, null), Integer.MAX_VALUE, System.nanoTime()));
    }

    private Optional<Node> handleResult(Result result) {
        Optional<Node> solution = result.getSolution();
        switch (result.getIndicator()) {
            case FAILURE -> System.out.println("Failure");
            case NOT_SOLVABLE -> System.out.println("Not solvable");
            case TERMINATED -> System.out.println("Terminated");
            case SOLUTION -> printSolution(result.getSolution().orElseThrow());
            default -> throw new IllegalArgumentException("Invalid indicator");
        }
        return solution;
    }

    private Result recursiveSearch(Node node, int fLimit, long start) {
        if (timeOut(start))
            return Result.of(Integer.MAX_VALUE, TERMINATED, null);

        if (node.isSolution(goal))
            return Result.of(fLimit, SOLUTION, node);

        List<Node> successors = node.getSuccessors();

        if (successors.isEmpty())
            return Result.of(Integer.MAX_VALUE, FAILURE, null);

        for (Node successor : successors)
            successor.setF(max(successor.misplaced(goal) + successor.getDepth(), node.misplaced(goal) + node.getDepth()));

        while (true) {
            successors.sort(Comparator.comparing(Node::getF));

            Node best = successors.get(0);
            if (best.getF() > fLimit)
                return Result.of(best.getF(), FAILURE, null);

            Node alt = successors.get(1);

            Result result = recursiveSearch(best, min(alt.getF(), fLimit), start);
            best.setF(result.getFBest());

            if (result.hasSolution() || result.isTerminated())
                return result;
        }
    }
}
