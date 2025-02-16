package models.util.exceptions;

public class JobException extends CustomExceptions {

    public JobException(String message) {
        super(message);
    }

    public JobException(String message, Throwable cause) {
        super(message, cause);
    }
    
}