package org.example.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.node.Node;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor(access = PRIVATE)
class Result {
    private int fBest;
    private boolean failure;
    private boolean cutoff;
    private Optional<Node> solution;

    public static Result of(int fBest, boolean failure, Node solution) {
        return new Result(fBest, failure, false, Optional.ofNullable(solution));
    }

    public static Result of(boolean failure, boolean cutoff, Node solution) {
        return new Result(0, failure, cutoff, Optional.ofNullable(solution));
    }
}
