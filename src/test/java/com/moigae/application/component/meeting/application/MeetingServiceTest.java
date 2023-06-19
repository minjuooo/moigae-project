package com.moigae.application.component.meeting.application;

import com.moigae.application.component.meeting.api.request.MeetingCategoryRequest;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting.repository.MeetingRepositoryCustom;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@MockMvcTest
class MeetingServiceTest {
    @Mock
    private MeetingRepositoryCustom meetingRepositoryCustom;
    @InjectMocks
    private MeetingService meetingService;

    @Test
    @DisplayName("중복 동적 쿼리, 페이징 - 모임 조회")
    public void 중복_동적_쿼리_페이징_모임_조회() {
        //given
        MeetingCategoryRequest meetingCategoryRequest = new MeetingCategoryRequest();
        Pageable pageable = PageRequest.of(0, 20);
        MeetingDto meetingDto = new MeetingDto();
        Page<MeetingDto> pageMeetingDto = new PageImpl<>(Arrays.asList(meetingDto));

        //when
        when(meetingRepositoryCustom.findMeetingsByCondition(meetingCategoryRequest, pageable)).thenReturn(pageMeetingDto);
        Page<MeetingDto> result = meetingService.Meetings(meetingCategoryRequest, pageable);

        //then
        assertThat(result.getContent().get(0)).isEqualTo(meetingDto);
        verify(meetingRepositoryCustom, times(1)).findMeetingsByCondition(meetingCategoryRequest, pageable);
    }

    @Test
    @DisplayName("미팅 아이디 조회 - UUID")
    public void 미팅_아이디_조회_UUID() {
        //given
        String meetingId = "meetingId";
        MeetingDto meetingDto = new MeetingDto();

        //when
        when(meetingRepositoryCustom.findCustomMeetingById(meetingId)).thenReturn(meetingDto);
        MeetingDto result = meetingService.meetingFindByUUID(meetingId);

        //then
        assertThat(result).isEqualTo(meetingDto);
        verify(meetingRepositoryCustom, times(1)).findCustomMeetingById(meetingId);
    }
}