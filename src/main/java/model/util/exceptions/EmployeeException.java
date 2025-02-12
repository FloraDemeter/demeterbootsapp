package model.util.exceptions;

public class EmployeeException extends CustomExceptions {

    public EmployeeException(String message) {
        super(message);
    }

    public EmployeeException(String message, Throwable cause) {
        super(message, cause);
    }
    
}