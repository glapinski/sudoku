package pl.first.firstjava.exception;

public class LoadFromFileException extends DaoException {
    public LoadFromFileException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
