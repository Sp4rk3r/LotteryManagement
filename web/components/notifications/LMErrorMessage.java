package be.thibaulthelsmoortel.lotterymanagement.web.components.notifications;

/**
 * Notification component for errors.
 *
 * @author Thibault Helsmoortel
 */
public class LMErrorMessage extends LMMessage {

    public LMErrorMessage(String caption) {
        super(caption, Type.ERROR_MESSAGE);
    }

    public LMErrorMessage(String caption, String description) {
        super(caption, description, Type.ERROR_MESSAGE);
    }

}
