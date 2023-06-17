package com.moigae.application.component.meeting.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class MeetingContact {
    //전화번호
    private String phone;
    //이메일
    private String email;
    //카카오톡 아이디
    private String kakaoId;
    //링크
    private String link;
    //기타 링크
    private String otherLink;

    @Builder
    public MeetingContact(String phone, String email, String kakaoId, String link, String otherLink) {
        this.phone = phone;
        this.email = email;
        this.kakaoId = kakaoId;
        this.link = link;
        this.otherLink = otherLink;
    }

    public void updateContact(String phone, String email, String kakaoId, String link, String otherLink) {
        this.phone = phone;
        this.email = email;
        this.kakaoId = kakaoId;
        this.link = link;
        this.otherLink = otherLink;
    }
}