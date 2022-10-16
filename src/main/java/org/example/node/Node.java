package org.example.node;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.utils.Utils.*;

@Getter
@Setter
public class Node {
    private int[][] state;
    private int emptyX;
    private int emptyY;
    private int depth;
    private int f;
    private Direction dir;
    private Node parent;

    public Node(
            int[][] state,
            int emptyX,
            int emptyY,
            int depth,
            Direction dir,
            Node parent) {
        this.state = state;
        this.emptyX = emptyX;
        this.emptyY = emptyY;
        this.depth = depth;
        this.dir = dir;
        this.parent = parent;
    }

    public List<Node> getSuccessors() {
        List<Node> nextStates = new ArrayList<>(Direction.values().length);
        for (Direction dir : Direction.values()) {
            if (isSafe(dir, emptyX, emptyY)) {
                int[][] state = cloneMatrix(getState());
                swap(state, emptyX + dir.getX(), emptyY + dir.getY(), emptyX, emptyY);
                nextStates.add(new Node(state, emptyX + dir.getX(), emptyY + dir.getY(), depth + 1, dir, this));
            }
        }
        return nextStates;
    }

    public int misplaced(int[][] goal) {
        int counter = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (state[i][j] != 0 && state[i][j] != goal[i][j])
                    counter++;
        return counter;
    }

    public boolean isSolution(int[][] goal) {
        return Arrays.deepEquals(state, goal);
    }

    public boolean depthIsReached(int limit) {
        return depth >= limit;
    }
}
