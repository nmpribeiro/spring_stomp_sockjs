package spring_boot_app.websockets.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Listener when a websocket session is disconnected.
 * 
 * @implNote currently only logs the disconnection in the future this might be used to properly
 * cleanup any resources that are associated with that websocket, but currently there is no need.
 *
 */
@Component
public class SessionDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {
    private final static Logger log = LogManager.getLogger(SessionDisconnectEventListener.class);

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.debug(headerAccessor.toString());
        log.debug("WebSocket::Disconnected > Session ID: " + headerAccessor.getSessionId());
        // webSocketSessionService.removeSession(event);
    }
}