package com.nhnacademy;

public class InvalidStatusException extends RuntimeException {
    private final int code;

    public InvalidStatusException(int code) {
        this.code = code;
    }

    public InvalidStatusException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "<h1>" + this.code + " " + super.getMessage() + "</h1>\n"
                + "<p>The requested resource could not be found.</p>";
    }
}
