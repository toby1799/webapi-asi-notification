package ch.asinfotrack.webapinotification.service;

import ch.asinfotrack.webapinotification.helper.DEPARTMENT;
import ch.asinfotrack.webapinotification.model.Notification;
import ch.asinfotrack.webapinotification.repository.NotificationRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public List<Notification> findAll() {
        List<Notification> notifications = repository.findAll();

        return notifications;
    }

    public void create(JSONObject json) throws JSONException {

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



        repository.saveAndFlush(notification);
    }

    public Notification transform(JSONObject json) throws JSONException {
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
