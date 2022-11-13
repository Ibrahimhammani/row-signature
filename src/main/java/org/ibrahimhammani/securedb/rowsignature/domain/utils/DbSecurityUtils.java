package org.ibrahimhammani.securedb.rowsignature.domain.utils;

import org.ibrahimhammani.securedb.rowsignature.domain.entity.Payment;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class DbSecurityUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat();

    static {
        DECIMAL_FORMAT.setMaximumFractionDigits(2);
        DECIMAL_FORMAT.setMinimumFractionDigits(2);
        DECIMAL_FORMAT.setGroupingUsed(false);
    }

    public static String generatePlainSignature(Payment payment) {
        return payment.getId() +
                "|" +
                (payment.getAmount() != null ? DECIMAL_FORMAT.format(payment.getAmount()) : null) +
                "|" +
                (payment.getDate() != null ? DATE_TIME_FORMATTER.format(payment.getDate()) : null) +
                "|" +
                payment.getAddress();
    }

}
