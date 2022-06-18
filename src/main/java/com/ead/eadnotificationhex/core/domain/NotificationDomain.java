package com.ead.eadnotificationhex.core.domain;

import com.ead.eadnotificationhex.core.domain.enums.NotificationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationDomain {

    private UUID id;
    private UUID userId;
    private String title;
    private String message;
    private LocalDateTime creationDate;
    private NotificationStatus status;

    public NotificationDomain() {}

    public NotificationDomain(UUID userId, String title, String message, LocalDateTime creationDate, NotificationStatus status) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.creationDate = creationDate;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }
}
