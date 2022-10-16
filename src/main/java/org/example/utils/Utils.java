package org.example.utils;

import org.example.node.Direction;
import org.example.node.Node;

import java.awt.*;
import java.util.Arrays;

import static java.util.Objects.nonNull;

public class Utils {
    public static boolean isSafe(Direction dir, int x, int y) {
        boolean xOutOfBound = x + dir.getX() < 0 || x + dir.getX() >= 3;
        boolean yOutOfBound = y + dir.getY() < 0 || y + dir.getY() >= 3;
        return !xOutOfBound && !yOutOfBound;
    }

    public static void swap(int[][] state, int x, int y, int eptX, int eptY) {
        int temp = state[y][x];
        state[y][x] = state[eptY][eptX];
        state[eptY][eptX] = temp;
    }

    public static int[][] cloneMatrix(int[][] cloneable) {
        return Arrays.stream(cloneable)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    public static void printSolution(Node solution) {
        if (solution == null)
            return;

        printSolution(solution.getParent());
        printState(solution);
        System.out.println();
    }

    public static void printState(Node node) {
        String dir = nonNull(node.getDir()) ? node.getDir().name() : "INITIAL";
        System.out.printf("%s%n", dir);
        int[][] state = node.getState();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%d ", state[i][j]);
            }
            System.out.println();
        }
        System.out.printf("Depth - %d%n", node.getDepth());
    }

    public static Point getEmptyTileCoordinates(int[][] state) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (state[i][j] == 0)
                    return new Point(j, i);
        throw new IllegalArgumentException("Has no empty tile!");
    }
}
