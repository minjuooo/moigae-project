package com.moigae.application.component.meeting_user.application;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.moigae.application.component.meeting_user.api.request.MeetingCreateRequest;
import com.moigae.application.component.meeting_user.domain.MeetingUser;
import com.moigae.application.component.meeting_user.repositroy.MeetingUserRepository;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MeetingUserServiceTest {
    @Mock
    private MeetingUserRepository meetingUserRepository;
    @Mock
    private MeetingRepository meetingRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private MeetingUserService meetingUserService;

    private CustomUser customUser;
    private User user;
    private Meeting meeting;
    private MeetingCreateRequest meetingCreateRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        customUser = new CustomUser();
        meetingCreateRequest = new MeetingCreateRequest();
        user = new User();
        meeting = new Meeting();

        customUser.setId("userId");
        customUser.setUsername("email");

        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(meetingRepository.save(any(Meeting.class))).thenReturn(meeting);
        when(meetingUserRepository.save(any(MeetingUser.class))).thenReturn(null);
    }

    @Test
    @DisplayName("호스트 센터 모임 등록 테스트")
    void 호스트_센터_모임_등록_테스트() {
        //given & when
        Meeting meeting = meetingUserService.meetingUserCreate(customUser, meetingCreateRequest);

        //then
        verify(userRepository, times(1)).findByEmail(customUser.getUsername());
        verify(meetingRepository, times(1)).save(any(Meeting.class));
        verify(meetingUserRepository, times(1)).save(any(MeetingUser.class));

        assertThat(meeting).isNotNull();
    }
}