package org.ibrahimhammani.securedb.rowsignature.repository;

import org.apache.commons.lang3.StringUtils;
import org.ibrahimhammani.securedb.rowsignature.domain.entity.Payment;
import org.ibrahimhammani.securedb.rowsignature.domain.utils.DbSecurityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ibrahimhammani.securedb.rowsignature.RowSignatureApplication;

import javax.crypto.Mac;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = { RowSignatureApplication.class })
class PaymentRepositoryTest {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    Mac signatureHMacEncoder;

    private final ZonedDateTime now= ZonedDateTime.now();
    private final String address= "Some address";
    private final BigDecimal amount= new BigDecimal("99.99");

    @Test
    public void insertTest(){
        Payment payment=new Payment();

        payment.setDate(now);
        payment.setAddress(address);
        payment.setAmount(amount);

        Payment savedPayment=paymentRepository.save(payment);

        Optional<Payment> readPayment=paymentRepository.findById(savedPayment.getId());
        assertTrue(readPayment.isPresent());
        assertTrue(StringUtils.isNotBlank(readPayment.get().getSignature()));
        String signature = Base64
                .getEncoder()
                .encodeToString(signatureHMacEncoder.doFinal(DbSecurityUtils.generatePlainSignature(readPayment.get())
                        .getBytes(StandardCharsets.UTF_8)));
        assertEquals(signature,readPayment.get().getSignature());
    }
}