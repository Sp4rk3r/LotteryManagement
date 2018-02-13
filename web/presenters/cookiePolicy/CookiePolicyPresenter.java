package be.thibaulthelsmoortel.lotterymanagement.web.presenters.cookiePolicy;

import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.exceptions.WebBaseException;
import be.thibaulthelsmoortel.lotterymanagement.i18n.Language;
import be.thibaulthelsmoortel.lotterymanagement.model.CookiePolicy;
import be.thibaulthelsmoortel.lotterymanagement.web.views.LMView.ErrorType;
import be.thibaulthelsmoortel.lotterymanagement.web.views.cookiepolicy.CookiePolicyView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

import static be.thibaulthelsmoortel.lotterymanagement.util.CookieUtils.LANGUAGE_COOKIE_NAME;
import static be.thibaulthelsmoortel.lotterymanagement.util.CookieUtils.getCookie;

/**
 * Presenter for {@link CookiePolicyView}.
 *
 * @author Thibault Helsmoortel
 */
@SpringComponent
@ViewScope
public class CookiePolicyPresenter implements CookiePolicyView.CookiePolicyViewListener {

    private final AppDelegate appDelegate;
    private CookiePolicyView view;

    @Autowired
    public CookiePolicyPresenter(AppDelegate appDelegate) {
        this.appDelegate = appDelegate;
    }

    public void init(CookiePolicyView view) {
        if (this.view == null) {
            this.view = view;
        }

        view.addListener(this);
    }

    public CookiePolicy getCookiePolicy() {
        try {
            String languageCode = getCookie(LANGUAGE_COOKIE_NAME).getValue();
            return appDelegate.getCookiePolicy(Language.fromCode(languageCode));
        } catch (WebBaseException e) {
            view.showGeneralError(ErrorType.READ);
            return null;
        }
    }

    public void updateCookiePolicy(CookiePolicy cookiePolicy) {
        try {
            appDelegate.updateCookiePolicy(cookiePolicy);
            view.showSuccessMessage("cookiePolicy.policyUpdate.success.title", "cookiePolicy.policyUpdate.success.content");
        } catch (WebBaseException e) {
            view.showGeneralError(ErrorType.UPDATE);
        }
    }

}
