package pl.first.firstjava.exception;

import java.io.IOException;

public class BoardFileLoadException extends IOException {
    public BoardFileLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
