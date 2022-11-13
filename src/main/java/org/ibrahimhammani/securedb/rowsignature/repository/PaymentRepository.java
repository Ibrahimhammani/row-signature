package org.ibrahimhammani.securedb.rowsignature.repository;

import org.ibrahimhammani.securedb.rowsignature.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
