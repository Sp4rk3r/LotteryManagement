package be.thibaulthelsmoortel.lotterymanagement.web.components.notifications;

import be.thibaulthelsmoortel.lotterymanagement.util.CookieUtils;
import be.thibaulthelsmoortel.lotterymanagement.web.styling.LMTheme;
import com.vaadin.server.Page;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import java.util.Optional;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

/**
 * Cookie notification component. Needed to be a custom component in order to add close functionality.
 *
 * @author Thibault Helsmoortel
 */
public class CookieNoticeNotification extends Window {

    public CookieNoticeNotification() {
        super();
        setCaption(getMessage("app.cookieNotice.content"));

        addStyleName(LMTheme.NOTIFICATION_COOKIES);
        setModal(false);
        setCaptionAsHtml(true);
        setResizable(false);
        setDraggable(false);

        addCloseListener(e -> {
            CookieUtils
                    .saveCookie(CookieUtils.COOKIES_ACCEPTED_NAME, "1", Optional.of(CookieUtils.DEFAULT_COOKIE_AGE));
            Page.getCurrent().reload(); // Make sure the window is closed
        });
    }

    public void openIfNotClosedBefore() {
        if (!CookieUtils.userHasAcceptedCookies()) {
            UI.getCurrent().access(() -> UI.getCurrent().addWindow(this));
        }
    }


}