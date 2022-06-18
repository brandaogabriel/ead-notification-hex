package com.ead.eadnotificationhex.adapters.configs;

import com.ead.eadnotificationhex.EadNotificationHexApplication;
import com.ead.eadnotificationhex.core.ports.NotificationPersistencePort;
import com.ead.eadnotificationhex.core.services.NotificationServicePortImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = EadNotificationHexApplication.class)
public class BeanConfiguration {


    @Bean
    public NotificationServicePortImpl notificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
        return new NotificationServicePortImpl(notificationPersistencePort);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
