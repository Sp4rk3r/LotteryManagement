package be.thibaulthelsmoortel.lotterymanagement.web.views.cookiepolicy;

import be.thibaulthelsmoortel.lotterymanagement.exceptions.UserNotAuthenticatedException;
import be.thibaulthelsmoortel.lotterymanagement.i18n.Language;
import be.thibaulthelsmoortel.lotterymanagement.model.CookiePolicy;
import be.thibaulthelsmoortel.lotterymanagement.model.User;
import be.thibaulthelsmoortel.lotterymanagement.session.SessionUtils;
import be.thibaulthelsmoortel.lotterymanagement.util.authentication.AuthenticationUtils;
import be.thibaulthelsmoortel.lotterymanagement.web.RootUI;
import be.thibaulthelsmoortel.lotterymanagement.web.presenters.cookiePolicy.CookiePolicyPresenter;
import be.thibaulthelsmoortel.lotterymanagement.web.styling.LMTheme;
import be.thibaulthelsmoortel.lotterymanagement.web.views.LMPage;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.label.RichText;

import java.util.ArrayList;
import java.util.List;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;
import static be.thibaulthelsmoortel.lotterymanagement.util.CookieUtils.LANGUAGE_COOKIE_NAME;
import static be.thibaulthelsmoortel.lotterymanagement.util.CookieUtils.getCookie;

/**
 * Actual cookie policy view.
 *
 * @author Thibault Helsmoortel
 */
@SpringView(name = CookiePolicyViewImpl.VIEW_NAME, ui = RootUI.class)
public class CookiePolicyViewImpl extends LMPage implements CookiePolicyView, View {

    public static final String VIEW_NAME = "cookiePolicy";

    private final transient List<CookiePolicyViewListener> listeners = new ArrayList<>();

    private final transient CookiePolicyPresenter presenter;

    private final Label lblTitle;

    @Autowired
    public CookiePolicyViewImpl(CookiePolicyPresenter presenter) {
        this.presenter = presenter;
        presenter.init(this);

        setTitle(getMessage("cookiePolicy.title"));

        lblTitle = new Label(getMessage("cookiePolicy.title"));
        lblTitle.addStyleName(LMTheme.LABEL_H1);
        addComponent(lblTitle);

        boolean isAdmin = false;
        try {
            isAdmin = SessionUtils.getAuthenticatedUser().isAdmin();
        } catch (UserNotAuthenticatedException e) {
            if (!SessionUtils.isAnonymousUser()) {
                showError("cookiePolicy.error.notAnAdmin.title", "cookiePolicy.error.notAnAdmin.content");
            }
        }

        if (AuthenticationUtils.isAuthenticated() && isAdmin) {
            RichTextArea taEditor = new RichTextArea();
            taEditor.setSizeFull();
            CookiePolicy cookiePolicy = presenter.getCookiePolicy();
            taEditor.setValue(cookiePolicy.getPolicyHtml());

            Button btnSave = new Button(getMessage("cookiePolicy.save"));
            btnSave.addClickListener(event -> {
                CookiePolicy policy = new CookiePolicy();
                policy.setLanguage(Language.fromCode(getLanguageCode()));
                policy.setPolicyHtml(taEditor.getValue());
                presenter.updateCookiePolicy(policy);
            });

            addComponents(taEditor, btnSave);
        } else {
            // XSS Safe rich text
            RichText rtPolicy = new RichText();
            rtPolicy.withSafeHtml(presenter.getCookiePolicy().getPolicyHtml());
            rtPolicy.setSizeFull();
            addComponent(rtPolicy);
        }
    }

    private String getLanguageCode() {
        return getCookie(LANGUAGE_COOKIE_NAME).getValue();
    }

    @Override
    public void addListener(CookiePolicyViewListener listener) {
        listeners.add(listener);
    }
}