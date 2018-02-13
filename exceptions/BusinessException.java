package be.thibaulthelsmoortel.lotterymanagement.exceptions;

/**
 * Checked exception to be used throughout the application.
 *
 * @author Thibault Helsmoortel
 */
public class BusinessException extends Exception {

    public BusinessException() {
        super("Something went wrong.");
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

}
