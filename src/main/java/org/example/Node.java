package org.example;

public class Node {
    private Node parent;
    private Box3x3 box;
    private int level;
    private int cost;
    public static int getCost(Box3x3 curr, Box3x3 goal) {
        return curr.getNumOfDifferences(goal);
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
