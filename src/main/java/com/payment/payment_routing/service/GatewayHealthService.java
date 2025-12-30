package com.payment.payment_routing.service;



import com.payment.payment_routing.model.PaymentGateway;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class GatewayHealthService {
    private final Map<PaymentGateway, Double> health =
            new EnumMap<>(PaymentGateway.class);
    public GatewayHealthService() {
        for (PaymentGateway g : PaymentGateway.values()) {
            health.put(g, 1.0);
        }
    }
    public boolean isHealthy(PaymentGateway gateway) {
        return health.get(gateway) > 0.3;
    }

    public void success(PaymentGateway gateway) {
        health.put(gateway, Math.min(1.0, health.get(gateway) + 0.05));
    }

    public void failure(PaymentGateway gateway) {
        health.put(gateway, health.get(gateway) - 0.2);
    }
}
