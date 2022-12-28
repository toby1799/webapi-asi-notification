package ch.asinfotrack.webapinotification.service;

import ch.asinfotrack.webapinotification.helper.MSGTYPE;
import ch.asinfotrack.webapinotification.model.Notification;
import ch.asinfotrack.webapinotification.websocket.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

import static org.springframework.data.util.TypeUtils.type;

@Service
public class WebSocketService {

    public void sendNotification(Notification notification, MSGTYPE type) throws IOException {
        List<WebSocketSession> activeSessions = SocketHandler.sessions;

        for(WebSocketSession webSocketSession : activeSessions) {
            if (webSocketSession.isOpen()) {
                String msgToSend = prepareMsg(notification, type);
                System.out.println(msgToSend);
                webSocketSession.sendMessage(new TextMessage(msgToSend));
            }
        }
    }

    private String prepareMsg(Notification notification, MSGTYPE type) {
        Gson gson = new Gson();

        JsonElement jsonElement = gson.toJsonTree(notification);

        JsonObject msg = new JsonObject();
        msg.add(type.toString(), jsonElement);

        /*String msg = type.toString() + ": " + gson.toJson(notification);*/

        return gson.toJson(msg);
    }
}
