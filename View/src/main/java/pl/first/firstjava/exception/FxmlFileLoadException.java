package pl.first.firstjava.exception;

import java.io.IOException;

public class FxmlFileLoadException extends IOException {
    public FxmlFileLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
