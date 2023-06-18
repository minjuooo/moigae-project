package com.moigae.application.component.user.api;

import com.moigae.application.component.user.application.UserService;
import com.moigae.application.component.user.repository.UserRepository;
import com.moigae.application.core.annotation.MockMvcTest;
import com.moigae.application.core.config.PrimaryGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    PrimaryGenerator primaryGenerator;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인_폼")
    void 로그인_폼() throws Exception {
        //given & when & then
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("")
    void 회원가입_폼() throws Exception {
        //given & when & then
        mockMvc.perform(get("/users/signup"))
                .andExpect(status().isOk());
    }
}