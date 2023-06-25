package com.moigae.application.component.meeting_payment.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RefundStatus {
    REQUESTED("환불 요청"),
    PROCESSING("환불 처리 중"),
    COMPLETED("환불 완료"),
    DENIED("환불 거부"),
    NONE("없음"),
    CANCEL("모임 신청 취소");

    private final String value;
}