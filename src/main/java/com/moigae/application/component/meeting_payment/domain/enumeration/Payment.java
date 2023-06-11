package com.moigae.application.component.meeting_payment.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Payment {
    CARD("카드 결제");

    private final String value;
}