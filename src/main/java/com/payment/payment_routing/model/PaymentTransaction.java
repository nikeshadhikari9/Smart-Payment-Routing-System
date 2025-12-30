package com.payment.payment_routing.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;


@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentGateway gateway;

    private String status; // SUCCESS / FAILED

    @Column(unique = true)
    private String requestId;

    private int retryCount;

    private LocalDateTime createdAt;

    public PaymentTransaction() {
        this.createdAt = LocalDateTime.now();
    }

    public String getStatus() {
        return this.status;
    }

    public void setAmount(double amount) {
        this.amount=amount;
    }

    public void setGateway(PaymentGateway gateway) {
        this.gateway=gateway;
    }

    public void setRequestId(String requestId) {
        this.requestId=requestId;
    }

    public void setStatus(String success) {
        this.status=status;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int i) {
        retryCount=i;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getRequestId() {
        return this.requestId;
    }
}

