package demeterboots.models.util.exceptions;

public class CustomerException extends CustomExceptions {

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }
    
}