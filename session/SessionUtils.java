package be.thibaulthelsmoortel.lotterymanagement.session;

import be.thibaulthelsmoortel.lotterymanagement.exceptions.UserNotAuthenticatedException;
import be.thibaulthelsmoortel.lotterymanagement.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Session utilities.
 *
 * @author Thibault Helsmoortel
 */
public final class SessionUtils {

    private static final String ANONYMOUS_USER = "anonymousUser";

    private SessionUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Returns the authenticated user, if one is found.
     *
     * @return the authenticated user, if one is found
     * @throws UserNotAuthenticatedException when the authenticated user wasn't found
     */
    public static User getAuthenticatedUser() throws UserNotAuthenticatedException {
        Object principal = getPrincipal();
        if (principal != null && !(principal instanceof User)) {
            throw new UserNotAuthenticatedException();
        }

        return (User) principal;
    }

    private static Object getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isAnonymousUser() {
        return getPrincipal().equals(ANONYMOUS_USER);
    }
}
