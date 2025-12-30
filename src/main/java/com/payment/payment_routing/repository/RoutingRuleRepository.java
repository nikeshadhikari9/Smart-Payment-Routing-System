package com.payment.payment_routing.repository;

import com.payment.payment_routing.model.RoutingRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutingRuleRepository
        extends JpaRepository<RoutingRule, Long> {
}