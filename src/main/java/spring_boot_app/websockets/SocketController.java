package spring_boot_app.websockets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import spring_boot_app.websockets.interceptors.HttpSessionIdHandshakeInterceptor;

/**
 * Central controller that communicates with the angular UI through websocket
 * and the STOMP protocol.
 */
@Controller
public class SocketController {
    private final static Logger log = LogManager.getLogger(SocketController.class);
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public SocketController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Leaving for test
    @MessageMapping("/shake-hands/req")
    @SendTo("/shake-hands/reply")
    public String processMessageFromClient(@Payload String message) throws Exception {
        log.debug("message: " + message);
        return "Hi client! Your session: " + HttpSessionIdHandshakeInterceptor.publicAttr.get("sessionId");
    }

    // ExceptionHandler
    @MessageExceptionHandler
    public String handleException(Throwable exception) {
        messagingTemplate.convertAndSend("/errors", exception.getMessage());
        log.error("[handleException] error: occurred in Websockethandler:", exception);
        return exception.getMessage();
    }

    // Devices related events req/res
    @MessageMapping("/request/test")
    @SendTo("/response/test")
    public String getTest(@Payload String req) {
        log.info("[getTest] WS got the following request: {}", req);
        return "Hello " + req;
    }
}
