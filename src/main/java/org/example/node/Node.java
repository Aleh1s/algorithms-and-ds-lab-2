package org.example.node;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Node {
    private final Node parent;
    private final Box3x3 box;
    private final int level;
    private int cost;
    public Node(
            Node parent,
            Box3x3 box,
            int level
    ) {
        this.parent = parent;
        this.box = box;
        this.level = level;
    }

    public Node(
            Node parent,
            Box3x3 box,
            int level,
            int cost
    ) {
        this.parent = parent;
        this.box = box;
        this.level = level;
        this.cost = cost;
    }

    public static int calculateCost(Box3x3 curr, int[][] goal, int level) {
        return curr.getNumOfDifferences(goal) + level;
    }
    public Box3x3 getSafeBox() {
        try {
            return box.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("(Can not clone Box3x3)");
            System.exit(-1);
        }
        return null;
    }
    public boolean isSolution(int[][] problem) {
        return box.getNumOfDifferences(problem) == 0;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return Objects.equals(box, node.box);
    }
    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + (box != null ? box.hashCode() : 0);
        result = 31 * result + level;
        result = 31 * result + cost;
        return result;
    }
}
