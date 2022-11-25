package ch.asinfotrack.webapinotification.model;

import ch.asinfotrack.webapinotification.helper.DEPARTMENT;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Notification {

    @Id
    @GeneratedValue
    private Long  id;
    @Column(name = "title")
    private String title;
    @Column(name = "host")
    private String host;
    @Column(name = "timestamp")
    private String timestamp;
    @Column(name = "department")
    private DEPARTMENT department;

    public Notification(String title, String host, String timestamp, DEPARTMENT department) {
        this.title = title;
        this.host = host;
        this.timestamp = timestamp;
        this.department = department;
    }

    public Notification() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public DEPARTMENT getDepartment() {
        return department;
    }

    public void setDepartment(DEPARTMENT department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Title: " + this.title + "Host: " + this.host + "TS: " + this.timestamp + "---" + this.department.toString();
    }
}
