package com.moigae.application.component.meeting.repository;

import com.moigae.application.component.meeting.api.request.MeetingCategoryDto;
import com.moigae.application.component.meeting.dto.MeetingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MeetingRepositoryCustom {
    Page<MeetingDto> findMeetingsByCondition(String sort, MeetingCategoryDto meetingCategoryDto,
                                             String searchTitle, Pageable pageable);
}