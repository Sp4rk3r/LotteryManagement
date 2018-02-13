package be.thibaulthelsmoortel.lotterymanagement.web.components.user.menubar;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;
import static be.thibaulthelsmoortel.lotterymanagement.util.CookieUtils.LANGUAGE_COOKIE_NAME;
import static be.thibaulthelsmoortel.lotterymanagement.util.CookieUtils.getCookie;
import static be.thibaulthelsmoortel.lotterymanagement.util.CookieUtils.saveCookie;

import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.exceptions.UserNotAuthenticatedException;
import be.thibaulthelsmoortel.lotterymanagement.i18n.Language;
import be.thibaulthelsmoortel.lotterymanagement.model.User;
import be.thibaulthelsmoortel.lotterymanagement.session.SessionUtils;
import be.thibaulthelsmoortel.lotterymanagement.util.authentication.LogoutHandler;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.AppPage;
import be.thibaulthelsmoortel.lotterymanagement.web.views.authentication.login.LoginViewImpl;
import com.vaadin.server.Page;
import com.vaadin.ui.MenuBar;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;

/**
 * Menu bar including general user actions (like 'logout').
 *
 * @author Thibault Helsmoortel
 */
@Log4j2
public class UserMenuBar extends MenuBar {

    private final transient LogoutHandler logoutHandler;

    public UserMenuBar(AppPage appPage, AppDelegate appDelegate) {
        this.logoutHandler = new LogoutHandler(appPage, appDelegate);
        createUserMenuBar();
    }

    private void createUserMenuBar() {
        try {
            User user = SessionUtils.getAuthenticatedUser();

            MenuItem miDropDown = addItem(user.getFirstName() + " " + user.getLastName(), null);

            MenuItem miLanguages = miDropDown.addItem(getMessage("language.adjust"), null);

            Language currentLanguage = Language.fromCode(getCookie(LANGUAGE_COOKIE_NAME).getValue());
            for (Language language : Language.getLanguages()) {
                if (!language.equals(currentLanguage)) {
                    miLanguages.addItem(getMessage("language." + language.getCode()), (Command) -> {
                        saveCookie(LANGUAGE_COOKIE_NAME, language.getCode(), Optional.empty());
                        Page.getCurrent().reload();
                        createUserMenuBar(); // Recreate the menu bar, as the current language has now changed
                    });
                }
            }

            miDropDown.addItem(getMessage("authentication.logout"),
                    (Command) selectedItem -> logoutHandler.handleLogout());
        } catch (UserNotAuthenticatedException e) {
            log.error("Authentication not found, redirecting back to the login page.");
            Page.getCurrent().setLocation("/#!" + LoginViewImpl.VIEW_NAME); // Force to login page
        }
    }

}