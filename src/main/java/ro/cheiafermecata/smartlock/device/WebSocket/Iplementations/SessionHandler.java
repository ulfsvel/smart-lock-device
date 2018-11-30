package ro.cheiafermecata.smartlock.device.WebSocket.Iplementations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import ro.cheiafermecata.smartlock.device.Config.Urls;
import ro.cheiafermecata.smartlock.device.Data.SendToDeviceMessage;
import ro.cheiafermecata.smartlock.device.Factory.MessageFactory;
import ro.cheiafermecata.smartlock.device.Factory.ResponseFactory;
import ro.cheiafermecata.smartlock.device.HardwareInteraction.SmartLock;

import java.lang.reflect.Type;

@Component
public class SessionHandler extends StompSessionHandlerAdapter {

    private final Logger logger = LogManager.getLogger(SessionHandler.class);

    private final ResponseFactory responseFactory;

    private final SmartLock smartLock;

    public SessionHandler(ResponseFactory responseFactory, SmartLock smartLock) {
        this.responseFactory = responseFactory;
        this.smartLock = smartLock;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        session.subscribe(Urls.DATA_INFLUX, new FrameHandler(session,responseFactory));
        logger.info("Subscribed to "+Urls.DATA_INFLUX);
        session.send(Urls.SEND_TO_USERS, MessageFactory.getInfoMessage(this.smartLock.getState()));
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
        session.send(Urls.SEND_TO_USERS, MessageFactory.getErrorMessage(exception.getMessage()));
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return SendToDeviceMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        SendToDeviceMessage message = (SendToDeviceMessage) payload;
        logger.info("Received : " + message.getAction() + " for : " + message.getDeviceId() + " at : " + message.getTime());
    }
}