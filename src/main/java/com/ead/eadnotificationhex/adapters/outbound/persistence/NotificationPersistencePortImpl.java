package com.ead.eadnotificationhex.adapters.outbound.persistence;

import com.ead.eadnotificationhex.adapters.outbound.persistence.entities.NotificationEntity;
import com.ead.eadnotificationhex.core.domain.NotificationDomain;
import com.ead.eadnotificationhex.core.domain.PageInfo;
import com.ead.eadnotificationhex.core.domain.enums.NotificationStatus;
import com.ead.eadnotificationhex.core.ports.NotificationPersistencePort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationPersistencePortImpl implements NotificationPersistencePort {

    private final NotificationJpaRepository notificationJpaRepository;
    private final ModelMapper modelMapper;


    public NotificationPersistencePortImpl(NotificationJpaRepository notificationJpaRepository, ModelMapper modelMapper) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationDomain saveNotification(NotificationDomain notificationDomain) {
        NotificationEntity entity = notificationJpaRepository.save(modelMapper.map(notificationDomain, NotificationEntity.class));
        return modelMapper.map(entity, NotificationDomain.class);
    }

    @Override
    public List<NotificationDomain> findAllNotificationByUserIdAndNotificationStatus(UUID userId, NotificationStatus status, PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize());
        return notificationJpaRepository.findAllByUserIdAndStatus(userId, status, pageable).stream().map(entity -> modelMapper.map(entity, NotificationDomain.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        Optional<NotificationEntity> optionalNotificationEntity = notificationJpaRepository.findByIdAndUserId(notificationId, userId);
        if (optionalNotificationEntity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(modelMapper.map(optionalNotificationEntity.get(), NotificationDomain.class));
    }
}
