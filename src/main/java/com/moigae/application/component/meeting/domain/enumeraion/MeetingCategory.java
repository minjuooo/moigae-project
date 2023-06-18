package com.moigae.application.component.meeting.domain.enumeraion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeetingCategory {
    // 시그니처 추가하기.
    FELLOWSHIP("친목도모"),
    PARTY("파티"),
    OUTDOOR_ACTIVITY("야외활동"),

    EDUCATION("교육"),
    COMMUNICATION("소통"),
    HOBBY("취미"),
    SELF_DEVELOPMENT("자기계발"),
    OTHERS("기타");

    private final String value;
}