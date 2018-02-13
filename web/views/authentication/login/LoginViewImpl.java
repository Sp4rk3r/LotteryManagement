package be.thibaulthelsmoortel.lotterymanagement.web.views.authentication.login;

import be.thibaulthelsmoortel.lotterymanagement.util.authentication.AuthenticationUtils;
import be.thibaulthelsmoortel.lotterymanagement.util.authentication.LoginHandler;
import be.thibaulthelsmoortel.lotterymanagement.web.AppUI;
import be.thibaulthelsmoortel.lotterymanagement.web.RootUI;
import be.thibaulthelsmoortel.lotterymanagement.web.components.authentication.LoginForm;
import be.thibaulthelsmoortel.lotterymanagement.web.presenters.authentication.login.LoginPresenter;
import be.thibaulthelsmoortel.lotterymanagement.web.views.LMPage;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.dashboard.DashboardViewImpl;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

/**
 * Actual login view.
 *
 * @author Thibault Helsmoortel
 */
@SpringView(name = LoginViewImpl.VIEW_NAME, ui = RootUI.class)
public class LoginViewImpl extends LMPage implements LoginView, View {

    public static final String VIEW_NAME = "login";

    private final transient List<LoginViewListener> listeners = new ArrayList<>();

    private final transient LoginPresenter presenter;

    @Autowired
    public LoginViewImpl(LoginPresenter presenter, LoginHandler loginHandler) {
        this.presenter = presenter;
        presenter.init(this);

        setTitle(getMessage("authentication.login"));

        if (AuthenticationUtils.isAuthenticated()) {
            Page.getCurrent().setLocation(AppUI.UI_NAME + "#!" + DashboardViewImpl.VIEW_NAME);
        }

        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        LoginForm loginForm = new LoginForm(loginHandler, this);
        horizontalLayout.addComponents(loginForm);
        addComponentsAndExpand(horizontalLayout);
        setComponentAlignment(horizontalLayout, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void addListener(LoginViewListener listener) {
        listeners.add(listener);
    }
}
