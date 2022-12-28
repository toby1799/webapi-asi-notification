package ch.asinfotrack.webapinotification.service;

import ch.asinfotrack.webapinotification.helper.DEPARTMENT;
import ch.asinfotrack.webapinotification.helper.MSGTYPE;
import ch.asinfotrack.webapinotification.model.Notification;
import ch.asinfotrack.webapinotification.repository.NotificationRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final WebSocketService webSocketService;

    public NotificationService(NotificationRepository repository, WebSocketService webSocketService) {
        this.repository = repository;
        this.webSocketService = webSocketService;
    }

    public List<Notification> findAll() {
        List<Notification> notifications = repository.findAll();

        return notifications;
    }

    public void create(JSONObject json) throws JSONException, IOException {

        Notification notification;

        if (json.has("schemaId")) {
            notification = this.transform(json);
        } else {
            notification = new Notification(
                    json.get("title").toString(),
                    json.get("host").toString(),
                    json.get("timestamp").toString(),
                    DEPARTMENT.DEVOPS);
        }
        Date date = new Date();


        repository.saveAndFlush(notification);
        webSocketService.sendNotification(notification, MSGTYPE.CREATE);
    }

    public void delete(Long id) throws IOException {
        Optional<Notification> notification;

        notification = repository.findById(id);

        if (notification.isPresent()) {
            repository.delete(notification.get());
            webSocketService.sendNotification(notification.get(), MSGTYPE.DELETE);
        }
    }

    private Notification transform(JSONObject json) throws JSONException {
        if (json.has("data")) {
            JSONObject data = json.getJSONObject("data");
            return new Notification(
                    data.getJSONObject("alertContext").get("condition").toString(),
                    json.get("host").toString(),
                    json.get("timestamp").toString(),
                    DEPARTMENT.DEVOPS);
        }
        return null;
    }

}
