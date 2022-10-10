package org.example.utils;

import org.example.node.Node;

import java.util.Stack;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
}
