package com.moigae.application.component.meeting.domain.enumeraion;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MeetingPrice {
    FREE("무료", 0),
    PAY("유료", 100);  // 가격은 나중에 설정 가능, 디폴트 백원 -> 테스트 용도
    private final String value;
    private int price;

    //가격 설정 메소드
    public void setPriceForPay(int price) {
        if (this == PAY) {
            this.price = price;
        } else {
            throw new UnsupportedOperationException("무료 모임에서는 가격 설정이 불가능");
        }
    }

    public String getValue() {
        return value;
    }

    public int getPrice() {
        return price;
    }
}