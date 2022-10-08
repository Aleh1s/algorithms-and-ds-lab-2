package org.example;

public class CutoffException extends Exception {
    public CutoffException() {
    }

    public CutoffException(String message) {
        super(message);
    }

    public CutoffException(String message, Throwable cause) {
        super(message, cause);
    }

    public CutoffException(Throwable cause) {
        super(cause);
    }
}
