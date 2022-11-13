package org.ibrahimhammani.securedb.rowsignature.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.ibrahimhammani.securedb.rowsignature.domain.eventlistener.eventlistener.EventListenerIntegrator;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class HibernateConfig implements HibernatePropertiesCustomizer {

    private final EventListenerIntegrator eventListenerIntegrator;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
            hibernateProperties.put(
                "hibernate.integrator_provider",
                (IntegratorProvider) () -> Collections.singletonList(eventListenerIntegrator)
            );
    }
}
