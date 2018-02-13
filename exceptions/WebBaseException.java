package be.thibaulthelsmoortel.lotterymanagement.exceptions;

/**
 * Exception raised by {@link be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate} for failed transactions.
 *
 * @author Thibault Helsmoortel
 */
public class WebBaseException extends BusinessException {

    public WebBaseException() {
        super("A data transaction failed.");
    }

    public WebBaseException(String message) {
        super(message);
    }

    public WebBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebBaseException(Throwable cause) {
        super(cause);
    }

}
