package model.util.exceptions;

public class OrderLineException extends CustomExceptions {

    public OrderLineException(String message) {
        super(message);
    }

    public OrderLineException(String message, Throwable cause) {
        super(message, cause);
    }
    
}