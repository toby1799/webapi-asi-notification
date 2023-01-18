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
            DEPARTMENT department;
            switch (json.get("department").toString()) {
                case "DEVOPS":
                    department = DEPARTMENT.DEVOPS;
                    break;
                case "SWE":
                    department = DEPARTMENT.SWE;
                    break;
                case "IBMI":
                    department = DEPARTMENT.IBMI;
                    break;
                case "SOL":
                    department = DEPARTMENT.SOL;
                    break;
                default:
                    department = DEPARTMENT.ICT;
                    break;
            }


            notification = new Notification(
                    json.get("title").toString(),
                    json.get("host").toString(),
                    json.get("timestamp").toString(),
                    department);
        }

        assert notification != null;
        if (repository.findAllByTitle(notification.getTitle()).size() == 0) {
            repository.saveAndFlush(notification);
            webSocketService.sendNotification(notification, MSGTYPE.CREATE);
        }
    }

    public void delete(Long id) throws IOException {
        Optional<Notification> notification;

        notification = repository.findById(id);

        if (notification.isPresent()) {
            repository.delete(notification.get());
            webSocketService.sendNotification(notification.get(), MSGTYPE.DELETE);
        }
    }

    public void delete(String title) throws IOException {
        List<Notification> notifications;

        notifications = repository.findAllByTitle(title);

        if (notifications.size() > 0) {
            repository.delete(notifications.get(0));
            webSocketService.sendNotification(notifications.get(0), MSGTYPE.DELETE);
        }
    }

    private Notification transform(JSONObject json) throws JSONException {
        if (json.has("data")) {
            JSONObject data = json.getJSONObject("data");
            return new Notification(
                    data.getJSONObject("essentials").get("alertRule").toString(),
                    "Azure",
                    data.getJSONObject("alertContext").get("eventTimestamp").toString(),
                    DEPARTMENT.DEVOPS);
        }
        return null;
    }

}
