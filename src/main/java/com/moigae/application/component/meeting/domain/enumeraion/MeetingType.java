package com.moigae.application.component.meeting.domain.enumeraion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeetingType {
    ONLINE("온라인"),
    OFFLINE("오프라인");

    private final String value;
}