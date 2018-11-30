package ro.cheiafermecata.smartlock.device.HardwareInteraction;

import org.springframework.stereotype.Component;
import ro.cheiafermecata.smartlock.device.Data.Events;

@Component
public interface SmartLock {

    Events open();

    Events close();

    Events getState();

}
