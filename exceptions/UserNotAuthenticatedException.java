package be.thibaulthelsmoortel.lotterymanagement.exceptions;

/**
 * Exception raised when a {@link be.thibaulthelsmoortel.lotterymanagement.model.User} is not authenticated.
 *
 * @author Thibault Helsmoortel
 */
public class UserNotAuthenticatedException extends BusinessException {

    public UserNotAuthenticatedException() {
        super("No authenticated user was found.");
    }

    public UserNotAuthenticatedException(String message) {
        super(message);
    }

    public UserNotAuthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotAuthenticatedException(Throwable cause) {
        super(cause);
    }

}
