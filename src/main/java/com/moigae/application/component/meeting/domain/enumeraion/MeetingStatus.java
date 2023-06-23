package com.moigae.application.component.meeting.domain.enumeraion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeetingStatus {
    AVAILABLE("참여 가능"),
    PERSON_FULL("인원 마감"),
    CANCELLED("모집 취소"),
    END("미팅 종료");

    private final String value;
}