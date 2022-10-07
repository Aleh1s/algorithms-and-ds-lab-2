package org.example;

public class Node {
    private final Node parent;
    private final Box3x3 box;
    private final int level;
    private final int cost;

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

    public static int getCost(Box3x3 curr, Box3x3 goal) {
        return curr.getNumOfDifferences(goal);
    }

    public Node getParent() {
        return parent;
    }

    public Box3x3 getBox() {
        return box;
    }

    public int getLevel() {
        return level;
    }

    public int getCost() {
        return cost;
    }
}
