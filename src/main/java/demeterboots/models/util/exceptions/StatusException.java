package demeterboots.models.util.exceptions;

public class StatusException extends CustomExceptions {

    public StatusException(String message) {
        super(message);
    }

    public StatusException(String message, Throwable cause) {
        super(message, cause);
    }
    
}