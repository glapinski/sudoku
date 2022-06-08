package pl.first.firstjava.exception;

public class BadContainerSizeException extends IllegalArgumentException {
    public BadContainerSizeException(final String message) {
        super(message);
    }
}
