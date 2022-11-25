package ch.asinfotrack.webapinotification.repository;

import ch.asinfotrack.webapinotification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByTitle(String title);

}
