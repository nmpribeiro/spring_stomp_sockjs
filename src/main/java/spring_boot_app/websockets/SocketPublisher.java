package spring_boot_app.websockets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * One way socket comms to send messages to the client.
 *
 * @implNote Useful for push notifications
 */
@Component
public class SocketPublisher {
    private final static Logger log = LogManager.getLogger(SocketPublisher.class);
    private final SimpMessagingTemplate webSocketTemplate;

    @Autowired
    public SocketPublisher(SimpMessagingTemplate webSocketTemplate) {
        this.webSocketTemplate = webSocketTemplate;
    }

    public void publishMessage(String destination, Object object) {
        webSocketTemplate.convertAndSend(destination, object);
    }
}
