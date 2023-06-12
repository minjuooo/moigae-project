package com.moigae.application.component.meeting.domain;

import com.moigae.application.component.meeting.domain.enumeraion.MeetingCategory;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingPrice;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingStatus;
import com.moigae.application.component.meeting.domain.enumeraion.PetAllowedStatus;
import com.moigae.application.component.meeting_image.domain.MeetingImage;
import com.moigae.application.core.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Meeting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //모임 이름
    @Column(name = "meeting_title")
    private String meetingTitle;

    //카테고리
    @Column(name = "meeting_category")
    @Enumerated(EnumType.STRING)
    private MeetingCategory meetingCategory;

    //닉네임
    @Column(name = "nickName")
    private String nickName;

    //호스트 소개글
    @Column(name = "host_description")
    private String hostDescription;

    //대표 이미지 - 1장 추가
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "meeting_image_id")
    private MeetingImage meetingImage;

    //모임 소개글
    @Column(name = "meeting_description")
    private String meetingDescription;

    //모집 기간 - 시작 날짜
    @Column(name = "recruitment_start_date_time")
    private LocalDateTime recruitmentStartDateTime;

    //모집 기간 - 종료 날짜
    @Column(name = "recruitment_end_date_time")
    private LocalDateTime recruitmentEndDateTime;

    //모집 인원 - 최소, 최대
    @Column(name = "participant_range")
    @Embedded
    private ParticipantRange participantRange;

    //모임 시작 시간
    @Column(name = "meeting_start_date_time")
    private LocalDateTime meetingStartDateTime;

    //모임 종료 시간
    @Column(name = "meeting_end_date_time")
    private LocalDateTime meetingEndDateTime;

    //모임 장소
    @Column(name = "meeting_address")
    @Embedded
    private MeetingAddress meetingAddress;

    //참가 비용
    @Column(name = "meeting_price")
    @Enumerated(EnumType.STRING)
    private MeetingPrice meetingPrice;

    //반려견 동반 유무
    @Column(name = "pet_allowed_status")
    @Enumerated(EnumType.STRING)
    private PetAllowedStatus petAllowedStatus;

    //연락수단
    @Column(name = "meeting_contact")
    @Embedded
    private MeetingContact meetingContact;

    //모임 정보 자유 작성 - 사진 첨부 X
    @Column(name = "meeting_free_form_details", length = 5000)
    private String meetingFreeFormDetails;

    //모임 상태(참여 가능, 인원 마감, 미팅 종료)
    @Column(name = "meetingStatus")
    @Enumerated(EnumType.STRING)
    private MeetingStatus meetingStatus;

    @Builder
    public Meeting(String meetingTitle, MeetingCategory meetingCategory, String nickName, String hostDescription,
                   MeetingImage meetingImage, String meetingDescription, LocalDateTime recruitmentStartDateTime,
                   LocalDateTime recruitmentEndDateTime, ParticipantRange participantRange,
                   LocalDateTime meetingStartDateTime, LocalDateTime meetingEndDateTime, MeetingAddress meetingAddress,
                   MeetingPrice meetingPrice, PetAllowedStatus petAllowedStatus, MeetingContact meetingContact,
                   String meetingFreeFormDetails, MeetingStatus meetingStatus) {
        this.meetingTitle = meetingTitle;
        this.meetingCategory = meetingCategory;
        this.nickName = nickName;
        this.hostDescription = hostDescription;
        this.meetingImage = meetingImage;
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

    public void addImage(MeetingImage meetingImage) {
        this.meetingImage = meetingImage;
    }
}