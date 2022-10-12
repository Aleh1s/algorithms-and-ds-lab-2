package org.example.algorithm;

import org.example.exception.FailureException;
import org.example.exception.InvalidBox3x3Exception;
import org.example.exception.OutOfBoxException;
import org.example.node.Box3x3;
import org.example.node.Direction;
import org.example.node.Node;

import java.util.*;

import static java.util.Objects.nonNull;

public class RecursiveBestFirstSearch {

    public static Stack<Node> search(int[][] problem, int[][] goal) throws InvalidBox3x3Exception, FailureException {
        Stack<Node> result = new Stack<>();
        Box3x3.isValid(goal);
        Box3x3 box3x3 = new Box3x3(problem);
        int level = 0,
            cost = Node.calculateCost(box3x3, goal, level);
        Node root = new Node(null, box3x3, level, cost);
        recursiveSearch(goal, root, Integer.MAX_VALUE, result);
        return result;
    }

    private static void recursiveSearch(int[][] goal, Node node, int costLimit, Stack<Node> result) throws FailureException {
        result.add(node);
        if (node.isSolution(goal)) return;
        List<Node> successors = getSuccessors(node, goal);
        if (successors.isEmpty())
            throw new FailureException(Integer.MAX_VALUE);
        while (true) {
            Node best = findBest(successors);
            if (best.getCost() > costLimit)
                throw new FailureException(best.getCost());
            Optional<Node> alternative = findAlternative(successors, best);
            int limit = alternative
                    .map(alt -> Math.min(costLimit, alt.getCost()))
                    .orElse(costLimit);
            try {
                recursiveSearch(goal, best, limit, result);
                return;
            } catch (FailureException e) {
                best.setCost(e.getCostLimit());
                result.pop();
            }
        }
    }

    private static List<Node> getSuccessors(Node node, int[][] goal) {
        List<Node> successors = new LinkedList<>();
        for (Direction dir : Direction.values()) {
            Box3x3 state = node.getSafeBox();
            boolean isSafeOperation = true;
            try {
                state.moveEmptyTile(dir);
            } catch (OutOfBoxException e) {
                isSafeOperation = false;
            }
            if (isSafeOperation) {
                boolean isSameAsParent = false;
                if (nonNull(node.getParent()))
                    isSameAsParent = state.equals(node.getParent().getBox());
                if (!isSameAsParent) {
                    int level = node.getLevel() + 1,
                            cost = Node.calculateCost(state, goal, level);
                    successors.add(new Node(node, state, level, cost));
                }
            }
        }
        return successors;
    }

    private static Optional<Node> findAlternative(List<Node> successors, Node best) {
        List<Node> copy = new LinkedList<>(successors);
        copy.remove(best);
        return copy.stream()
                .min(Comparator.comparing(Node::getCost));
    }

    private static Node findBest(List<Node> successors) {
        return successors.stream()
                .min(Comparator.comparing(Node::getCost))
                .orElseThrow();
    }
}
