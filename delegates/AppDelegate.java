package be.thibaulthelsmoortel.lotterymanagement.delegates;

import be.thibaulthelsmoortel.lotterymanagement.exceptions.WebBaseException;
import be.thibaulthelsmoortel.lotterymanagement.i18n.Language;
import be.thibaulthelsmoortel.lotterymanagement.model.*;
import be.thibaulthelsmoortel.lotterymanagement.services.*;
import com.vaadin.spring.annotation.SpringComponent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Main delegate class of the application. All transactions go through here.
 *
 * @author Thibault Helsmoortel
 * @author Koen Rombout
 */
@SpringComponent
@Log4j2
public class AppDelegate {

    private final AppStartupService appStartupService;
    private final UserService userService;
    private final DrawService drawService;
    private final CookiePolicyService cookiePolicyService;
    private final PlayerService playerService;
    private final DrawEnrollmentService drawEnrollmentService;

    @Autowired
    public AppDelegate(AppStartupService appStartupService, UserService userService, DrawService drawService, CookiePolicyService cookiePolicyService, PlayerService playerService, DrawEnrollmentService drawEnrollmentService) {
        this.appStartupService = appStartupService;
        this.userService = userService;
        this.drawService = drawService;
        this.cookiePolicyService = cookiePolicyService;
        this.playerService = playerService;
        this.drawEnrollmentService = drawEnrollmentService;
    }

    public void saveAppStartup(AppStartup appStartup) throws WebBaseException {
        try {
            appStartupService.saveAppStartup(appStartup);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WebBaseException(e.getMessage(), e);
        }

    }

    public void logout() throws WebBaseException {
        try {
            userService.logout();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WebBaseException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unused")
    public void registerNewUser(String username, String firstName, String lastName, String email, String password) throws WebBaseException {
        try {
            userService.registerNewUser(username, firstName, lastName, email, password);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WebBaseException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unused")
    public boolean isExistingUser(String username, String email) throws WebBaseException {
        try {
            return userService.getUser(username, email) != null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WebBaseException(e.getMessage());
        }
    }

    public User authenticateUser(String username, String password) throws WebBaseException {
        try {
            return userService.authenticate(username, password);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WebBaseException(e.getMessage());
        }
    }

    public List<Draw> getAllDraws() throws WebBaseException {
        try {
            return drawService.getAllDraws();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WebBaseException(e.getMessage());
        }
    }

    public List<Player> getAllPlayers() throws WebBaseException {
        try {
            return playerService.getAllPlayers();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WebBaseException(e.getMessage());
        }
    }

    public CookiePolicy getCookiePolicy(Language language) throws WebBaseException {
        try {
            return cookiePolicyService.getCookiePolicy(language);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WebBaseException(e.getMessage());
        }
    }

    public void updateCookiePolicy(CookiePolicy cookiePolicy) throws WebBaseException {
        try {
            cookiePolicyService.updateCookiePolicy(cookiePolicy);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WebBaseException(e.getMessage());
        }
    }

    public void createDrawEnrollment(DrawEnrollment enrollment) throws WebBaseException {
        try {
            drawEnrollmentService.createDrawEnrollment(enrollment);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WebBaseException(e.getMessage());
        }
    }

    public void createDraw(Draw draw) throws WebBaseException {
        try {
            drawService.createDraw(draw);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new WebBaseException(e.getMessage());
        }
    }
}
