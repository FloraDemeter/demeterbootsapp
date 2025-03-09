package demeterboots.models.util.exceptions;

public class PaymentTypeException extends CustomExceptions {

    public PaymentTypeException(String message) {
        super(message);
    }

    public PaymentTypeException(String message, Throwable cause) {
        super(message, cause);
    }
    
}