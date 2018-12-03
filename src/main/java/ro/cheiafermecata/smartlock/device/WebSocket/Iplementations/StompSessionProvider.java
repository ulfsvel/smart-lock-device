package ro.cheiafermecata.smartlock.device.WebSocket.Iplementations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import ro.cheiafermecata.smartlock.device.Data.Credentials;
import ro.cheiafermecata.smartlock.device.Repository.CredentialsRepository;
import ro.cheiafermecata.smartlock.device.Repository.UrlRepository;
import ro.cheiafermecata.smartlock.device.WebSocket.SessionProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class StompSessionProvider implements SessionProvider {

    private StompSession session;

    private final SessionHandler sessionHandler;

    private final CredentialsRepository credentialsRepository;

    private final UrlRepository urlRepository;

    private final Logger logger = LogManager.getLogger(StompSessionProvider.class);

    public StompSessionProvider(SessionHandler sessionHandler, CredentialsRepository credentialsRepository, UrlRepository urlRepository) {
        this.sessionHandler = sessionHandler;
        this.credentialsRepository = credentialsRepository;
        this.urlRepository = urlRepository;
    }

    private StompSession connect(
            String username,
            String device,
            String password
    ) throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();

        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(client));

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.set("username", username);
        stompHeaders.set("password", password);
        stompHeaders.set("device", device);

        WebSocketHttpHeaders httpHeaders = new WebSocketHttpHeaders();
        httpHeaders.add("username",username);
        httpHeaders.add("password",password);
        httpHeaders.add("device",device);

        return stompClient.connect(urlRepository.wsUrl(), httpHeaders, stompHeaders, this.sessionHandler).get();
    }

    public StompSession getSession() {
        if(this.session == null){
            try{
                Credentials credentials = this.credentialsRepository.get();
                this.session = this.connect(credentials.getUsername(),credentials.getDevice().toString(),credentials.getPassword());
            }catch (Exception e){
                this.logger.error(e);
            }
        }
        return this.session;
    }

    @Override
    public void disconnect(){
        if(this.session != null){
            if(this.session.isConnected()){
                this.session.disconnect();
            }
            this.session = null;
        }
    }

}
