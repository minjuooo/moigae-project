package com.moigae.application.component.user.application;

import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockMvcTest
class CustomMeetingServiceTest {
    @Mock
    private MeetingRepository meetingRepository;
    @InjectMocks
    private CustomMeetingService customMeetingService;

    @Test
    @DisplayName("업데이트 모임")
    public void updateMeeting() {
        //given
        Meeting meeting = new Meeting();
        when(meetingRepository.save(any(Meeting.class))).thenReturn(meeting);

        //when
        customMeetingService.updateMeeting(meeting);

        //then
        verify(meetingRepository).save(meeting);
    }
}