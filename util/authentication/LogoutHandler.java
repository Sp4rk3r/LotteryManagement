package be.thibaulthelsmoortel.lotterymanagement.util.authentication;

import be.thibaulthelsmoortel.lotterymanagement.AppConstants;
import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.exceptions.UserNotAuthenticatedException;
import be.thibaulthelsmoortel.lotterymanagement.exceptions.WebBaseException;
import be.thibaulthelsmoortel.lotterymanagement.model.User;
import be.thibaulthelsmoortel.lotterymanagement.session.SessionUtils;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.AppPage;
import com.vaadin.server.Page;
import lombok.extern.log4j.Log4j2;

/**
 * Class responsible for handling the actual logout.
 *
 * @author Thibault Helsmoortel
 */
@Log4j2
public class LogoutHandler {

    private final AppPage appPage;
    private final AppDelegate appDelegate;

    public LogoutHandler(AppPage appPage, AppDelegate appDelegate) {
        this.appPage = appPage;
        this.appDelegate = appDelegate;
    }

    public void handleLogout() {
        if (AuthenticationUtils.isAuthenticated()) {
            try {
                User user = SessionUtils.getAuthenticatedUser();
                log.info("Trying to logout user '{}'.", user.getUsername());
                appDelegate.logout();
                redirectToLogoutPage();
                log.info("Successfully logged out user '{}'.", user.getUsername());
            } catch (WebBaseException e) {
                appPage.showError("logout.error.general.title", "logout.error.general.content");
            } catch (UserNotAuthenticatedException e) {
                appPage.showError("logout.error.notAuthenticated.title", "logout.error.notAuthenticated.content");
            }
        } else {
            appPage.showError("logout.error.notAuthenticated.title", "logout.error.notAuthenticated.content");
        }

    }

    private void redirectToLogoutPage() {
        Page.getCurrent().setLocation(AppConstants.LOGOUT_URL);
    }

}