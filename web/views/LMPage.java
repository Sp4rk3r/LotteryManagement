package be.thibaulthelsmoortel.lotterymanagement.web.views;

import be.thibaulthelsmoortel.lotterymanagement.web.components.common.Footer;
import be.thibaulthelsmoortel.lotterymanagement.web.components.common.Header;
import be.thibaulthelsmoortel.lotterymanagement.web.components.notifications.CookieNoticeNotification;
import be.thibaulthelsmoortel.lotterymanagement.web.components.notifications.LMErrorMessage;
import be.thibaulthelsmoortel.lotterymanagement.web.components.notifications.LMSuccessMessage;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import java.util.Arrays;

import static be.thibaulthelsmoortel.lotterymanagement.i18n.AppMessageSource.getMessage;

/**
 * Component representing a page.
 *
 * @author Thibault Helsmoortel
 */
public class LMPage extends VerticalLayout implements LMView {

    public void setTitle(String title) {
        Page.getCurrent().setTitle(title);
    }

    private final Header header;
    private final VerticalLayout content;
    private final Footer footer;

    public LMPage() {
        CookieNoticeNotification cookieNoticeNotification = new CookieNoticeNotification();
        cookieNoticeNotification.openIfNotClosedBefore();

        this.header = new Header();
        this.content = new VerticalLayout();
        this.footer = new Footer();
        addDefaultComponents();

        setHeightUndefined();
        setMargin(false);
    }

    @Override
    public void showGeneralError(ErrorType type) {
        String title;
        String description;
        switch (type) {
            case CREATE:
                title = "error.message.create.title";
                description = "error.message.create.content";
                break;
            case READ:
                title = "error.message.read.title";
                description = "error.message.read.content";
                break;
            case UPDATE:
                title = "error.message.update.title";
                description = "error.message.update.content";
                break;
            case DELETE:
                title = "error.message.delete.title";
                description = "error.message.delete.content";
                break;
            default:
                title = "error.message.default.title";
                description = "error.message.default.content";
                break;
        }
        LMErrorMessage notification = new LMErrorMessage(
                getMessage(title),
                getMessage(description));
        notification.show();
    }

    @Override
    public void showError(String title, String content) {
        LMErrorMessage notification = new LMErrorMessage(
                getMessage(title),
                getMessage(content));
        notification.show();
    }

    @Override
    public void showError(String title) {
        LMErrorMessage notification = new LMErrorMessage(getMessage(title));
        notification.show();
    }

    @Override
    public void showSuccessMessage(String title, String description) {
        LMSuccessMessage notification = new LMSuccessMessage(
                getMessage(title),
                getMessage(description));
        notification.show();
    }

    @Override
    public void showSuccessMessage(String title) {
        LMSuccessMessage notification = new LMSuccessMessage(getMessage(title));
        notification.show();
    }

    private void addDefaultComponents() {
        if (!components.contains(header)) {
            super.addComponentAsFirst(header);
            setExpandRatio(header, 0);
        }

        super.addComponent(content);

        if (!components.contains(footer)) {
            super.addComponent(footer);
            setExpandRatio(footer, 0);
        }
    }

    @Override
    public void setComponentAlignment(Component childComponent, Alignment alignment) {
        content.setComponentAlignment(childComponent, alignment);
    }

    @Override
    public void addComponentsAndExpand(Component... components) {
        content.addComponentsAndExpand(components);
    }

    @Override
    public void addComponents(Component... components) {
        Arrays.stream(components).forEach(content::addComponent);
    }

    @Override
    public void addComponent(Component c) {
        content.addComponent(c);
    }

    @Override
    public void addComponentAsFirst(Component c) {
        content.addComponent(c);
    }

    @Override
    public void addComponent(Component c, int index) {
        content.addComponent(c, index);
    }

    @Override
    public void removeAllComponents() {
        components.stream().filter(component -> !component.equals(header) && !component.equals(content) && !component.equals(footer)).forEach(this::removeComponent);
    }

    @Override
    public void removeComponent(Component c) {
        if (c.equals(header) || c.equals(content) || c.equals(footer)) {
            throw new IllegalArgumentException(String.format("Component '%s' could not be removed", c.getClass().getSimpleName()));
        }

        super.removeComponent(c);
    }
}
