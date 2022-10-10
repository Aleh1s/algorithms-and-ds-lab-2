package org.example.utils;

import org.example.node.Node;

import java.util.Stack;

public class Utils {
    public static void printResult(Stack<Node> queue) {
        queue.stream()
                .map(Node::getBox)
                .forEach(box3x3 -> System.out.println(box3x3.toString()));
    }
}
