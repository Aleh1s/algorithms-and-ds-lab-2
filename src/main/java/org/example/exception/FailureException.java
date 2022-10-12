package org.example.exception;

public class FailureException extends Exception {

    private int costLimit;

    public FailureException() {
    }

    public FailureException(int costLimit) {
        this.costLimit = costLimit;
    }

    public int getCostLimit() {
        return costLimit;
    }
}
