package org.ibrahimhammani.securedb.rowsignature.domain.eventlistener.eventlistener.listeners;


import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.ibrahimhammani.securedb.rowsignature.domain.entity.Payment;
import org.ibrahimhammani.securedb.rowsignature.domain.eventlistener.eventlistener.exception.NotSingableException;
import org.ibrahimhammani.securedb.rowsignature.domain.utils.DbSecurityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@AllArgsConstructor
public class SignaturePreUpdateEntityListener implements PreUpdateEventListener {

    private final Mac signatureHMacEncoder;


    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        try {
            Payment payment = (Payment) event.getEntity();
            if (payment.getId() == null) {
                throw new NotSingableException("Entity id is null");
            }
            String signature = Base64
                    .getEncoder()
                    .encodeToString(signatureHMacEncoder.doFinal(DbSecurityUtils.generatePlainSignature(payment).getBytes(StandardCharsets.UTF_8)));
            event.getState()[ArrayUtils.indexOf(
                    event.getPersister().getEntityMetamodel().getPropertyNames(), "signature")] =
                    signature;
            payment.setSignature(signature);

        } catch (NotSingableException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
