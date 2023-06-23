package com.moigae.application.component.user.dto;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.MeetingAddress;
import com.moigae.application.component.meeting.domain.MeetingContact;
import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingCategory;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingPrice;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingStatus;
import com.moigae.application.component.meeting.domain.enumeraion.PetAllowedStatus;
import com.moigae.application.component.meeting_image.domain.MeetingImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MeetingPostDto {
    private String id;
    private String meetingTitle;
    private MeetingCategory meetingCategory;
    private String nickName;
    private String hostDescription;
    private MeetingImage meetingImage;
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

//    @Builder
//    public MeetingPostDto(String id, String meetingTitle, MeetingCategory meetingCategory, String nickName, String hostDescription, MeetingImage meetingImage, String meetingDescription, LocalDateTime recruitmentStartDateTime, LocalDateTime recruitmentEndDateTime, ParticipantRange participantRange, LocalDateTime meetingStartDateTime, LocalDateTime meetingEndDateTime, MeetingAddress meetingAddress, MeetingPrice meetingPrice, PetAllowedStatus petAllowedStatus, MeetingContact meetingContact, String meetingFreeFormDetails, String meetingStatus) {
//        this.id = id;
//        this.meetingTitle = meetingTitle;
//        this.meetingCategory = meetingCategory;
//        this.nickName = nickName;
//        this.meetingImage = meetingImage;
//        this.meetingDescription = meetingDescription;
//        this.recruitmentStartDateTime = recruitmentStartDateTime;
//        this.recruitmentEndDateTime = recruitmentEndDateTime;
//        this.participantRange = participantRange;
//        this.meetingStartDateTime = meetingStartDateTime;
//        this.meetingEndDateTime = meetingEndDateTime;
//        this.meetingAddress = meetingAddress;
//        this.meetingPrice = meetingPrice;
//        this.petAllowedStatus = petAllowedStatus;
//        this.meetingContact = meetingContact;
//        this.meetingFreeFormDetails = meetingFreeFormDetails;
//        this.meetingStatus = meetingStatus;
//    }


    /**
     * public static Meeting toMeeting(MeetingPostRequest meetingPostRequest){
     *     ... 안에 로직
     * }
     *
     * 프론트에서 넘겨준 값을 객체로 받아서 해당 값으로 meeting 도메인 객체를 생성한다.
     * 단, 이 과정에서 toMeetingDto 메서드를 통해 해당 과정을 수행해야한다.
     *
     * 조건 1(필수) : 위의 메서드 구조를 참고해서 작성한다.
     * 조건 2(선택) : builder 패턴을 사용한다.
     * 조건 3(필수) : MeetingRepository를 사용해서 DB에 저장한다.
     *
     * @param meeting
     * @return
     */

    /**
     * 아래의 메서드는 Post 요청에 사용하지 않지만 조건 2를 수행하기 위해 참고 자료로 이용한다.
     **/
    public static Meeting toMeeting(MeetingPostDto meetingPostDto) {
        return Meeting.builder()
                .meetingTitle(meetingPostDto.getMeetingTitle())
                .meetingCategory(meetingPostDto.getMeetingCategory())
                .nickName(meetingPostDto.getNickName())
                .meetingImage(meetingPostDto.getMeetingImage())
                .meetingDescription(meetingPostDto.getMeetingDescription())
                .recruitmentStartDateTime(meetingPostDto.getRecruitmentStartDateTime())
                .recruitmentEndDateTime(meetingPostDto.getRecruitmentEndDateTime())
                .participantRange(meetingPostDto.getParticipantRange())
                .meetingStartDateTime(meetingPostDto.getMeetingStartDateTime())
                .meetingEndDateTime(meetingPostDto.getMeetingEndDateTime())
                .meetingAddress(meetingPostDto.getMeetingAddress())
                .meetingPrice(meetingPostDto.getMeetingPrice())
                .petAllowedStatus(meetingPostDto.getPetAllowedStatus())
                .meetingContact(meetingPostDto.getMeetingContact())
                .meetingFreeFormDetails(meetingPostDto.getMeetingFreeFormDetails())
                .meetingStatus(MeetingStatus.valueOf(meetingPostDto.getMeetingStatus()))
                .build();
    }
}