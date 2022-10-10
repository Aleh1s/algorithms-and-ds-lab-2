package org.example.node;

import java.util.Objects;

public class Node {
    private final Node parent;
    private final Box3x3 box;
    private final int level;
    private final int cost;

    {
        cost = 0;
    }

    public Node(
            Node parent,
            Box3x3 box,
            int level
    ) {
        this.parent = parent;
        this.box = box;
        this.level = level;
    }

    public static int calculateCost(Box3x3 curr, int[][] goal) {
        return curr.getNumOfDifferences(goal);
    }

    public Node getParent() {
        return parent;
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

    public Box3x3 getBox() {
        return box;
    }

    public boolean isSolution(int[][] problem) {
        return calculateCost(box, problem) == 0;
    }

    public int getLevel() {
        return level;
    }

    public int getCost() {
        return cost;
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
