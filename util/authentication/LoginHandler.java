package be.thibaulthelsmoortel.lotterymanagement.util.authentication;

import be.thibaulthelsmoortel.lotterymanagement.model.User;
import be.thibaulthelsmoortel.lotterymanagement.web.components.authentication.LoginForm;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Class responsible for handling the actual login.
 *
 * @author Thibault Helsmoortel
 */
@SpringComponent
@VaadinSessionScope
@Log4j2
public class LoginHandler {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginHandler(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void handleLogin(LoginForm loginForm, Runnable loginTask) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsernameValue(), loginForm.getPasswordValue()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (AuthenticationUtils.isAuthenticated()) {
                log.info("User '" + ((User) authentication.getPrincipal()).getUsername() + "' successfully logged in.");
                loginTask.run();
            }
        } catch (AuthenticationException e) {
            log.warn("Login failed for user: " + loginForm.getUsernameValue());
            loginForm.showLoginError(e);
        }

    }

}
