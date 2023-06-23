package com.moigae.application.component.meeting.domain;

import com.moigae.application.component.meeting.domain.enumeraion.*;
import com.moigae.application.component.meeting_image.domain.MeetingImage;
import com.moigae.application.core.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting")
@NoArgsConstructor
@Getter
@Setter
public class Meeting extends BaseEntity {
    @Id
    @Column(length = 191)
    @GeneratedValue(generator = "CUID")
    @GenericGenerator(name = "CUID", strategy = "com.moigae.application.core.config.PrimaryGenerator")
    private String id;

    //모임 이름
    @Column(name = "meeting_title")
    private String meetingTitle;

    //모임 - (오프라인, 온라인)
    @Column(name = "meeting_type")
    @Enumerated(EnumType.STRING)
    private MeetingType meetingType;

    //카테고리
    @Column(name = "meeting_category")
    @Enumerated(EnumType.STRING)
    private MeetingCategory meetingCategory;

    //닉네임
    @Column(name = "nickName")
    private String nickName;

    //대표 이미지 - 1장 추가
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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

    //모집 인원(값 타입 1) - 최소, 최대
    @Column(name = "participant_range")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "currentParticipants", column = @Column(nullable = true)),
            @AttributeOverride(name = "maxParticipants", column = @Column(nullable = true))
    })
    private ParticipantRange participantRange;

    //모임 시작 날짜
    @Column(name = "meeting_start_date_time")
    private LocalDateTime meetingStartDateTime;

    //모임 종료 날짜
    @Column(name = "meeting_end_date_time")
    private LocalDateTime meetingEndDateTime;

    //모임 장소(값 타입 2)
    @Column(name = "meeting_address")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(nullable = true)),
            @AttributeOverride(name = "detailAddress", column = @Column(nullable = true)),
            @AttributeOverride(name = "locationDescription", column = @Column(nullable = true))
    })
    private MeetingAddress meetingAddress;

    //유료, 무료 상태
    @Column(name = "meeting_price")
    @Enumerated(EnumType.STRING)
    private MeetingPrice meetingPrice;

    //참가 비용
    @Column(name = "meeting_amount")
    private Integer meetingAmount;

    //반려견 동반 유무
    @Column(name = "pet_allowed_status")
    @Enumerated(EnumType.STRING)
    private PetAllowedStatus petAllowedStatus;

    //연락수단(값 타입 3)
    @Column(name = "meeting_contact")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phone", column = @Column(nullable = true)),
            @AttributeOverride(name = "email", column = @Column(nullable = true)),
            @AttributeOverride(name = "kakaoId", column = @Column(nullable = true)),
            @AttributeOverride(name = "link", column = @Column(nullable = true)),
            @AttributeOverride(name = "otherLink", column = @Column(nullable = true))
    })
    private MeetingContact meetingContact;

    //모임 정보 자유 작성 - 사진 첨부 X
    @Column(name = "meeting_free_form_details", length = 5000)
    private String meetingFreeFormDetails;

    //모임 상태(참여 가능, 인원 마감, 미팅 종료)
    @Column(name = "meetingStatus")
    @Enumerated(EnumType.STRING)
    private MeetingStatus meetingStatus;

    @Builder
    public Meeting(String id, String meetingTitle, MeetingType meetingType, MeetingCategory meetingCategory,
                   String nickName, MeetingImage meetingImage, String meetingDescription,
                   LocalDateTime recruitmentStartDateTime, LocalDateTime recruitmentEndDateTime,
                   ParticipantRange participantRange, LocalDateTime meetingStartDateTime,
                   LocalDateTime meetingEndDateTime, MeetingAddress meetingAddress, MeetingPrice meetingPrice,
                   Integer meetingAmount, PetAllowedStatus petAllowedStatus, MeetingContact meetingContact,
                   String meetingFreeFormDetails, MeetingStatus meetingStatus) {
        this.id = id;
        this.meetingTitle = meetingTitle;
        this.meetingType = meetingType;
        this.meetingCategory = meetingCategory;
        this.nickName = nickName;
        this.meetingImage = meetingImage;
        this.meetingDescription = meetingDescription;
        this.recruitmentStartDateTime = recruitmentStartDateTime;
        this.recruitmentEndDateTime = recruitmentEndDateTime;
        this.participantRange = participantRange;
        this.meetingStartDateTime = meetingStartDateTime;
        this.meetingEndDateTime = meetingEndDateTime;
        this.meetingAddress = meetingAddress;
        this.meetingPrice = meetingPrice;
        this.meetingAmount = meetingAmount;
        this.petAllowedStatus = petAllowedStatus;
        this.meetingContact = meetingContact;
        this.meetingFreeFormDetails = meetingFreeFormDetails;
        this.meetingStatus = meetingStatus;
    }

    public void addImage(MeetingImage meetingImage) {
        this.meetingImage = meetingImage;
    }
}