//package com.moigae.application.component.meeting.repository;
//
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//@DataJpaTest
//@ActiveProfiles("prod")
//@RequiredArgsConstructor
//public class MeetingRepositoryImplTest {
//    private final MeetingRepositoryCustom meetingRepositoryCustom;
//
//    @Test
//    @Transactional
//    public void findMeetingsByConditionTest() {
//        //given
//
//        MeetingCategoryRequest meetingCategoryRequest = // 초기화
//                PageRequest pageable = PageRequest.of(0, 10);
//
//        //when
//        Page<MeetingDto> page = meetingRepositoryImpl.findMeetingsByCondition(meetingCategoryRequest, pageable);
//
//        //then
//        assertThat(page).isNotNull();
//        assertThat(page.getContent()).isNotEmpty();
//    }
//
//    @Test
//    @Transactional
//    public void findCustomMeetingByIdTest() {
//        //given
//
//        String meetingId = // 초기화
//
//                //when
//                MeetingDto result = meetingRepositoryImpl.findCustomMeetingById(meetingId);
//
//        //then
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(meetingId);
//        // 기타 필요한 검증 추가
//    }
//
//    @Test
//    @Transactional
//    public void findCustomMeetingByPayIdTest() {
//        //given
//
//        String meetingId = // 초기화
//
//                //when
//                Meeting result = meetingRepositoryImpl.findCustomMeetingByPayId(meetingId);
//
//        //then
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(meetingId);
//        // 기타 필요한 검증 추가
//    }
//}