package org.ibrahimhammani.securedb.rowsignature.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class DbSecurityConfig {

    private static final String HMAC_ALGORITHM = "HmacSHA512";

    @Bean
    public Mac signatureHMacEncoder(@Value("${application.security.entity.signature.key}") String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
        Mac hMac = Mac.getInstance(HMAC_ALGORITHM);
        hMac.init(secretKeySpec);
        return hMac;
    }
}
