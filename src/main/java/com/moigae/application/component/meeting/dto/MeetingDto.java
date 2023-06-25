package com.moigae.application.component.meeting.dto;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.MeetingAddress;
import com.moigae.application.component.meeting.domain.MeetingContact;
import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingCategory;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingPrice;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingType;
import com.moigae.application.component.meeting.domain.enumeraion.PetAllowedStatus;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@NoArgsConstructor
public class MeetingDto {
    private static Long payId = 100000L;
    private String id;
    private String meetingTitle;
    private MeetingType meetingType;
    private MeetingCategory meetingCategory;
    private String nickName;
    //이미지 경로
    private String path;
    private String meetingDescription;
    private LocalDateTime recruitmentStartDateTime;
    private LocalDateTime recruitmentEndDateTime;
    private ParticipantRange participantRange;
    private LocalDateTime meetingStartDateTime;
    private LocalDateTime meetingEndDateTime;
    private MeetingAddress meetingAddress;
    // 긴급 수정 - 홍정완
    private Integer price;
    private MeetingPrice meetingPrice;
    //
    private PetAllowedStatus petAllowedStatus;
    private MeetingContact meetingContact;
    private String meetingFreeFormDetails;
    private String meetingStatus;
    /*private Date rsDate;*/
    @Builder
    public MeetingDto(String id, String meetingTitle, MeetingType meetingType, MeetingCategory meetingCategory,
                      String nickName, String path, String meetingDescription, LocalDateTime recruitmentStartDateTime,
                      LocalDateTime recruitmentEndDateTime, ParticipantRange participantRange,
                      LocalDateTime meetingStartDateTime, LocalDateTime meetingEndDateTime,
                      MeetingAddress meetingAddress, Integer price, MeetingPrice meetingPrice,
                      PetAllowedStatus petAllowedStatus, MeetingContact meetingContact, String meetingFreeFormDetails,
                      String meetingStatus) {
        this.id = id;
        this.meetingTitle = meetingTitle;
        this.meetingType = meetingType;
        this.meetingCategory = meetingCategory;
        this.nickName = nickName;
        this.path = path;
        this.meetingDescription = meetingDescription;
        this.recruitmentStartDateTime = recruitmentStartDateTime;
        this.recruitmentEndDateTime = recruitmentEndDateTime;
        this.participantRange = participantRange;
        this.meetingStartDateTime = meetingStartDateTime;
        this.meetingEndDateTime = meetingEndDateTime;
        this.meetingAddress = meetingAddress;
        this.price = price;
        this.meetingPrice = meetingPrice;
        this.petAllowedStatus = petAllowedStatus;
        this.meetingContact = meetingContact;
        this.meetingFreeFormDetails = meetingFreeFormDetails;
        this.meetingStatus = meetingStatus;
    }

    public static MeetingDto toMeetingDto(Meeting meeting) {
        return MeetingDto.builder()
                .id(meeting.getId())
                .meetingTitle(meeting.getMeetingTitle())
                .meetingType(meeting.getMeetingType())
                .meetingCategory(meeting.getMeetingCategory())
                .nickName(meeting.getNickName())
                .path(meeting.getMeetingImage().getPath())
                .meetingDescription(meeting.getMeetingDescription())
                .recruitmentStartDateTime(meeting.getRecruitmentStartDateTime())
                .recruitmentEndDateTime(meeting.getRecruitmentEndDateTime())
                .participantRange(meeting.getParticipantRange())
                .meetingStartDateTime(meeting.getMeetingStartDateTime())
                .meetingEndDateTime(meeting.getMeetingEndDateTime())
                .meetingAddress(meeting.getMeetingAddress())
                .price(meeting.getMeetingAmount())
                .meetingPrice(meeting.getMeetingPrice())
                .petAllowedStatus(meeting.getPetAllowedStatus())
                .meetingContact(meeting.getMeetingContact())
                .meetingFreeFormDetails(meeting.getMeetingFreeFormDetails())
                .meetingStatus(meeting.getMeetingStatus().getValue())
                .build();
    }

    public Long getPayId() {
        return payId++;
    }

    public String getId() {
        return id;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public MeetingCategory getMeetingCategory() {
        return meetingCategory;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPath() {
        return path;
    }

    public String getMeetingDescription() {
        return meetingDescription;
    }

    public LocalDateTime getRecruitmentStartDateTime() {
        return recruitmentStartDateTime;
    }

    public LocalDateTime getRecruitmentEndDateTime() {
        return recruitmentEndDateTime;
    }

    public ParticipantRange getParticipantRange() {
        return participantRange;
    }

    public LocalDateTime getMeetingStartDateTime() {
        return meetingStartDateTime;
    }

    public LocalDateTime getMeetingEndDateTime() {
        return meetingEndDateTime;
    }

    public MeetingAddress getMeetingAddress() {
        return meetingAddress;
    }

    public MeetingPrice getMeetingPrice() {
        return meetingPrice;
    }

    public PetAllowedStatus getPetAllowedStatus() {
        return petAllowedStatus;
    }

    public MeetingContact getMeetingContact() {
        return meetingContact;
    }

    public String getMeetingFreeFormDetails() {
        return meetingFreeFormDetails;
    }

    public String getMeetingStatus() {
        return meetingStatus;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }

    public int getPrice() {
        return price;
    }

/*    public Date getRsDate() {
        return rsDate;
    }

    public void setRsDate(Date rsDate) {
        this.rsDate = rsDate;
    }*/
}