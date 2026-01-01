package com.payment.payment_routing.model;
import jakarta.persistence.*;
@Entity
public class RoutingRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double minAmount;
    private double maxAmount;

    @Enumerated(EnumType.STRING)
    private PaymentGateway gateway;

    private int priority;

    public Long getId() {
        return id;
    }
    public double getMinAmount() {
        return minAmount;
    }
    public double getMaxAmount() {
        return maxAmount;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }

    public int getPriority() {
        return priority;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }
    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }
    public void setGateway(PaymentGateway gateway) {
        this.gateway = gateway;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
