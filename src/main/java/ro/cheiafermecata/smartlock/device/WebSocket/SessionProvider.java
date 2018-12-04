package ro.cheiafermecata.smartlock.device.WebSocket;

import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;

@Component
public interface SessionProvider {

    /**
     * Creates a connection to the central server
     * @return the connection to the server
     */
    StompSession getSession();

    /**
     * Terminates the current session
     */
    void disconnect();

}
