package org.example.algorithm;

import lombok.SneakyThrows;
import org.example.node.Box3x3;
import org.example.node.Node;
import org.example.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class RecursiveBestFirstSearchTest {

    private final int[][] expected = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };

    @Test
    @SneakyThrows
    void solutionIsReached1() {
        int[][] problem = {
                {1, 2, 3},
                {0, 4, 6},
                {7, 5, 8}
        };
        Stack<Node> search = RecursiveBestFirstSearch.search(problem, expected);
        Node peek = search.peek();
        Box3x3 box = peek.getBox();
        int[][] actual = Utils.from(box.getTiles());
        assertArrayEquals(expected, actual);
    }
    @Test
    @SneakyThrows
    void solutionIsReached2() {
        int[][] problem = {
                {0, 2, 3},
                {1, 4, 6},
                {7, 5, 8}
        };
        Stack<Node> search = RecursiveBestFirstSearch.search(problem, expected);
        Node peek = search.peek();
        Box3x3 box = peek.getBox();
        int[][] actual = Utils.from(box.getTiles());
        assertArrayEquals(expected, actual);
    }
    @Test
    @SneakyThrows
    void solutionIsReached3() {
        int[][] problem = {
                {2, 0, 3},
                {1, 4, 6},
                {7, 5, 8}
        };
        Stack<Node> search = RecursiveBestFirstSearch.search(problem, expected);
        Node peek = search.peek();
        Box3x3 box = peek.getBox();
        int[][] actual = Utils.from(box.getTiles());
        assertArrayEquals(expected, actual);
    }
    @Test
    @SneakyThrows
    void solutionIsReached4() {
        int[][] problem = {
                {2, 4, 3},
                {1, 0, 6},
                {7, 5, 8}
        };
        Stack<Node> search = RecursiveBestFirstSearch.search(problem, expected);
        Node peek = search.peek();
        Box3x3 box = peek.getBox();
        int[][] actual = Utils.from(box.getTiles());
        assertArrayEquals(expected, actual);
    }

    @Test
    @SneakyThrows
    void solutionIsReached5() {
        int[][] problem = {
                {2, 4, 3},
                {1, 5, 0},
                {7, 8, 6}
        };
        Stack<Node> search = RecursiveBestFirstSearch.search(problem, expected);
        Node peek = search.peek();
        Box3x3 box = peek.getBox();
        int[][] actual = Utils.from(box.getTiles());
        assertArrayEquals(expected, actual);
    }
}