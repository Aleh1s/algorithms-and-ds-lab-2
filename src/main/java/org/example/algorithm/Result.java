package org.example.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.node.Indicator;
import org.example.node.Node;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.example.node.Indicator.*;

@Getter
@Setter
@AllArgsConstructor(access = PRIVATE)
public class Result {
    private int fBest;
    private Indicator indicator;
    private Optional<Node> solution;

    public static Result of(int fBest, Indicator indicator, Node solution) {
        return new Result(fBest, indicator, Optional.ofNullable(solution));
    }

    public static Result of(Indicator indicator, Node solution) {
        return new Result(0, indicator, Optional.ofNullable(solution));
    }

    public boolean hasSolution() {
        return indicator.equals(SOLUTION);
    }

    public boolean isFailed() {
        return indicator.equals(FAILURE);
    }

    public boolean cutoff() {
        return indicator.equals(CUTOFF);
    }

    public boolean isTerminated() {
        return indicator.equals(TERMINATED);
    }

    public boolean notSolvable() {
        return indicator.equals(NOT_SOLVABLE);
    }
}
