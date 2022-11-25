package ch.asinfotrack.webapinotification.controller;

import ch.asinfotrack.webapinotification.model.Notification;
import ch.asinfotrack.webapinotification.service.NotificationService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    @ResponseBody
    public String debug(@RequestBody String response) {
        return response;
    }

    @PostMapping("/notifications")
    @ResponseBody
    public void newOrder(@RequestBody String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        notificationService.create(jsonObject);
    }
}
