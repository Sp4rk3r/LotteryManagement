package be.thibaulthelsmoortel.lotterymanagement.web.views.app;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

import be.thibaulthelsmoortel.lotterymanagement.delegates.AppDelegate;
import be.thibaulthelsmoortel.lotterymanagement.web.AppUI;
import be.thibaulthelsmoortel.lotterymanagement.web.components.user.menubar.UserMenuBar;
import be.thibaulthelsmoortel.lotterymanagement.web.views.LMPage;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.dashboard.DashboardViewImpl;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.draw.DrawOverviewViewImpl;
import be.thibaulthelsmoortel.lotterymanagement.web.views.app.player.PlayerOverviewViewImpl;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;

/**
 * Component representing a page residing inside the {@link AppUI}.
 * This page adds the {@link be.thibaulthelsmoortel.lotterymanagement.web.components.user.menubar.UserMenuBar} to the view.
 *
 * @author Thibault Helsmoortel
 */
public class AppPage extends LMPage {

    private final HorizontalLayout hlNavigation;
    private final MenuBar navigationBar;
    private final UserMenuBar userMenuBar;

    public AppPage(AppDelegate appDelegate) {
        this.hlNavigation = new HorizontalLayout();
        this.navigationBar = new MenuBar();
        this.userMenuBar = new UserMenuBar(this, appDelegate);

        Navigator navigator = UI.getCurrent().getNavigator();
        navigationBar.addItem(getMessage("dashboard.title"), command -> {
            navigator.navigateTo(DashboardViewImpl.VIEW_NAME);
        });

        navigationBar.addItem(getMessage("drawOverview.title"), command -> {
            navigator.navigateTo(DrawOverviewViewImpl.VIEW_NAME);
        });

        navigationBar.addItem(getMessage("playerOverview.title"), command -> {
            navigator.navigateTo(PlayerOverviewViewImpl.VIEW_NAME);
        });

        hlNavigation.addComponents(userMenuBar, navigationBar);
        addComponent(hlNavigation);
    }

}