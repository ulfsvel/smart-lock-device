package ro.cheiafermecata.smartlock.device.HardwareInteraction.Implementations;

import org.springframework.stereotype.Component;
import ro.cheiafermecata.smartlock.device.Data.Events;
import ro.cheiafermecata.smartlock.device.HardwareInteraction.SmartLock;

@Component
public class SmartLockDummy implements SmartLock {
    @Override
    public Events open() {
        return Events.OPEN;
    }

    @Override
    public Events close() {
        return Events.CLOSE;
    }

    @Override
    public Events getState() {
        return Events.CLOSE;
    }
}
