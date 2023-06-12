package com.moigae.application.component.meeting.dto;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.MeetingAddress;
import com.moigae.application.component.meeting.domain.MeetingContact;
import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingCategory;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingPrice;
import com.moigae.application.component.meeting.domain.enumeraion.PetAllowedStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MeetingDto {
    private Long id;
    private String meetingTitle;
    private MeetingCategory meetingCategory;
    private String nickName;
    private String hostDescription;
    //이미지 경로
    private String path;
    private String meetingDescription;
    private LocalDateTime recruitmentStartDateTime;
    private LocalDateTime recruitmentEndDateTime;
    private ParticipantRange participantRange;
    private LocalDateTime meetingStartDateTime;
    private LocalDateTime meetingEndDateTime;
    private MeetingAddress meetingAddress;
    private MeetingPrice meetingPrice;
    private PetAllowedStatus petAllowedStatus;
    private MeetingContact meetingContact;
    private String meetingFreeFormDetails;
    private String meetingStatus;

    @Builder
    public MeetingDto(Long id, String meetingTitle, MeetingCategory meetingCategory,
                      String nickName, String hostDescription, String path, String meetingDescription,
                      LocalDateTime recruitmentStartDateTime, LocalDateTime recruitmentEndDateTime,
                      ParticipantRange participantRange, LocalDateTime meetingStartDateTime,
                      LocalDateTime meetingEndDateTime, MeetingAddress meetingAddress, MeetingPrice meetingPrice,
                      PetAllowedStatus petAllowedStatus, MeetingContact meetingContact, String meetingFreeFormDetails,
                      String meetingStatus) {
        this.id = id;
        this.meetingTitle = meetingTitle;
        this.meetingCategory = meetingCategory;
        this.nickName = nickName;
        this.hostDescription = hostDescription;
        this.path = path;
        this.meetingDescription = meetingDescription;
        this.recruitmentStartDateTime = recruitmentStartDateTime;
        this.recruitmentEndDateTime = recruitmentEndDateTime;
        this.participantRange = participantRange;
        this.meetingStartDateTime = meetingStartDateTime;
        this.meetingEndDateTime = meetingEndDateTime;
        this.meetingAddress = meetingAddress;
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
                .meetingCategory(meeting.getMeetingCategory())
                .nickName(meeting.getNickName())
                .hostDescription(meeting.getHostDescription())
                .path(meeting.getMeetingImage().getPath())
                .meetingDescription(meeting.getMeetingDescription())
                .recruitmentStartDateTime(meeting.getRecruitmentStartDateTime())
                .recruitmentEndDateTime(meeting.getRecruitmentEndDateTime())
                .participantRange(meeting.getParticipantRange())
                .meetingStartDateTime(meeting.getMeetingStartDateTime())
                .meetingEndDateTime(meeting.getMeetingEndDateTime())
                .meetingAddress(meeting.getMeetingAddress())
                .meetingPrice(meeting.getMeetingPrice())
                .petAllowedStatus(meeting.getPetAllowedStatus())
                .meetingContact(meeting.getMeetingContact())
                .meetingFreeFormDetails(meeting.getMeetingFreeFormDetails())
                .meetingStatus(meeting.getMeetingStatus().getValue())
                .build();
    }
}