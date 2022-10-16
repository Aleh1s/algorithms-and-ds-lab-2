package org.example.algorithm;

import lombok.SneakyThrows;
import org.example.node.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@ExtendWith(MockitoExtension.class)
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
        Optional<Node> search = RecursiveBestFirstSearch.search(problem, expected);
        Node node = search.orElseThrow();
        assertArrayEquals(expected, node.getState());
    }

    @Test
    @SneakyThrows
    void solutionIsReached2() {
        int[][] problem = {
                {0, 2, 3},
                {1, 4, 6},
                {7, 5, 8}
        };
        Optional<Node> search = RecursiveBestFirstSearch.search(problem, expected);
        Node node = search.orElseThrow();
        assertArrayEquals(expected, node.getState());
    }

    @Test
    @SneakyThrows
    void solutionIsReached3() {
        int[][] problem = {
                {2, 0, 3},
                {1, 4, 6},
                {7, 5, 8}
        };
        Optional<Node> search = RecursiveBestFirstSearch.search(problem, expected);
        Node node = search.orElseThrow();
        assertArrayEquals(expected, node.getState());
    }

    @Test
    @SneakyThrows
    void solutionIsReached4() {
        int[][] problem = {
                {2, 4, 3},
                {1, 0, 6},
                {7, 5, 8}
        };
        Optional<Node> search = RecursiveBestFirstSearch.search(problem, expected);
        Node node = search.orElseThrow();
        assertArrayEquals(expected, node.getState());
    }

    @Test
    @SneakyThrows
    void solutionIsReached5() {
        int[][] problem = {
                {2, 4, 3},
                {1, 5, 0},
                {7, 8, 6}
        };
        Optional<Node> search = RecursiveBestFirstSearch.search(problem, expected);
        Node node = search.orElseThrow();
        assertArrayEquals(expected, node.getState());
    }
}