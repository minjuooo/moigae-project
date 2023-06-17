package com.moigae.application.component.meeting.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MeetingAddressTest {
    private static final String ADDRESS = "주소";
    private static final String DETAIL_ADDRESS = "상세 주소";
    private static final String LOCATION_DESCRIPTION = "위치 정보";
    private MeetingAddress meetingAddress = new MeetingAddress();

    @Test
    @DisplayName("모임 주소 객체 생성 테스트")
    void 모임_주소_객체_생성_테스트() {
        //given & when
        meetingAddress = MeetingAddress.of(ADDRESS, DETAIL_ADDRESS, LOCATION_DESCRIPTION);
        //then
        모임_주소_객체_검증();
    }

    private void 모임_주소_객체_검증() {
        assertThat(meetingAddress.getAddress()).isEqualTo("주소");
        assertThat(meetingAddress.getDetailAddress()).isEqualTo("상세 주소");
        assertThat(meetingAddress.getLocationDescription()).isEqualTo("위치 정보");
    }
}