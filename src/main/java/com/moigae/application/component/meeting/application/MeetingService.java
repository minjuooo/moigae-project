package com.moigae.application.component.meeting.application;

import com.moigae.application.component.meeting.api.request.MeetingCategoryRequest;
import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting.repository.MeetingRepository;
import com.moigae.application.component.meeting.repository.MeetingRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingRepositoryCustom meetingRepositoryCustom;

    @Transactional(readOnly = true)
    public Page<MeetingDto> Meetings(MeetingCategoryRequest meetingCategoryRequest, Pageable pageable) {
        Page<MeetingDto> meetingDtoPage = meetingRepositoryCustom.findMeetingsByCondition(meetingCategoryRequest, pageable);
        return meetingDtoPage;
    }

    @Transactional(readOnly = true)
    public MeetingDto meetingDto(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("미팅 아이디가 존재하지 않습니다."));
        return MeetingDto.toMeetingDto(meeting);
    }
}