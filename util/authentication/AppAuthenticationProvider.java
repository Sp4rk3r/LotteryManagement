package be.thibaulthelsmoortel.lotterymanagement.util.authentication;

import static java.util.stream.Collectors.toList;

import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.exceptions.WebBaseException;
import be.thibaulthelsmoortel.lotterymanagement.model.User;
import be.thibaulthelsmoortel.lotterymanagement.session.UserRoles;
import com.vaadin.server.VaadinSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

/**
 * Authentication provider for the application.
 *
 * @author Thibault Helsmoortel
 */
@Component("authenticationProvider")
public class AppAuthenticationProvider implements AuthenticationProvider {

    private final AppDelegate appDelegate;
    private final Map<Authentication, VaadinSession> authentications;
    private SessionRegistry sessionRegistry;

    @Autowired
    public AppAuthenticationProvider(AppDelegate appDelegate) {
        this.appDelegate = appDelegate;
        this.authentications = new HashMap<>();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Authentication newAuthentication;

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user;
        try {
            user = appDelegate.authenticateUser(name, password);
        } catch (WebBaseException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
        if (user != null) {
            user.setUsername(name);
            user.setPassword(password);
            List<GrantedAuthority> grantedAuths = userRolesToGrantedAuthorities(user.getWebRoles());

            newAuthentication = new UsernamePasswordAuthenticationToken(user, password, grantedAuths);

            authentications.put(newAuthentication, VaadinSession.getCurrent());

            sessionRegistry.registerNewSession(String.valueOf(UUID.randomUUID()), user);
        } else {
            throw new BadCredentialsException("Could not login due to invalid credentials.");
        }

        return newAuthentication;
    }

    public static List<GrantedAuthority> userRolesToGrantedAuthorities(List<UserRoles> webRoles) {
        return webRoles.stream().map(userRole -> new SimpleGrantedAuthority(userRole.toString())).collect(toList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public SessionRegistry getSessionRegistry() {
        return sessionRegistry;
    }

    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }
}