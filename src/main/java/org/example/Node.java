package org.example;

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

    public static void printPath(Node curr) {
        if (curr == null)
            return;
        printPath(curr.parent);
        curr.box.print();
        System.out.println();
    }

    public static int calculateCost(Box3x3 curr, int[][] goal) {
        return curr.getNumOfDifferences(goal);
    }

    public Node getParent() {
        return parent;
    }

    public Box3x3 getSafeBox() {
        try {
            return (Box3x3) box.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("(Can not clone Box3x3)");
            System.exit(-1);
        }
        return null;
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
}
