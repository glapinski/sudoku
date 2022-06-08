package pl.first.firstjava.exception;

import java.io.IOException;

public class DaoException extends IOException {
    public DaoException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

}
