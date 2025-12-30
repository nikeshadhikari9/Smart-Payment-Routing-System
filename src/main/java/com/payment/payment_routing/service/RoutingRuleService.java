package com.payment.payment_routing.service;

import com.payment.payment_routing.model.PaymentGateway;
import com.payment.payment_routing.model.RoutingRule;
import com.payment.payment_routing.repository.RoutingRuleRepository;
import org.springframework.stereotype.Service;
import java.util.Comparator;

@Service
public class RoutingRuleService {
    private final RoutingRuleRepository repo;

    public RoutingRuleService(RoutingRuleRepository repo) {
        this.repo = repo;
    }

    public PaymentGateway selectGateway(double amount) {
        return repo.findAll().stream()
                .filter(r -> amount >= r.getMinAmount() && amount <= r.getMaxAmount())
                .min(Comparator.comparingInt(RoutingRule::getPriority))
                .orElseThrow(() -> new RuntimeException("No routing rule found"))
                .getGateway();
    }
}
