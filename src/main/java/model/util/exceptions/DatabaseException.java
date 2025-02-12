package model.util.exceptions;

public class DatabaseException extends CustomExceptions {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
    
}