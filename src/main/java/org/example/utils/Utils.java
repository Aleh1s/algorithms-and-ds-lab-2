package org.example.utils;

import org.example.node.Box3x3;
import org.example.node.Node;
import org.example.node.Tile;

import java.util.Stack;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Utils {
    public static void printResult(Stack<Node> queue) {
        queue.stream()
                .map(Node::getBox)
                .forEach(box3x3 -> System.out.println(box3x3.toString()));
    }

    public static long executingTime(Runnable action) {
        long start = System.nanoTime();
        action.run();
        return System.nanoTime() - start;
    }

    public static int[][] from(Tile[][] tiles) {
        int[][] result = new int[tiles.length][];
        IntStream.range(0, tiles.length).forEach(i -> {
            int[] arr = new int[tiles[i].length];
            IntStream.range(0, tiles[i].length).forEach(j -> {
                arr[j] = tiles[i][j].getValue();
            });
            result[i] = arr;
        });
        return result;
    }
}
