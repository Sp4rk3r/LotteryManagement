package be.thibaulthelsmoortel.lotterymanagement.web;

import be.thibaulthelsmoortel.lotterymanagement.util.authentication.AuthenticationUtils;
import be.thibaulthelsmoortel.lotterymanagement.web.styling.LMTheme;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.dashboard.DashboardViewImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.leif.headertags.Meta;

import static be.thibaulthelsmoortel.lotterymanagement.AppConstants.META_AUTHORS;

/**
 * UI for the app context.
 *
 * @author Thibault Helsmoortel
 */
@Theme(LMTheme.NAME)
@Viewport("width=device-width, initial-scale=1.0")
@Meta(name = "author", content = META_AUTHORS)
@SpringUI(path = AppUI.UI_NAME)
@SpringViewDisplay
@Widgetset("org.vaadin.addons.autocomplete.Widgetset")
public class AppUI extends UI {

    public static final String UI_NAME = "/app";

    private final SpringViewProvider viewProvider;

    @Autowired
    public AppUI(SpringViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);

        if (AuthenticationUtils.isAuthenticated()) {
            // Switch to the correct view (default is dashboard)
            if (navigator.getState().isEmpty()) {
                navigator.navigateTo(DashboardViewImpl.VIEW_NAME);
            }
        } else {
            Page.getCurrent().setLocation("/");
        }

    }
}
