package org.example;

import org.example.algorithm.LimitDepthFirstSearch;
import org.example.exception.CutoffException;
import org.example.exception.FailureException;
import org.example.exception.InvalidBox3x3Exception;
import org.example.node.Node;
import org.example.utils.Utils;

import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        int[][] curr = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}
        };
        int[][] problem = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        try {
            Stack<Node> search = LimitDepthFirstSearch.search(curr, problem, 0);
            Utils.printResult(search);
        } catch (InvalidBox3x3Exception e) {
            throw new RuntimeException(e);
        } catch (CutoffException e) {
            throw new RuntimeException(e);
        } catch (FailureException e) {
            throw new RuntimeException(e);
        }
    }
}