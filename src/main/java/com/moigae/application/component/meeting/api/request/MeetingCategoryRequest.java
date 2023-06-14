package com.moigae.application.component.meeting.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MeetingCategoryRequest {
    private String fellowship;
    private String party;
    private String outdoorActivity;
    private String education;
    private String communication;
    private String hobby;
    private String selfDevelopment;
    private String others;

    @Builder
    public MeetingCategoryRequest(String fellowship, String party, String outdoorActivity, String education,
                                  String communication, String hobby, String selfDevelopment, String others) {
        this.fellowship = fellowship;
        this.party = party;
        this.outdoorActivity = outdoorActivity;
        this.education = education;
        this.communication = communication;
        this.hobby = hobby;
        this.selfDevelopment = selfDevelopment;
        this.others = others;
    }

    //Reflection API 도입
    public List<String> toMeetingCategoryDtoList() {
        List<String> list = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String value = (String) field.get(this);
                if (value != null && !value.isEmpty()) {
                    list.add(value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}