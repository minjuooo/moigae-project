package com.moigae.application.component.meeting_user.application;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.MeetingAddress;
import com.moigae.application.component.meeting.domain.MeetingContact;
import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingPrice;
import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.moigae.application.component.meeting_user.api.request.MeetingCreateRequest;
import com.moigae.application.component.meeting_user.domain.MeetingUser;
import com.moigae.application.component.meeting_user.repositroy.MeetingUserRepository;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingUserService {
    private final MeetingUserRepository meetingUserRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Transactional
    public Meeting meetingUserCreate(CustomUser customUser, MeetingCreateRequest meetingCreateRequest) {
        User user = userRepository.findByEmail(customUser.getUsername());
        Meeting meeting = createMeeting(meetingCreateRequest);

        meetingRepository.save(meeting);
        meetingUserRepository.save(createMeetingUser(user, meeting));
        return meeting;
    }

    public static MeetingUser createMeetingUser(User user, Meeting meeting) {
        return MeetingUser.builder()
                .user(user)
                .meeting(meeting)
                .hostId(user.getId())
                .build();
    }

    public static Meeting createMeeting(MeetingCreateRequest meetingCreateRequest) {
        if (meetingCreateRequest.getMeetingPrice() == MeetingPrice.PAY) {
            meetingCreateRequest.getMeetingPrice().setPriceForPay(meetingCreateRequest.getPriceInput());
        }

        return Meeting.builder()
                .meetingTitle(meetingCreateRequest.getMeetingTitle())
                .meetingType(meetingCreateRequest.getMeetingType())
                .meetingCategory(meetingCreateRequest.getMeetingCategory())
                .nickName(meetingCreateRequest.getNickName())
                .meetingDescription(meetingCreateRequest.getMeetingDescription())
                .recruitmentStartDateTime(meetingCreateRequest.getRecruitmentStartDateTime())
                .recruitmentEndDateTime(meetingCreateRequest.getRecruitmentEndDateTime())
                .participantRange(createParticipantRange(meetingCreateRequest))
                .meetingStartDateTime(meetingCreateRequest.getMeetingStartDateTime())
                .meetingEndDateTime(meetingCreateRequest.getMeetingEndDateTime())
                .meetingAddress(createMeetingAddress(meetingCreateRequest))
                .meetingPrice(meetingCreateRequest.getMeetingPrice())
                .petAllowedStatus(meetingCreateRequest.getPetAllowedStatus())
                .meetingContact(createMeetingContact(meetingCreateRequest))
                .meetingFreeFormDetails(meetingCreateRequest.getMeetingFreeFormDetails())
                .meetingStatus(meetingCreateRequest.getMeetingStatus())
                .build();
    }

    public static ParticipantRange createParticipantRange(MeetingCreateRequest meetingCreateRequest) {
        return ParticipantRange.of(
                meetingCreateRequest.getCurrentParticipants(),
                meetingCreateRequest.getMaxParticipants()
        );
    }

    public static MeetingAddress createMeetingAddress(MeetingCreateRequest meetingCreateRequest) {
        return MeetingAddress.of(
                meetingCreateRequest.getAddress(),
                meetingCreateRequest.getDetailAddress(),
                meetingCreateRequest.getLocationDescription()
        );
    }

    public static MeetingContact createMeetingContact(MeetingCreateRequest meetingCreateRequest) {
        return MeetingContact.builder()
                .phone(meetingCreateRequest.getPhone())
                .email(meetingCreateRequest.getEmail())
                .kakaoId(meetingCreateRequest.getKakaoId())
                .link(meetingCreateRequest.getLink())
                .otherLink(meetingCreateRequest.getOtherLink())
                .build();
    }
}