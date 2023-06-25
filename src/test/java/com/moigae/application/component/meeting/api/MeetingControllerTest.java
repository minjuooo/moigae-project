package com.moigae.application.component.meeting.api;

import com.moigae.application.component.meeting.api.request.MeetingCategoryRequest;
import com.moigae.application.component.meeting.application.MeetingService;
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
        중복_동적_쿼리_페이징_모임_검증(customUser, meetingCategoryRequest, meetingDtoPage, model, view);
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

    @Test
    @DisplayName("유료 모임 상세 페이지")
    void 유료_모임_상세_페이지() {
        //given
        String meetingId = "meetingId";
        CustomUser customUser = new CustomUser();
        MeetingDto meetingDto = new MeetingDto();
        meetingDto.setPrice(100);
        Model model = new BindingAwareModelMap();

        //when
        when(meetingService.meetingFindByUUIDPay(meetingId)).thenReturn(meetingDto);

        //then
        verify(meetingService, never()).meetingFindByUUIDPay(anyString());
    }

    @Test
    @DisplayName("상세 모임 조회 테스트")
    void 상세_모임_조회_테스트() {
        //given
        String meetingId = "meetingId";
        CustomUser customUser = new CustomUser();
        MeetingDto meetingDto = new MeetingDto();
        meetingDto.setPrice(0);
        Model model = new BindingAwareModelMap();

        //when
        when(meetingService.meetingFindByUUIDPay(meetingId)).thenReturn(meetingDto);

        //then
        verify(meetingService, never()).meetingFindByUUIDPay(anyString());
    }

    @Test
    @DisplayName("무료 모임 신청 페이지")
    void 무료_모임_신청_페이지() {
        // given
        String meetingId = "meetingId";
        CustomUser customUser = new CustomUser();
        UserDto userDto = new UserDto();
        MeetingDto meetingDto = new MeetingDto();
        meetingDto.setPrice(0);
        Model model = new BindingAwareModelMap();

        // when
        when(userService.customUserFindBy(customUser)).thenReturn(userDto);
        when(meetingService.meetingFindByUUIDPay(meetingId)).thenReturn(meetingDto);
        String view = meetingController.applicationPayMeeting(model, customUser, meetingId);

        // then
        assertThat(view).isEqualTo("meetings/meeting_application_free");
        assertThat(model.getAttribute("meetingDto")).isEqualTo(meetingDto);
        assertThat(model.getAttribute("userDto")).isEqualTo(userDto);
        assertThat(model.getAttribute("customUser")).isEqualTo(customUser);
    }

    @Test
    @DisplayName("유료 모임 신청 - 인증된 유저")
    void 유료_모임_신청_인증된_유저() {
        //given
        String meetingId = "meetingId";
        CustomUser customUser = new CustomUser();
        UserDto userDto = new UserDto();
        MeetingDto meetingDto = new MeetingDto();
        meetingDto.setPrice(1000);
        Model model = new BindingAwareModelMap();

        //when
        when(userService.customUserFindBy(customUser)).thenReturn(userDto);
        when(meetingService.meetingFindByUUIDPay(meetingId)).thenReturn(meetingDto);
        String view = meetingController.applicationPayMeeting(model, customUser, meetingId);

        //then
        유료_모임_신청_인증된_유저_검증(customUser, userDto, meetingDto, model, view);
    }

    @Test
    @DisplayName("유료 모임 신청 - 인증되지 않은 유저일 경우")
    void 유료_모임_신청_인증되지_않은_유저() {
        //given
        String meetingId = "meetingId";

        //when
        String viewName = meetingController.applicationPayMeeting(null, null, meetingId);

        //then
        유료_모임_신청_인증되지_않은_유저(viewName);
    }

    private static void 중복_동적_쿼리_페이징_모임_검증(CustomUser customUser, MeetingCategoryRequest meetingCategoryRequest, Page<MeetingDto> meetingDtoPage, Model model, String view) {
        assertThat(view).isEqualTo("meetings/meeting_list");
        assertThat(model.getAttribute("meetingDtoPage")).isEqualTo(meetingDtoPage);
        assertThat(model.getAttribute("meetingCategoryDto")).isEqualTo(meetingCategoryRequest);
        assertThat(model.getAttribute("customUser")).isEqualTo(customUser);
    }

    private static void 유료_모임_신청_인증된_유저_검증(CustomUser customUser, UserDto userDto, MeetingDto meetingDto, Model model, String view) {
        assertThat(view).isEqualTo("meetings/meeting_application_pay");
        assertThat(model.getAttribute("meetingDto")).isEqualTo(meetingDto);
        assertThat(model.getAttribute("userDto")).isEqualTo(userDto);
        assertThat(model.getAttribute("customUser")).isEqualTo(customUser);
    }

    private void 유료_모임_신청_인증되지_않은_유저(String viewName) {
        verify(userService, never()).customUserFindBy(any(CustomUser.class));
        verify(meetingService, never()).meetingFindByUUIDPay(anyString());
        assertThat(viewName).isEqualTo("redirect:/users/login");
    }

}