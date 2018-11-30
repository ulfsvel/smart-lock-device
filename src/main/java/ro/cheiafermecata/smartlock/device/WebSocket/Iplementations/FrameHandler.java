package ro.cheiafermecata.smartlock.device.WebSocket.Iplementations;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import ro.cheiafermecata.smartlock.device.Config.Urls;
import ro.cheiafermecata.smartlock.device.Data.SendToDeviceMessage;
import ro.cheiafermecata.smartlock.device.Factory.MessageFactory;
import ro.cheiafermecata.smartlock.device.Factory.ResponseFactory;

import java.lang.reflect.Type;

@Component
public class FrameHandler implements StompFrameHandler {
    
    private final StompSession stompSession;
    
    private final ResponseFactory responseFactory;

    public FrameHandler(StompSession stompSession, ResponseFactory responseFactory) {
        this.stompSession = stompSession;
        this.responseFactory = responseFactory;
    }

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return SendToDeviceMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
        SendToDeviceMessage message = (SendToDeviceMessage) o;
        stompSession.send(Urls.SEND_TO_USERS, MessageFactory.getMessage(this.responseFactory.get(message)));
    }
    

}
