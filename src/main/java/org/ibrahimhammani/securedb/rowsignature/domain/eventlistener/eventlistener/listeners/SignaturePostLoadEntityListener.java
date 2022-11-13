package org.ibrahimhammani.securedb.rowsignature.domain.eventlistener.eventlistener.listeners;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.ibrahimhammani.securedb.rowsignature.domain.entity.Payment;
import org.ibrahimhammani.securedb.rowsignature.domain.eventlistener.eventlistener.exception.InvalidSignatureException;
import org.ibrahimhammani.securedb.rowsignature.domain.eventlistener.eventlistener.exception.NoSignatureException;
import org.ibrahimhammani.securedb.rowsignature.domain.utils.DbSecurityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class SignaturePostLoadEntityListener implements PostLoadEventListener {

    protected final Mac signatureHMacEncoder;


    @Override
    public void onPostLoad(PostLoadEvent event) {
        try {
            Payment payment = (Payment) event.getEntity();
            String currentSignature = payment.getSignature();
            if (StringUtils.isBlank(currentSignature)) {
                throw new NoSignatureException("Entity is not signed");
            }
            String calculatedSignature = Base64
                    .getEncoder()
                    .encodeToString(signatureHMacEncoder.doFinal(DbSecurityUtils.generatePlainSignature(payment).getBytes(StandardCharsets.UTF_8)));
            if (!calculatedSignature.equals(currentSignature)) {
                throw new InvalidSignatureException("Entity signature is not valid");
            }

        } catch (InvalidSignatureException | NoSignatureException e) {
            throw new RuntimeException(e);
        }
    }
}
