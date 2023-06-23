package com.moigae.application.component.meeting.domain.enumeraion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MeetingPriceTest {
    @Test
    @DisplayName("모임_가격_테스트")
    void 모임_가격_테스트() {
        //given
        MeetingPrice meetingPrice = MeetingPrice.PAY;

        //when
        meetingPrice.setPriceForPay(1000);

        //then
        Assertions.assertThat(meetingPrice.getPrice()).isEqualTo(1000);
    }
}