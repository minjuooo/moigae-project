package com.moigae.application.component.host.controller;

import com.moigae.application.component.host.dto.AccountDto;
import com.moigae.application.component.host.service.AccountService;
import com.moigae.application.component.host.service.BoardService;
import com.moigae.application.component.meeting_user.repositroy.MeetingUserRepository;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@MockMvcTest
class BoardControllerTest {
    @Mock
    private BoardService boardService;
    @Mock
    private AccountService accountService;
    @Mock
    private MeetingUserRepository meetingUserRepository;
    @InjectMocks
    private BoardController boardController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    @DisplayName("계좌 저장 - 컨트롤러")
    public void testPostBoard() throws Exception {
        //given
        CustomUser customUser = new CustomUser();
        customUser.setUsername("username");
        customUser.setPassword("password");
        customUser.setId("id");
        customUser.setName("name");

        //when & then
        when(accountService.iscreateAccountExists(anyString())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/host-center/board")
                        .with(user(customUser))
                        .flashAttr("accountDto", new AccountDto()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/host-center/board"))
                .andDo(print());
    }
}