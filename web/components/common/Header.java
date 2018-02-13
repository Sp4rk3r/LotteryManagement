package be.thibaulthelsmoortel.lotterymanagement.web.components.common;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

import be.thibaulthelsmoortel.lotterymanagement.web.styling.LMTheme;
import be.thibaulthelsmoortel.lotterymanagement.web.views.home.HomeViewImpl;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

/**
 * Header component to be shown on every {@link be.thibaulthelsmoortel.lotterymanagement.web.views.LMPage}
 *
 * @author Thibault Helsmoortel
 * @since 9/10/2017
 */
public class Header extends HorizontalLayout {

    private Button btnAppTitle;

    public Header() {
        createLayout();
        addListeners();

        setPrimaryStyleName(LMTheme.HEADER);
        setWidth(100, Unit.PERCENTAGE);
    }

    private void createLayout() {
        btnAppTitle = new Button(getMessage("header.appTitle"));

        btnAppTitle.addStyleName(LMTheme.BUTTON_BORDERLESS_COLORED);
        addComponent(btnAppTitle);
    }

    private void addListeners() {
        btnAppTitle.addClickListener(event -> {
            Page current = Page.getCurrent();
            String currLocation = current.getLocation().toString();
            if (!currLocation.endsWith("/") && !currLocation.endsWith("/#!" + HomeViewImpl.VIEW_NAME)) {
                current.setLocation("/");
            }
        });
    }
}
