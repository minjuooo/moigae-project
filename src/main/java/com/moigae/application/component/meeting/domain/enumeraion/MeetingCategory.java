package com.moigae.application.component.meeting.domain.enumeraion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeetingCategory {
    //카테고리
    TOTAL("전체"),
    ONLINE("온라인"),
    OFFLINE("오프라인"),

    FREE("무료"),
    PAY("유료"),

    PET_FRIENDLY("반려견 동반"),

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