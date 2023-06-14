package com.moigae.application.component.article.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    STORY("기사"),
    ISSUE("공지사항");

    private final String value;

}
