package spring_boot_app.websockets.interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Class used to maintain a record of the currently open
 * sessions with clients and to therefore be able to
 * identify which clients to respond to.
 *
 * @implNote Currently only used for websocket debugging and identifying open connections
 */
public class HttpSessionIdHandshakeInterceptor implements HandshakeInterceptor {
    private final static Logger log = LogManager.getLogger(HttpSessionIdHandshakeInterceptor.class);

    public static Map<String, Object> publicAttr;

    /***
     * Stores the session ID of a client who has established a ws connection
     */
    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) throws Exception
    {
        log.info("[HttpSessionIdHandshakeInterceptor][beforeHandshake] Handshake interceptor called");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest
                    = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest
                    .getServletRequest().getSession();
            attributes.put("sessionId", session.getId());
            publicAttr = attributes;
            log.info("Handshake from " + session.getId());
        }
        return true;
    }

    /**
     * Does nothing
     *
     * //@TODO: correct behaviour?  What if ws handshake fails
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                                        WebSocketHandler wsHandler, Exception ex) {
    }
}
