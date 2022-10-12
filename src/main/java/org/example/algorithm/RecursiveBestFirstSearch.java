package org.example.algorithm;

import org.example.exception.FailureException;
import org.example.exception.InvalidBox3x3Exception;
import org.example.exception.OutOfBoxException;
import org.example.node.Box3x3;
import org.example.node.Direction;
import org.example.node.Node;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static java.util.Objects.nonNull;

public class RecursiveBestFirstSearch {

    public static Stack<Node> search(int[][] curr, int[][] problem) throws InvalidBox3x3Exception, FailureException {
        Stack<Node> result = new Stack<>();
        Box3x3.isValid(problem);
        Box3x3 box3x3 = new Box3x3(curr);
        Node root = new Node(null, box3x3, 0, Node.calculateCost(box3x3, problem, 0));
        recursiveSearch(problem, root, Integer.MAX_VALUE, result);
        return result;
    }

    private static void recursiveSearch(int[][] problem, Node curr, int fLimit, Stack<Node> result) throws FailureException {
        result.add(curr);
        if (curr.isSolution(problem))
            return;
        List<Node> successors = new LinkedList<>();
        for (Direction dir : Direction.values()) {
            Box3x3 box = curr.getSafeBox();
            boolean isSafeOperation = true;
            try {
                box.moveEmptyTile(dir);
            } catch (OutOfBoxException e) {
                isSafeOperation = false;
            }
            if (isSafeOperation) {
                boolean isSameAsParent = false;
                if (nonNull(curr.getParent())) {
                    isSameAsParent = box.equals(curr.getParent().getBox());
                }
                if (!isSameAsParent) {
                    successors.add(new Node(curr, box, curr.getLevel() + 1));
                }
            }
        }
        if (successors.isEmpty())
            throw new FailureException(Integer.MAX_VALUE);
        for (Node successor : successors) {
            successor.setCost(Math.max(Node.calculateCost(successor.getBox(), problem, successor.getLevel()), curr.getCost()));
        }
        while (true) {
            Node best = findNodeWithMinCost(successors);
            if (best.getCost() > fLimit)
                throw new FailureException(best.getCost());
            Optional<Node> alternative = findAlternative(successors, best);
            int limit = alternative
                    .map(node -> Math.min(fLimit, node.getCost()))
                    .orElse(fLimit);
            try {
                recursiveSearch(problem, best, limit, result);
                return;
            } catch (FailureException e) {
                best.setCost(e.getfLimit());
                result.pop();
            }
        }
    }

    private static Optional<Node> findAlternative(List<Node> successors, Node best) {
        List<Node> copy = new LinkedList<>(successors);
        copy.remove(best);
        return copy.stream()
                .min(Comparator.comparing(Node::getCost));
    }

    private static Node findNodeWithMinCost(List<Node> successors) {
        return successors.stream()
                .min(Comparator.comparing(Node::getCost))
                .orElseThrow();
    }
}
