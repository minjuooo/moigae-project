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
public class MeetingCategoryDto {
    private String place;
    private String price;
    private String companion;
    private String festival;
    private String etc;

    @Builder
    public MeetingCategoryDto(String place, String price, String companion, String festival, String etc) {
        this.place = place;
        this.price = price;
        this.companion = companion;
        this.festival = festival;
        this.etc = etc;
    }

    //Reflection API 도입
    public List<String> toMeetingCategoryDtoList() {
        List<String> list = new ArrayList<>();
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String value = (String) field.get(this);
                if (value != null) {
                    list.add(value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}