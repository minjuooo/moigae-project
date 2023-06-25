package com.moigae.application.component.meeting.domain.enumeraion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeetingCategory {
    // 시그니처 추가하기.
    FELLOWSHIP("여행/축제"),
    PARTY("대관/파티"),
    OUTDOOR_ACTIVITY("액티비티"),

    EDUCATION("교육/체험"),
    COMMUNICATION("소통"),
    HOBBY("취미"),
    SELF_DEVELOPMENT("봉사활동"),
    OTHERS("기타");

    private final String value;
}