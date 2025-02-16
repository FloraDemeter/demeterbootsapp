package models.util.exceptions;

public class InvoiceException extends CustomExceptions {

    public InvoiceException(String message) {
        super(message);
    }

    public InvoiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
}