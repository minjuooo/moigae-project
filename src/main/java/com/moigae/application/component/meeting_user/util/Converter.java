package com.moigae.application.component.meeting_user.util;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.MeetingAddress;
import com.moigae.application.component.meeting.domain.MeetingContact;
import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingStatus;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting_user.api.request.MeetingUpdateRequest;
import lombok.Data;
import lombok.experimental.UtilityClass;

@Data
@UtilityClass
public class Converter {
    public MeetingUpdateRequest toUpdateRequest(Meeting meeting) {
        MeetingUpdateRequest updateRequest = new MeetingUpdateRequest();
        updateRequest.setId(meeting.getId());
        updateRequest.setMeetingType(meeting.getMeetingType());
        updateRequest.setMeetingTitle(meeting.getMeetingTitle());
        updateRequest.setMeetingCategory(meeting.getMeetingCategory());
        updateRequest.setMeetingDescription(meeting.getMeetingDescription());
        updateRequest.setRecruitmentStartDateTime(meeting.getRecruitmentStartDateTime());
        updateRequest.setRecruitmentEndDateTime(meeting.getRecruitmentEndDateTime());
        updateRequest.setMeetingStartDateTime(meeting.getMeetingStartDateTime());
        updateRequest.setAddress(meeting.getMeetingAddress().getAddress());
        updateRequest.setDetailAddress(meeting.getMeetingAddress().getDetailAddress());
        updateRequest.setLocationDescription(meeting.getMeetingAddress().getLocationDescription());
        updateRequest.setPetAllowedStatus(meeting.getPetAllowedStatus());
        updateRequest.setPhone(meeting.getMeetingContact().getPhone());
        updateRequest.setEmail(meeting.getMeetingContact().getEmail());
        updateRequest.setKakaoId(meeting.getMeetingContact().getKakaoId());
        updateRequest.setLink(meeting.getMeetingContact().getLink());
        updateRequest.setOtherLink(meeting.getMeetingContact().getOtherLink());
        updateRequest.setMeetingPrice(meeting.getMeetingPrice());
        updateRequest.setMeetingFreeFormDetails(meeting.getMeetingFreeFormDetails());
        updateRequest.setMeetingStatus(meeting.getMeetingStatus());
        return updateRequest;
    }

    public Meeting fromUpdateRequestToMeeting(MeetingUpdateRequest updateRequest) {
        return Meeting.builder()
                .id(updateRequest.getId())
                .meetingTitle(updateRequest.getMeetingTitle())
                .meetingCategory(updateRequest.getMeetingCategory())
                .recruitmentStartDateTime(updateRequest.getRecruitmentStartDateTime())
                .recruitmentEndDateTime(updateRequest.getRecruitmentEndDateTime())
                .meetingDescription(updateRequest.getMeetingDescription())
                .participantRange(ParticipantRange.of(updateRequest.getCurrentParticipants(),
                        updateRequest.getMaxParticipants()))
                .meetingStartDateTime(updateRequest.getMeetingStartDateTime())
                .meetingEndDateTime(updateRequest.getMeetingEndDateTime())
                .meetingPrice(updateRequest.getMeetingPrice())
                .meetingContact(MeetingContact.builder()
                        .email(updateRequest.getEmail())
                        .kakaoId(updateRequest.getKakaoId())
                        .link(updateRequest.getLink())
                        .phone(updateRequest.getPhone())
                        .otherLink(updateRequest.getOtherLink())
                        .build())
                .meetingType(updateRequest.getMeetingType())
                .meetingAddress(MeetingAddress.of(updateRequest.getAddress(),
                        updateRequest.getDetailAddress(),
                        updateRequest.getLocationDescription()))
                .meetingStatus(updateRequest.getMeetingStatus())
                .petAllowedStatus(updateRequest.getPetAllowedStatus())
                .nickName(updateRequest.getNickName())
                .meetingFreeFormDetails(updateRequest.getMeetingFreeFormDetails())
                .build();
    }

    public static Meeting toMeeting(MeetingDto meetingDto) {
        return Meeting.builder()
                .id(meetingDto.getId())
                .meetingTitle(meetingDto.getMeetingTitle())
                .meetingCategory(meetingDto.getMeetingCategory())
                .recruitmentStartDateTime(meetingDto.getRecruitmentStartDateTime())
                .recruitmentEndDateTime(meetingDto.getRecruitmentEndDateTime())
                .meetingDescription(meetingDto.getMeetingDescription())
                .participantRange(meetingDto.getParticipantRange())
                .meetingStartDateTime(meetingDto.getMeetingStartDateTime())
                .meetingEndDateTime(meetingDto.getMeetingEndDateTime())
                .meetingPrice(meetingDto.getMeetingPrice())
                .meetingContact(meetingDto.getMeetingContact())
                .meetingType(meetingDto.getMeetingType())
                .meetingAddress(meetingDto.getMeetingAddress())
                .meetingStatus(getMeetingStatus(meetingDto.getMeetingStatus()))
                .petAllowedStatus(meetingDto.getPetAllowedStatus())
                .nickName(meetingDto.getNickName())
                .meetingFreeFormDetails(meetingDto.getMeetingFreeFormDetails())
                .build();
    }

    private static MeetingStatus getMeetingStatus(String meetingStatusValue) {
        if (meetingStatusValue != null) {
            switch (meetingStatusValue) {
                case "참여 가능":
                    return MeetingStatus.AVAILABLE;
                case "인원 마감":
                    return MeetingStatus.PERSON_FULL;
                case "모집 취소":
                    return MeetingStatus.CANCELLED;
                case "미팅 종료":
                    return MeetingStatus.END;
            }
        }
        return MeetingStatus.AVAILABLE; // 기본값 설정 또는 예외 처리
    }

    public MeetingDto toMeetingDto(Meeting meeting) {
        return MeetingDto.builder()
                .id(meeting.getId())
                .meetingTitle(meeting.getMeetingTitle())
                .meetingCategory(meeting.getMeetingCategory())
                .recruitmentStartDateTime(meeting.getRecruitmentStartDateTime())
                .recruitmentEndDateTime(meeting.getRecruitmentEndDateTime())
                .meetingDescription(meeting.getMeetingDescription())
                .participantRange(meeting.getParticipantRange())
                .meetingStartDateTime(meeting.getMeetingStartDateTime())
                .meetingEndDateTime(meeting.getMeetingEndDateTime())
                .meetingPrice(meeting.getMeetingPrice())
                .meetingContact(meeting.getMeetingContact())
                .meetingType(meeting.getMeetingType())
                .meetingAddress(meeting.getMeetingAddress())
                .meetingStatus(meeting.getMeetingStatus().getValue())
                .petAllowedStatus(meeting.getPetAllowedStatus())
                .nickName(meeting.getNickName())
                .meetingFreeFormDetails(meeting.getMeetingFreeFormDetails())
                .build();
    }
}