package com.moigae.application.component.user.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MAN("남자"),
    WOMAN("여자");

    private final String value;
}