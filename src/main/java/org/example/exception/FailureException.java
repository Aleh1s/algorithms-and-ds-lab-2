package org.example.exception;

public class FailureException extends Exception {

    private int fLimit;

    public FailureException() {
    }

    public FailureException(int fLimit) {
        this.fLimit = fLimit;
    }

    public int getfLimit() {
        return fLimit;
    }
}
