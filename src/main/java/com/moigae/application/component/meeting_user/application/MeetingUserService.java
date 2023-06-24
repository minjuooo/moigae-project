package com.moigae.application.component.meeting_user.application;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.domain.MeetingAddress;
import com.moigae.application.component.meeting.domain.MeetingContact;
import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.moigae.application.component.meeting_user.api.request.MeetingCreateRequest;
import com.moigae.application.component.meeting_user.domain.MeetingUser;
import com.moigae.application.component.meeting_user.dto.CalculationDto;
import com.moigae.application.component.meeting_user.repositroy.MeetingUserRepository;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    //정산 조회
    @Transactional(readOnly = true)
    public List<CalculationDto> calculationDtoList() {
        List<Object[]> results = meetingUserRepository.findCalculations();
        List<CalculationDto> calculationDtos = new ArrayList<>();

        for (Object[] result : results) {
            CalculationDto dto = new CalculationDto((String) result[0],
                    (LocalDateTime) result[1], (Integer) result[2], (Integer) result[3], (Long) result[4]);
            calculationDtos.add(dto);
        }
        return calculationDtos;
    }

    public static MeetingUser createMeetingUser(User user, Meeting meeting) {
        return MeetingUser.builder()
                .user(user)
                .meeting(meeting)
                .hostId(user.getId())
                .build();
    }

    public static Meeting createMeeting(MeetingCreateRequest meetingCreateRequest) {
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
                .meetingAmount(meetingCreateRequest.getPrice())
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