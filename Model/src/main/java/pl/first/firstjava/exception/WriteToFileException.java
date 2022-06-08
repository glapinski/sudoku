package pl.first.firstjava.exception;

public class WriteToFileException extends DaoException {
    public WriteToFileException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
