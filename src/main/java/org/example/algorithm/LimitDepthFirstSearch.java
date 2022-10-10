package org.example.algorithm;

import org.example.exception.CutoffException;
import org.example.exception.FailureException;
import org.example.exception.InvalidBox3x3Exception;
import org.example.exception.OutOfBoxException;
import org.example.node.Box3x3;
import org.example.node.Direction;
import org.example.node.Node;

import java.util.Stack;

import static java.util.Objects.*;

public class LimitDepthFirstSearch {

    public static Stack<Node> search(int[][] curr, int[][] problem, int limit) throws InvalidBox3x3Exception, CutoffException, FailureException {
        Stack<Node> result = new Stack<>();
        Box3x3.isValid(problem);
        Box3x3 box3x3 = new Box3x3(curr);
        Node root = new Node(null, box3x3, 0);
        recursiveSearch(root, problem, limit, result);
        return result;
    }

    private static void recursiveSearch(Node curr, int[][] problem, int limit, Stack<Node> result) throws CutoffException, FailureException {
        result.add(curr);
        boolean cutoffOccurred = false;
        String message = "(There is no solution of the problem on this depth level)";
        if (curr.isSolution(problem)) {
            return;
        } else if (curr.getLevel() == limit) {
            throw new CutoffException(message);
        } else {
            for (Direction direction : Direction.values()) {
                Box3x3 box = curr.getSafeBox();
                boolean isSafeOperation = true;
                try {
                    box.moveEmptyTile(direction);
                } catch (OutOfBoxException e) {
                    isSafeOperation = false;
                }
                if (isSafeOperation) {
                    boolean isSameAsParentBox = false;
                    if (nonNull(curr.getParent()))
                        isSameAsParentBox = box.equals(curr.getParent().getBox());
                    if (!isSameAsParentBox) {
                        Node successor = new Node(curr, box, curr.getLevel() + 1);
                        try {
                            recursiveSearch(successor, problem, limit, result);
                            return;
                        } catch (CutoffException e) {
                            cutoffOccurred = true;
                            result.pop();
                        }
                    }
                }
            }
        }
        if (cutoffOccurred)
            throw new CutoffException(message);
        else
            throw new FailureException();
    }
}
