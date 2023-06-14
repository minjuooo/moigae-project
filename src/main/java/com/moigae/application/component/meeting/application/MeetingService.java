package com.moigae.application.component.meeting.application;

import com.moigae.application.component.meeting.api.request.MeetingCategoryDto;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting.repository.MeetingRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepositoryCustom meetingRepositoryCustom;

    @Transactional(readOnly = true)
    public Page<MeetingDto> Meetings(MeetingCategoryDto meetingCategoryDto, Pageable pageable) {
        Page<MeetingDto> meetingDtoPage = meetingRepositoryCustom.findMeetingsByCondition(meetingCategoryDto, pageable);
        return meetingDtoPage;
    }
}