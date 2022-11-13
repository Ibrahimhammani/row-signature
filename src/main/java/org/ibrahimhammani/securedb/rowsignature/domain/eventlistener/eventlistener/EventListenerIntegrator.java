package org.ibrahimhammani.securedb.rowsignature.domain.eventlistener.eventlistener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.ibrahimhammani.securedb.rowsignature.domain.eventlistener.eventlistener.listeners.SignaturePostLoadEntityListener;
import org.ibrahimhammani.securedb.rowsignature.domain.eventlistener.eventlistener.listeners.SignaturePreInsertEntityListener;
import org.ibrahimhammani.securedb.rowsignature.domain.eventlistener.eventlistener.listeners.SignaturePreUpdateEntityListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventListenerIntegrator implements Integrator {

    private final SignaturePreInsertEntityListener signaturePreInsertEntityListener;
    private final SignaturePreUpdateEntityListener signaturePreUpdateEntityListener;
    private final SignaturePostLoadEntityListener signaturePostLoadEntityListener;

    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
        eventListenerRegistry.appendListeners(EventType.PRE_INSERT, signaturePreInsertEntityListener);
        eventListenerRegistry.appendListeners(EventType.PRE_UPDATE, signaturePreUpdateEntityListener);
        eventListenerRegistry.appendListeners(EventType.POST_LOAD, signaturePostLoadEntityListener);
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
    }
}
