package be.thibaulthelsmoortel.lotterymanagement.util;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinService;
import lombok.extern.log4j.Log4j2;
import org.vaadin.viritin.util.BrowserCookie;

import javax.servlet.http.Cookie;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;

/**
 * Utils for cookie management.
 *
 * @author Thibault Helsmoortel
 * @since 15 Sep 2017
 */
@Log4j2
public final class CookieUtils {

    public static final String LANGUAGE_COOKIE_NAME = "LM_LANGUAGE_COOKIE";
    public static final String COOKIES_ACCEPTED_NAME = "LM_COOKIES_ACCEPTED";
    private static final int TWO_YEARS = 365 * 2;
    public static final int DEFAULT_COOKIE_AGE = toIntExact(Duration.ofDays(TWO_YEARS).getSeconds());

    private CookieUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Cookie getCookie(String cookieName) {
        VaadinRequest request = VaadinService.getCurrentRequest();
        Cookie[] cookies = request.getCookies();
        return Stream.of(cookies).filter(c -> cookieName.equals(c.getName())).findFirst().orElse(null);
    }

    public static void saveCookie(Cookie cookie) {
        saveCookie(cookie.getName(), cookie.getValue(), Optional.ofNullable(getCookieAge(cookie)));
    }

    private static Integer getCookieAge(Cookie cookie) {
        return Optional.of(cookie.getMaxAge()).orElse(DEFAULT_COOKIE_AGE);
    }

    public static void saveCookie(String cookieName, String cookieValue, Optional<Integer> cookieAge) {
        saveCookie(cookieName, cookieValue, cookieAge, VaadinService.getCurrentResponse());
    }

    public static void saveCookie(String cookieName, String cookieValue, Optional<Integer> cookieAge,
            VaadinResponse response) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(cookieAge.orElse(DEFAULT_COOKIE_AGE));
        cookie.setPath("/");
        log.debug("Saving cookie {} with value {}.", cookie.getName(), cookie.getValue());

        VaadinResponse theResponse = response;

        if (theResponse != null) {
            theResponse.addCookie(cookie);
        } else {
            theResponse = VaadinService.getCurrentResponse();
            if (theResponse != null) {
                theResponse.addCookie(cookie);
            } else {
                // Ultimately set the cookie using Viritin's BrowserCookie
                if (cookie.getMaxAge() == -1) {
                    BrowserCookie.setCookie(cookie.getName(), cookie.getValue(), cookie.getPath());
                } else {
                    BrowserCookie.setCookie(cookie.getName(), cookie.getValue(), cookie.getPath(),
                            LocalDateTime.now().plusSeconds(cookie.getMaxAge()));
                }
            }
        }
    }

    public static void saveCookie(Cookie cookie, VaadinResponse response) {
        saveCookie(cookie.getName(), cookie.getValue(), Optional.ofNullable(getCookieAge(cookie)), response);
    }

    public static boolean userHasAcceptedCookies() {
        Cookie cookie = getCookie(COOKIES_ACCEPTED_NAME);
        return cookie != null && Objects.equals(cookie.getValue(), "1");
    }
}