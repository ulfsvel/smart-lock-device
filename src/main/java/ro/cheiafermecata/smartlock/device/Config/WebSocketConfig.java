package ro.cheiafermecata.smartlock.device.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompSession;
import ro.cheiafermecata.smartlock.device.WebSocket.SessionProvider;

@Configuration
public class WebSocketConfig {

    private final SessionProvider sessionProvider;

    public WebSocketConfig(SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    @Bean
    StompSession stompSession(){
        return sessionProvider.getSession();
    }


}
