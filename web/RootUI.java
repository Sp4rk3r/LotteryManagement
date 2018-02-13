package be.thibaulthelsmoortel.lotterymanagement.web;

import static be.thibaulthelsmoortel.lotterymanagement.AppConstants.META_AUTHORS;

import be.thibaulthelsmoortel.lotterymanagement.util.authentication.AuthenticationUtils;
import be.thibaulthelsmoortel.lotterymanagement.web.styling.LMTheme;
import be.thibaulthelsmoortel.lotterymanagement.web.views.authentication.login.LoginViewImpl;
import be.thibaulthelsmoortel.lotterymanagement.web.views.home.HomeViewImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.leif.headertags.Meta;

/**
 * UI entry point of the application.
 *
 * @author Thibault Helsmoortel
 */
@Theme(LMTheme.NAME)
@Viewport("width=device-width, initial-scale=1.0")
@Meta(name = "author", content = META_AUTHORS)
@SpringUI
public class RootUI extends UI {

    private final SpringViewProvider viewProvider;
    private Navigator navigator;

    @Autowired
    public RootUI(SpringViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        String state = navigator.getState();

        if (state.equals(LoginViewImpl.VIEW_NAME) && !AuthenticationUtils.isAuthenticated()) {
            navigator.navigateTo(LoginViewImpl.VIEW_NAME);
        } else if (state.isEmpty()) {
            navigator.navigateTo(HomeViewImpl.VIEW_NAME);
        }
    }
}
