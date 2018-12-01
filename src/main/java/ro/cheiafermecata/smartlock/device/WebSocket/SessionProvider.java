package ro.cheiafermecata.smartlock.device.WebSocket;

import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;

@Component
public interface SessionProvider {

    StompSession getSession();

    void disconnect();

}
