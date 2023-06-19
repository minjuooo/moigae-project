package com.moigae.application.component.meeting_user.api.request;

import com.moigae.application.component.meeting.domain.enumeraion.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MeetingCreateRequest {
    //모임 이름
    private String meetingTitle;

    //모임 - (오프라인, 온라인)
    private MeetingType meetingType;

    //모임 카테고리
    private MeetingCategory meetingCategory;

    //닉네임
    private String nickName;

    //대표이미지
    private MultipartFile path;

    //모임 소개글
    private String meetingDescription;

    //모집 기간 - (시작, 종료) 날짜
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime recruitmentStartDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime recruitmentEndDateTime;

    //모집 인원 - 객체 변환 필요
    private Integer currentParticipants = 0;
    private Integer maxParticipants;

    //모임 시간 - (시작, 종료) 날짜
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime meetingStartDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime meetingEndDateTime;

    //모임 장소 - 객체 변환 필요
    private String address;
    private String detailAddress;
    private String locationDescription;

    //참가 비용
    private MeetingPrice meetingPrice;
    private Integer price;

    //반려견 동반 유무
    private PetAllowedStatus petAllowedStatus;

    //연락수단 - 객체 변환 필요
    private String phone;
    private String email;
    private String kakaoId;
    private String link;
    private String otherLink;

    //모임 정보 자유 작성
    private String meetingFreeFormDetails;

    //모임 상태
    private MeetingStatus meetingStatus = MeetingStatus.AVAILABLE;

    @Builder
    public MeetingCreateRequest(String meetingTitle, MeetingType meetingType, MeetingCategory meetingCategory,
                                String nickName, MultipartFile path, String meetingDescription,
                                LocalDateTime recruitmentStartDateTime, LocalDateTime recruitmentEndDateTime,
                                Integer currentParticipants, Integer maxParticipants, LocalDateTime meetingStartDateTime,
                                LocalDateTime meetingEndDateTime, String address, String detailAddress,
                                String locationDescription, MeetingPrice meetingPrice, Integer price,
                                PetAllowedStatus petAllowedStatus, String phone, String email, String kakaoId,
                                String link, String otherLink, String meetingFreeFormDetails,
                                MeetingStatus meetingStatus) {
        this.meetingTitle = meetingTitle;
        this.meetingType = meetingType;
        this.meetingCategory = meetingCategory;
        this.nickName = nickName;
        this.path = path;
        this.meetingDescription = meetingDescription;
        this.recruitmentStartDateTime = recruitmentStartDateTime;
        this.recruitmentEndDateTime = recruitmentEndDateTime;
        this.currentParticipants = currentParticipants;
        this.maxParticipants = maxParticipants;
        this.meetingStartDateTime = meetingStartDateTime;
        this.meetingEndDateTime = meetingEndDateTime;
        this.address = address;
        this.detailAddress = detailAddress;
        this.locationDescription = locationDescription;
        this.meetingPrice = meetingPrice;
        this.price = price;
        this.petAllowedStatus = petAllowedStatus;
        this.phone = phone;
        this.email = email;
        this.kakaoId = kakaoId;
        this.link = link;
        this.otherLink = otherLink;
        this.meetingFreeFormDetails = meetingFreeFormDetails;
        this.meetingStatus = meetingStatus;
    }
}