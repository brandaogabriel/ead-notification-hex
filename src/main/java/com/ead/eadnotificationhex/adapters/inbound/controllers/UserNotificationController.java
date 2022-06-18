package com.ead.eadnotificationhex.adapters.inbound.controllers;

import com.ead.eadnotificationhex.adapters.dtos.NotificationDto;
import com.ead.eadnotificationhex.core.domain.NotificationDomain;
import com.ead.eadnotificationhex.core.domain.PageInfo;
import com.ead.eadnotificationhex.core.ports.NotificationServicePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    private final NotificationServicePort notificationServicePort;

    public UserNotificationController(NotificationServicePort notificationServicePort) {
        this.notificationServicePort = notificationServicePort;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/api/v1/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationDomain>> getAllNotificationsByUser(@PathVariable(value = "userId") UUID userId,
                                                                              @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                              Authentication authentication) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNumber(pageable.getPageNumber());
        pageInfo.setPageSize(pageInfo.getPageSize());
        List<NotificationDomain> notificationDomainList = notificationServicePort.findAllNotificationByUser(userId, pageInfo);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(notificationDomainList, pageable, notificationDomainList.size()));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PatchMapping("/api/v1/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotificationStatus(@PathVariable(value = "userId") UUID userId,
                                                           @PathVariable(value = "notificationId") UUID notificationId,
                                                           @RequestBody @Valid NotificationDto request) {
        Optional<NotificationDomain> possibleNotification = notificationServicePort.findByNotificationIdAndUserId(notificationId, userId);
        if (possibleNotification.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found");
        }
        possibleNotification.get().setStatus(request.getStatus());
        notificationServicePort.saveNotification(possibleNotification.get());
        return ResponseEntity.status(HttpStatus.OK).body(possibleNotification.get());
    }

}
