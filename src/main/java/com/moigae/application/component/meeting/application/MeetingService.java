package com.moigae.application.component.meeting.application;

import com.moigae.application.component.meeting.api.request.MeetingCategoryRequest;
import com.moigae.application.component.meeting.domain.Meeting;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting.repository.MeetingRepositoryCustom;
import com.moigae.application.component.meeting_payment.repository.MeetingPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepositoryCustom meetingRepositoryCustom;
    private final MeetingPaymentRepository meetingPaymentRepository;

    @Transactional(readOnly = true)
    public Page<MeetingDto> Meetings(MeetingCategoryRequest meetingCategoryRequest, Pageable pageable) {
        Page<MeetingDto> meetingDtoPage = meetingRepositoryCustom.findMeetingsByCondition(meetingCategoryRequest, pageable);
        return meetingDtoPage;
    }

    @Transactional(readOnly = true)
    public MeetingDto meetingFindByUUID(String meetingId) {
        MeetingDto meetingDto = meetingRepositoryCustom.findCustomMeetingById(meetingId);
        return meetingDto;
    }

    @Transactional(readOnly = true)
    public MeetingDto meetingFindByUUIDPay(String meetingId) {
        Meeting meeting = meetingRepositoryCustom.findCustomMeetingByPayId(meetingId);
        if (meeting.getMeetingAmount() == null || meeting.getMeetingAmount() == 0) {
            MeetingDto meetingDto = MeetingDto.toMeetingDto(meeting);
            meetingDto.setPrice(0);
            return meetingDto;
        }
        MeetingDto meetingDto = MeetingDto.toMeetingDto(meeting);
        return meetingDto;
    }

}