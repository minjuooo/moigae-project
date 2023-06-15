package com.moigae.application.component.meeting_payment.api.request;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CallbackPayload {
    private String secret;
    private String status;
    private String orderId;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}