package be.thibaulthelsmoortel.lotterymanagement.i18n;

import be.thibaulthelsmoortel.lotterymanagement.util.CookieUtils;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringComponent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.util.Locale;
import java.util.stream.Collectors;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.Language.ENGLISH;
import static be.thibaulthelsmoortel.lotterymanagement.i18n.Language.getLanguages;

/**
 * Class responsible for delivering localized application messages.
 *
 * @author Thibault Helsmoortel
 * @since 15 Sep 2017
 */
@SpringComponent
@Log4j2
public final class AppMessageSource implements Serializable {

    private static MessageSource messageSource;

    private AppMessageSource() {
    }

    /**
     * Returns a localized message based on a given key and optional arguments. When the key isn't found in the
     * messages, the key will be returned.
     *
     * @param key the message property key
     * @param arguments optional arguments that replaces {}'s.
     * @return the localized message or the key when the message wasn't found
     */
    public static String getMessage(String key, Object... arguments) {
        String translation = key;

        try {
            detectOrSetupLanguage();

            Locale locale;
            VaadinSession session = VaadinSession.getCurrent();
            if (session != null) {
                locale = session.getLocale();
            } else {
                locale = Locale.ENGLISH;
            }
            translation = messageSource.getMessage(key, arguments, locale);
        } catch (NoSuchMessageException | NullPointerException e) {
            log.error(e.getMessage());
        }

        return translation;
    }

    private static void detectOrSetupLanguage() {
        Cookie languageCookie = CookieUtils.getCookie(CookieUtils.LANGUAGE_COOKIE_NAME);

        String defaultLanguage;
        if (languageCookie == null) {
            // Determine default language
            defaultLanguage = VaadinService.getCurrentRequest().getLocale().getLanguage();
            languageCookie = new Cookie(CookieUtils.LANGUAGE_COOKIE_NAME, defaultLanguage);

            if (!getLanguages().stream().map(Language::getCode).collect(Collectors.toList())
                    .contains(defaultLanguage)) {
                defaultLanguage = ENGLISH.getCode();
            }

            Locale locale = new Locale(defaultLanguage, "");
            VaadinSession.getCurrent().setLocale(locale);

            languageCookie.setMaxAge(CookieUtils.DEFAULT_COOKIE_AGE);
            languageCookie.setPath("/");
            CookieUtils.saveCookie(languageCookie);
        } else {
            Locale locale = new Locale(languageCookie.getValue(), "");
            VaadinSession.getCurrent().setLocale(locale);
        }
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        AppMessageSource.messageSource = messageSource;
    }
}