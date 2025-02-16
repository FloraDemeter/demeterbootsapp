package models.util.exceptions;

public class InvoiceLineException extends CustomExceptions {

    public InvoiceLineException(String message) {
        super(message);
    }

    public InvoiceLineException(String message, Throwable cause) {
        super(message, cause);
    }
    
}