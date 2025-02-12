package model.util.exceptions;

public class OrderException extends CustomExceptions {

    public OrderException(String message) {
        super(message);
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }
    
}