package com.moigae.application.component.meeting.domain.enumeraion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetAllowedStatus {
    WITH_PET("반려견 동반"),
    WITHOUT_PET("반려견 미동반");

    private final String value;
}