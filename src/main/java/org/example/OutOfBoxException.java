package org.example;

public class OutOfBoxException extends Exception {
    public OutOfBoxException() {
    }

    public OutOfBoxException(String message) {
        super(message);
    }

    public OutOfBoxException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfBoxException(Throwable cause) {
        super(cause);
    }
}
