package be.thibaulthelsmoortel.lotterymanagement.web.components.notifications;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

/**
 * Notification component for messages.
 *
 * @author Thibault Helsmoortel
 */
public class LMMessage extends Notification {

    public LMMessage(String caption) {
        super(caption);
        init();
    }

    private void init() {
        setDelayMsec(3000);
        setPosition(Position.BOTTOM_RIGHT);
    }

    public LMMessage(String caption, Type type) {
        super(caption, type);
        init();
    }

    public LMMessage(String caption, String description) {
        super(caption, description);
        init();
    }

    public LMMessage(String caption, String description, Type type) {
        super(caption, description, type);
        init();
    }

    public LMMessage(String caption, String description, Type type, boolean htmlContentAllowed) {
        super(caption, description, type, htmlContentAllowed);
        init();
    }

    public void show() {
        show(Page.getCurrent());
    }

}
