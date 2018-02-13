package be.thibaulthelsmoortel.lotterymanagement.web.components.notifications;

import be.thibaulthelsmoortel.lotterymanagement.web.styling.LMTheme;

/**
 * Notification component for warning messages.
 *
 * @author Thibault Helsmoortel
 */
public class LMWarningMessage extends LMMessage {

    public LMWarningMessage(String caption) {
        super(caption, Type.HUMANIZED_MESSAGE);
        setStyleName(LMTheme.NOTIFICATION_WARNING);
    }

    public LMWarningMessage(String caption, String description) {
        super(caption, description, Type.HUMANIZED_MESSAGE);
        setStyleName(LMTheme.NOTIFICATION_WARNING);
    }

}
