package com.payment.payment_routing.service;

import com.payment.payment_routing.model.PaymentTransaction;
import com.payment.payment_routing.repository.PaymentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RetryScheduler {
    private final PaymentRepository repo;
    private final PaymentService service;

    public RetryScheduler(PaymentRepository repo,
                          PaymentService service) {
        this.repo = repo;
        this.service = service;
    }

    @Scheduled(fixedDelay = 15000)
    public void retryFailed() {
        List<PaymentTransaction> failed =
                repo.findByStatus("FAILED");

        for (PaymentTransaction tx : failed) {
            if (tx.getRetryCount() >= 3) continue;
            tx.setRetryCount(tx.getRetryCount() + 1);
            service.process(tx.getAmount(), tx.getRequestId());
        }
    }
}
