package com.payment.payment_routing.repository;

import com.payment.payment_routing.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository  extends JpaRepository<PaymentTransaction, Long> {

    public Optional<PaymentTransaction> findByRequestId(String requestId);
    List<PaymentTransaction> findByStatus(String status);

}
