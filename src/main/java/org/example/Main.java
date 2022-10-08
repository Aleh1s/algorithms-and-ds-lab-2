package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[][] curr = {
                {1, 2, 3},
                {0, 4, 6},
                {7, 5, 8}
        };
        int[][] problem = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        try {
            LimitDepthFirstSearch.search(curr, problem, 3);
        } catch (InvalidBox3x3Exception e) {
            throw new RuntimeException(e);
        } catch (CutoffException e) {
            throw new RuntimeException(e);
        } catch (FailureException e) {
            throw new RuntimeException(e);
        }
    }
}