package ro.cheiafermecata.smartlock.device.Factory;

import ro.cheiafermecata.smartlock.device.Data.Events;
import ro.cheiafermecata.smartlock.device.Data.SendToUsersMessage;

public class MessageFactory {

    public static SendToUsersMessage getErrorMessage(String errorMessage) {
        return new SendToUsersMessage(Events.ERROR.toString(), errorMessage);
    }

    public static SendToUsersMessage getMessage(Events event) {
        switch (event) {
            case OPEN:
                return MessageFactory.getOpenMessage();
            case CLOSE:
                return MessageFactory.getCloseMessage();
            case ERROR:
            default:
                return MessageFactory.getErrorMessage("An error occurred");
        }
    }

    private static SendToUsersMessage getOpenMessage() {
        return new SendToUsersMessage(Events.OPEN.toString(), "Device OPENED successfully");
    }

    private static SendToUsersMessage getCloseMessage() {
        return new SendToUsersMessage(Events.CLOSE.toString(), "Device CLOSED successfully");
    }

    public static SendToUsersMessage getInfoMessage(Events event) {
        return new SendToUsersMessage(event.toString(), "The current state is " + event.toString());
    }

}
