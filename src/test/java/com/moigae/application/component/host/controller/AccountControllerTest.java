package com.moigae.application.component.host.controller;

import com.moigae.application.component.host.service.AccountService;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@MockMvcTest
class AccountControllerTest {
    @Mock
    private AccountService accountService;
    @InjectMocks
    private AccountController accountController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    @DisplayName("계좌 생성 - 폼")
    public void 계좌_생성_폼() throws Exception {
        //given & when & then
        mockMvc.perform(get("/accounts/create"))
                .andExpect(model().attributeExists("accountDto"))
                .andExpect(view().name("host/hostCenterMain"))
                .andDo(print());
    }

    @Test
    @DisplayName("계좌 등록")
    public void 계좌_등록() throws Exception {
        //given & when & then
        mockMvc.perform(post("/accounts/create"))
                .andExpect(view().name("host/hostCenterMain"))
                .andDo(print());
    }
}