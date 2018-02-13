package be.thibaulthelsmoortel.lotterymanagement.util.authentication;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Authentication utilities.
 *
 * @author Thibault Helsmoortel
 */
public final class AuthenticationUtils {

    private AuthenticationUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                // When Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken);
    }
}
