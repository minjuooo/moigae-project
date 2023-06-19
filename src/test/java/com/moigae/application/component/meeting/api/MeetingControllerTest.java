package com.moigae.application.component.meeting.api;

import com.moigae.application.component.meeting.api.request.MeetingCategoryRequest;
import com.moigae.application.component.meeting.application.MeetingService;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingPrice;
import com.moigae.application.component.meeting.dto.MeetingDto;
import com.moigae.application.component.meeting_payment.application.MeetingPaymentService;
import com.moigae.application.component.user.application.UserService;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.dto.UserDto;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockMvcTest
class MeetingControllerTest {
    @Mock
    private MeetingService meetingService;
    @Mock
    private UserService userService;
    @Mock
    private MeetingPaymentService meetingPaymentService;
    @InjectMocks
    private MeetingController meetingController;

    @Test
    @DisplayName("중복 동적 쿼리, 페이징 - 모임 조회")
    void 중복_동적_쿼리_페이징_모임_조회() {
        //given
        CustomUser customUser = new CustomUser();
        MeetingCategoryRequest meetingCategoryRequest = new MeetingCategoryRequest();
        Page<MeetingDto> meetingDtoPage = new PageImpl<>(Arrays.asList(new MeetingDto(), new MeetingDto()));
        Model model = new BindingAwareModelMap();

        //when
        when(meetingService.Meetings(any(MeetingCategoryRequest.class), any(PageRequest.class))).thenReturn(meetingDtoPage);
        String view = meetingController.meetings(model, customUser, meetingCategoryRequest, PageRequest.of(0, 20));

        //then
        assertThat(view).isEqualTo("meetings/meeting_list");
        assertThat(model.getAttribute("meetingDtoPage")).isEqualTo(meetingDtoPage);
        assertThat(model.getAttribute("meetingCategoryDto")).isEqualTo(meetingCategoryRequest);
        assertThat(model.getAttribute("customUser")).isEqualTo(customUser);
    }

    @Test
    @DisplayName("모임 상세 페이지 조회 - 유료")
    void 유료_모임_상세_페이지_조회() {
        //given
        String meetingId = "meetingId";
        MeetingDto meetingDto = new MeetingDto();
        Model model = new ConcurrentModel();
        CustomUser customUser = new CustomUser();

        meetingDto.setMeetingPrice(MeetingPrice.PAY);

        //when
        when(meetingService.meetingFindByUUID(meetingId)).thenReturn(meetingDto);
        String viewName = meetingController.detailMeeting(model, customUser, meetingId);

        //then
        assertThat(viewName).isEqualTo("meetings/meeting_detail_pay");
        assertThat(model.getAttribute("meetingDto")).isEqualTo(meetingDto);
        assertThat(model.getAttribute("customUser")).isEqualTo(customUser);
    }

    @Test
    @DisplayName("모임 상세 페이지 조회 - 무료")
    void 무료_모임_상세_페이지_조회() {
        //given
        String meetingId = "meetingId";
        MeetingDto meetingDto = new MeetingDto();
        Model model = new ConcurrentModel();
        CustomUser customUser = new CustomUser();

        meetingDto.setMeetingPrice(MeetingPrice.FREE);

        //when
        when(meetingService.meetingFindByUUID(meetingId)).thenReturn(meetingDto);
        String viewName = meetingController.detailMeeting(model, customUser, meetingId);

        //then
        assertThat(viewName).isEqualTo("meetings/meeting_detail_free");
        assertThat(model.getAttribute("meetingDto")).isEqualTo(meetingDto);
        assertThat(model.getAttribute("customUser")).isEqualTo(customUser);
    }

    @Test
    @DisplayName("모임 신청 페이지 조회 - 무료")
    public void 무료_모임_신청_페이지_조회() {
        //given
        String meetingId = "meetingId";
        MeetingDto meetingDto = new MeetingDto();
        CustomUser customUser = new CustomUser();
        UserDto userDto = new UserDto();
        Model model = new ConcurrentModel();

        meetingDto.setMeetingPrice(MeetingPrice.PAY);

        //when
        when(userService.customUserFindBy(customUser)).thenReturn(userDto);
        when(meetingService.meetingFindByUUID(meetingId)).thenReturn(meetingDto);
        String viewName = meetingController.applicationPayMeeting(model, customUser, meetingId);

        //then
        assertThat(viewName).isEqualTo("meetings/meeting_application_pay");
        assertThat(model.getAttribute("meetingDto")).isEqualTo(meetingDto);
        assertThat(model.getAttribute("userDto")).isEqualTo(userDto);
        assertThat(model.getAttribute("customUser")).isEqualTo(customUser);
    }

    @Test
    @DisplayName("모임 신청 페이지 조회 - 유료")
    public void 유료_모임_신청_페이지_조회() {
        //given
        String meetingId = "meetingId";
        MeetingDto meetingDto = new MeetingDto();
        CustomUser customUser = new CustomUser();
        UserDto userDto = new UserDto();
        Model model = new ConcurrentModel();

        meetingDto.setMeetingPrice(MeetingPrice.FREE);

        //when
        when(userService.customUserFindBy(customUser)).thenReturn(userDto);
        when(meetingService.meetingFindByUUID(meetingId)).thenReturn(meetingDto);
        String viewName = meetingController.applicationPayMeeting(model, customUser, meetingId);

        //then
        assertThat(viewName).isEqualTo("meetings/meeting_application_free");
        assertThat(model.getAttribute("meetingDto")).isEqualTo(meetingDto);
        assertThat(model.getAttribute("userDto")).isEqualTo(userDto);
        assertThat(model.getAttribute("customUser")).isEqualTo(customUser);
    }

    @Test
    @DisplayName("무료 모임 신청 - 인증된 유저")
    public void 무료_모임_신청_인증() {
        //given
        String meetingId = "{meetingId}";
        CustomUser customUser = new CustomUser();

        //when
        String viewName = meetingController.applicationFreeMeeting(null, customUser, meetingId);

        //then
        verify(meetingPaymentService).meetingFreeCreate(meetingId, customUser, meetingId);
        assertThat(viewName).isEqualTo("redirect:/meetings/" + meetingId);
    }

    @Test
    @DisplayName("무료 모임 신청 - 인증되지 않은 유저일 경우")
    public void 무료_모임_신청_인증전() {
        //given
        String meetingId = "meetingId";

        //when
        String viewName = meetingController.applicationFreeMeeting(null, null, meetingId);

        //then
        verify(meetingPaymentService, never()).meetingFreeCreate(anyString(), any(CustomUser.class), anyString());
        assertThat(viewName).isEqualTo("redirect:/users/login");
    }
}