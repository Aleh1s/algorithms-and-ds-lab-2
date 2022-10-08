package org.example;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static org.example.Direction.*;

public class LimitDepthFirstSearch {

    private static final Direction[] directions;

    static {
        directions = new Direction[]{UP, RIGHT, DOWN, LEFT};
    }

    public static void search(int[][] curr, int[][] problem, int limit) throws InvalidBox3x3Exception, CutoffException, FailureException {
        Box3x3 box3x3 = new Box3x3(curr);
        Node root = new Node(null, box3x3, 0);
        recursiveSearch(root, problem, limit);
    }

    private static void recursiveSearch(Node curr, int[][] problem, int limit) throws CutoffException, FailureException {
        AtomicBoolean cutoffOccurred = new AtomicBoolean(false);
        if (curr.isSolution(problem)) {
            Node.printPath(curr);
            return;
        } else if (curr.getLevel() == limit) {
            throw new CutoffException("(There is no solution of the problem on this depth level)");
        } else {
            for (Direction direction : directions) {
                Box3x3 safeParentBox = curr.getSafeBox();
                boolean isSafeOperation = true;
                try {
                    safeParentBox.moveEmptyTile(direction);
                } catch (OutOfBoxException e) {
                    isSafeOperation = false;
                }
                if (isSafeOperation) {
                    Node successor = new Node(curr, safeParentBox, curr.getLevel() + 1);
                    try {
                        recursiveSearch(successor, problem, limit);
                        return;
                    } catch (CutoffException e) {
                        cutoffOccurred.set(true);
                    }
                }
            }
        }
        if (cutoffOccurred.get())
            throw new CutoffException();
        else
            throw new FailureException();
    }
}
