package be.thibaulthelsmoortel.lotterymanagement.web.components.notifications;

import be.thibaulthelsmoortel.lotterymanagement.web.styling.LMTheme;

/**
 * Notification component for success messages.
 *
 * @author Koen Rombout
 * @author Thibault Helsmoortel
 */
public class LMSuccessMessage extends LMMessage {

    public LMSuccessMessage(String caption) {
        super(caption, Type.HUMANIZED_MESSAGE);
        setStyleName(LMTheme.NOTIFICATION_SUCCESS);
    }

    public LMSuccessMessage(String caption, String description) {
        super(caption, description, Type.HUMANIZED_MESSAGE);
        setStyleName(LMTheme.NOTIFICATION_SUCCESS);
    }
}
