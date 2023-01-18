package ch.asinfotrack.webapinotification.controller;

import ch.asinfotrack.webapinotification.model.Notification;
import ch.asinfotrack.webapinotification.service.NotificationService;
import ch.asinfotrack.webapinotification.service.WebSocketService;
import ch.asinfotrack.webapinotification.websocket.SocketHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Notification> getAll() {
        return notificationService.findAll();
    }

    @PostMapping("/debug")
    public String debug(@RequestParam(name = "token") String token, @RequestBody String response) {
        System.out.println(response);
        return response;
    }

    @PostMapping("/notifications")
    @ResponseBody
    public void newNotification(@RequestBody String response) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(response);
        notificationService.create(jsonObject);
    }

    @RequestMapping(value = "/notifications",method=RequestMethod.DELETE)
    @ResponseBody
    public void deletePost(@RequestParam(value = "id") Optional<Long> id, @RequestParam(value = "title") Optional<String> title) throws IOException {
        if (id.isPresent()) {
            notificationService.delete(id.get());
        } else if (title.isPresent()){
            notificationService.delete(title.get());
        }
    }
}
