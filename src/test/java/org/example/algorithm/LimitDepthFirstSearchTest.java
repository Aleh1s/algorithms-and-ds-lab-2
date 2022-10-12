package org.example.algorithm;

import lombok.SneakyThrows;
import org.example.exception.CutoffException;
import org.example.node.Box3x3;
import org.example.node.Node;
import org.example.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LimitDepthFirstSearchTest {

    private final int[][] expected = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };

    @Test
    @SneakyThrows
    void solutionIsReached1() {
        int limit = 3;
        int[][] problem = {
                {1, 2, 3},
                {0, 4, 6},
                {7, 5, 8}
        };
        Stack<Node> search = LimitDepthFirstSearch.search(problem, expected, limit);
        Node peek = search.peek();
        Box3x3 box = peek.getBox();
        int[][] actual = Utils.from(box.getTiles());
        assertArrayEquals(expected, actual);
    }
    @Test
    @SneakyThrows
    void solutionIsReached2() {
        int limit = 4;
        int[][] problem = {
                {0, 2, 3},
                {1, 4, 6},
                {7, 5, 8}
        };
        Stack<Node> search = LimitDepthFirstSearch.search(problem, expected, limit);
        Node peek = search.peek();
        Box3x3 box = peek.getBox();
        int[][] actual = Utils.from(box.getTiles());
        assertArrayEquals(expected, actual);
    }
    @Test
    @SneakyThrows
    void solutionIsReached3() {
        int limit = 5;
        int[][] problem = {
                {2, 0, 3},
                {1, 4, 6},
                {7, 5, 8}
        };
        Stack<Node> search = LimitDepthFirstSearch.search(problem, expected, limit);
        Node peek = search.peek();
        Box3x3 box = peek.getBox();
        int[][] actual = Utils.from(box.getTiles());
        assertArrayEquals(expected, actual);
    }
    @Test
    @SneakyThrows
    void solutionIsReached4() {
        int limit = 6;
        int[][] problem = {
                {2, 4, 3},
                {1, 0, 6},
                {7, 5, 8}
        };
        Stack<Node> search = LimitDepthFirstSearch.search(problem, expected, limit);
        Node peek = search.peek();
        Box3x3 box = peek.getBox();
        int[][] actual = Utils.from(box.getTiles());
        assertArrayEquals(expected, actual);
    }

    @Test
    @SneakyThrows
    void solutionIsReached5() {
        int limit = 7;
        int[][] problem = {
                {2, 4, 3},
                {1, 5, 0},
                {7, 8, 6}
        };
        Stack<Node> search = LimitDepthFirstSearch.search(problem, expected, limit);
        Node peek = search.peek();
        Box3x3 box = peek.getBox();
        int[][] actual = Utils.from(box.getTiles());
        assertArrayEquals(expected, actual);
    }

    @Test
    void throwsCutoffException1() {
        int limit = 2;
        int[][] problem = {
                {1, 2, 3},
                {0, 4, 6},
                {7, 5, 8}
        };
        assertThrows(CutoffException.class, () -> LimitDepthFirstSearch.search(problem, expected, limit));
    }

    @Test
    void throwsCutoffException2() {
        int limit = 3;
        int[][] problem = {
                {0, 2, 3},
                {1, 4, 6},
                {7, 5, 8}
        };
        assertThrows(CutoffException.class, () -> LimitDepthFirstSearch.search(problem, expected, limit));
    }

    @Test
    void throwsCutoffException3() {
        int limit = 4;
        int[][] problem = {
                {2, 0, 3},
                {1, 4, 6},
                {7, 5, 8}
        };
        assertThrows(CutoffException.class, () -> LimitDepthFirstSearch.search(problem, expected, limit));
    }

    @Test
    void throwsCutoffException4() {
        int limit = 5;
        int[][] problem = {
                {2, 3, 0},
                {1, 4, 6},
                {7, 5, 8}
        };
        assertThrows(CutoffException.class, () -> LimitDepthFirstSearch.search(problem, expected, limit));
    }

    @Test
    void throwsCutoffException5() {
        int limit = 6;
        int[][] problem = {
                {2, 3, 6},
                {1, 4, 0},
                {7, 5, 8}
        };
        assertThrows(CutoffException.class, () -> LimitDepthFirstSearch.search(problem, expected, limit));
    }
}