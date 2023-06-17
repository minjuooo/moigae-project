package com.moigae.application.component.meeting_user.api;

import com.moigae.application.component.meeting_image.service.MeetingImageService;
import com.moigae.application.component.meeting_user.api.request.MeetingCreateRequest;
import com.moigae.application.component.meeting_user.application.MeetingUserService;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class MeetingUserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MeetingImageService meetingImageService;

    @MockBean
    MeetingUserService meetingUserService;

    @Test
    @WithMockUser
    @DisplayName("호스트 센터 - 모임 생성 폼")
    void 호스트센터_모임_생성_폼() throws Exception {
        //given & when & then
        mockMvc.perform(get("/host-center/meetings/create"))
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }

    @Test
    @WithMockUser
    @DisplayName("호스트 센터 - 모임 생성")
    void 호스트센터_모임_생성() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile("path", "filename.txt", "text/plain", "some xml".getBytes());
        MeetingCreateRequest meetingCreateRequest = new MeetingCreateRequest();

        //when & then
        mockMvc.perform(multipart("/host-center/meetings/create").file(file)
                        .flashAttr("meetingCreateRequest", meetingCreateRequest))
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }
}