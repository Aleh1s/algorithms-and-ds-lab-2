package org.example;

public class InvalidBox3x3Exception extends Exception {
    public InvalidBox3x3Exception() {
    }

    public InvalidBox3x3Exception(String message) {
        super(message);
    }

    public InvalidBox3x3Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBox3x3Exception(Throwable cause) {
        super(cause);
    }
}
