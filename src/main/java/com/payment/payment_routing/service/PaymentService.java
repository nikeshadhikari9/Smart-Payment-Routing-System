package com.payment.payment_routing.service;

import com.payment.payment_routing.model.PaymentGateway;
import com.payment.payment_routing.model.PaymentTransaction;
import com.payment.payment_routing.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class PaymentService {
    private final PaymentRepository repo;
    private final RoutingRuleService routingRuleService;
    private final GatewayHealthService healthService;

    public PaymentService(PaymentRepository repo,
                          RoutingRuleService routingRuleService,
                          GatewayHealthService healthService) {
        this.repo = repo;
        this.routingRuleService = routingRuleService;
        this.healthService = healthService;
    }

    public String process(double amount, String requestId) {

        Optional<PaymentTransaction> existing =
                repo.findByRequestId(requestId);

        if (existing.isPresent()) {
            return existing.get().getStatus();
        }

        PaymentGateway gateway =
                routingRuleService.selectGateway(amount);

        if (!healthService.isHealthy(gateway)) {
            throw new RuntimeException("Gateway unhealthy");
        }

        boolean success = new Random().nextBoolean();

        PaymentTransaction tx = new PaymentTransaction();
        tx.setAmount(amount);
        tx.setGateway(gateway);
        tx.setRequestId(requestId);

        if (success) {
            tx.setStatus("SUCCESS");
            healthService.success(gateway);
        } else {
            tx.setStatus("FAILED");
            healthService.failure(gateway);
        }
        repo.save(tx);
        return tx.getStatus();
    }
}
