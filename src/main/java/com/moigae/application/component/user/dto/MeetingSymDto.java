package com.moigae.application.component.user.dto;


import com.moigae.application.component.meeting.domain.enumeraion.MeetingPrice;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MeetingSymDto {
    private String meetingId;
    private String path;
    private String meetingTitle;
    private Integer meetingAmount;
    private MeetingPrice meetingPrice;

    public MeetingSymDto(String meetingId, String path, String meetingTitle, Integer meetingAmount, MeetingPrice meetingPrice) {
        this.meetingId = meetingId;
        this.path = path;
        this.meetingTitle = meetingTitle;
        this.meetingAmount = meetingAmount;
        this.meetingPrice = meetingPrice;
    }
}







