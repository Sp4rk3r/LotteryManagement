package be.thibaulthelsmoortel.lotterymanagement.web.views.home;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

import be.thibaulthelsmoortel.lotterymanagement.util.authentication.AuthenticationUtils;
import be.thibaulthelsmoortel.lotterymanagement.util.authentication.LoginHandler;
import be.thibaulthelsmoortel.lotterymanagement.web.RootUI;
import be.thibaulthelsmoortel.lotterymanagement.web.components.authentication.LoginForm;
import be.thibaulthelsmoortel.lotterymanagement.web.presenters.home.HomePresenter;
import be.thibaulthelsmoortel.lotterymanagement.web.views.LMPage;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Actual home view.
 *
 * @author Thibault Helsmoortel
 */
@SpringView(name = HomeViewImpl.VIEW_NAME, ui = RootUI.class)
public class HomeViewImpl extends LMPage implements HomeView, View {

    public static final String VIEW_NAME = "home";

    private final transient List<HomeViewListener> listeners = new ArrayList<>();

    private final transient HomePresenter presenter;

    @Autowired
    public HomeViewImpl(HomePresenter presenter, LoginHandler loginHandler) {
        this.presenter = presenter;
        presenter.init(this);

        if (!AuthenticationUtils.isAuthenticated()) {
            LoginForm loginForm = new LoginForm(loginHandler, this);
            loginForm.setCaption(getMessage("authentication.login"));
            addComponents(loginForm);
        }
    }

    @Override
    public void addListener(HomeViewListener listener) {
        listeners.add(listener);
    }
}
