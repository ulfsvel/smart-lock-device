package ro.cheiafermecata.smartlock.device.Factory;

import org.springframework.stereotype.Component;
import ro.cheiafermecata.smartlock.device.Data.Events;
import ro.cheiafermecata.smartlock.device.Data.SendToDeviceMessage;
import ro.cheiafermecata.smartlock.device.HardwareInteraction.SmartLock;

@Component
public class ResponseFactory {

    private final SmartLock smartLock;

    public ResponseFactory(SmartLock smartLock) {
        this.smartLock = smartLock;
    }

    public Events get(SendToDeviceMessage sendToDeviceMessage) {
        Events event = Events.valueOf(sendToDeviceMessage.getAction());
        switch (event) {
            case OPEN_REQUEST:
                return this.smartLock.open();
            case CLOSE_REQUEST:
                return this.smartLock.close();
            default:
                return Events.ERROR;
        }
    }

}
