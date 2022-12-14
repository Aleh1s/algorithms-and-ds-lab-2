package org.example.utils;

import org.example.algorithm.Result;
import org.example.node.Direction;
import org.example.node.Node;
import org.example.property.Property;

import java.awt.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

public class Utils {

    private static final int timeLimit;
    public static final int ONE_GB_IN_BYTES = 1024 * 1024 * 1024;

    static {
        Properties properties = Property.getInstance().getProperties();
        timeLimit = Integer.parseInt(properties.getProperty("time.minutes.limit"));
    }

    public static void printExecutionTimeOf(Runnable action) {
        long start = System.nanoTime();
        action.run();
        System.out.printf("%d ms", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
    }

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

    public static int[][] generateProblem() {
        java.util.List<Integer> nums = new LinkedList<>(java.util.List.of(1, 2, 3, 4, 5, 6, 7, 8, 0));
        Collections.shuffle(nums);
        int[][] problem = new int[3][3];
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                problem[i][j] = nums.get(counter++);
            }
        }
        return problem;
    }
    public static boolean notSolvable(int[][] state) {
        int count = 0;
        int[] arr = from(state);
        for (int i = 0; i < 9; i++)
            for (int j = i + 1; j < 9; j++)
                if (arr[i] != 0 && arr[j] != 0 && arr[i] > arr[j])
                    count++;
        return count % 2 != 0;
    }
    public static void printInitialState(int[][] initialState) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%d ", initialState[i][j]);
            }
            System.out.println();
        }
    }

    public static int[] from(int[][] state) {
        return Arrays.stream(state)
                .flatMapToInt(IntStream::of)
                .toArray();
    }

    public static boolean timeOut(long start) {
        return TimeUnit.NANOSECONDS.toMinutes(System.nanoTime() - start) > timeLimit;
    }

    public static void handleResult(Result result) {
        Optional<Node> solution = result.getSolution();
        switch (result.getIndicator()) {
            case CUTOFF -> System.out.println("There is no solution on this depth level");
            case FAILURE -> System.out.println("Failure");
            case NOT_SOLVABLE -> System.out.println("Not solvable");
            case TERMINATED -> System.out.println("Terminated");
            case SOLUTION -> printSolution(solution.orElseThrow());
            default -> throw new IllegalArgumentException("Invalid indicator");
        }
    }

    public static boolean memoryLimitIsReached() {
        Runtime runtime = Runtime.getRuntime();
        long usedBytes = (runtime.totalMemory() - runtime.freeMemory());
        return usedBytes >= ONE_GB_IN_BYTES;
    }
}
