package com.moigae.application.component.user.api;

import com.moigae.application.component.user.api.request.UserLoginForm;
import com.moigae.application.component.user.application.UserService;
import com.moigae.application.component.user.dto.UserDto;
import com.moigae.application.component.user.repository.UserRepository;
import com.moigae.application.core.annotation.MockMvcTest;
import com.moigae.application.core.config.PrimaryGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
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

    @Test
    @DisplayName("로그인")
    @WithMockUser
    void 로그인() throws Exception {
        UserLoginForm userLoginForm = new UserLoginForm();
        userLoginForm.setEmail("홍정완@test.com");
        userLoginForm.setPassword("홍");

        when(userService.login(userLoginForm)).thenReturn(new UserDto());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "홍정완@test.com")
                        .param("password", "홍"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("회원가입")
    @WithMockUser
    void 회원가입() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("홍정완@test.com");
        userDto.setPassword("홍");

        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/users/signup")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "홍정완@test.com")
                        .param("password", "홍"))
                .andExpect(status().is3xxRedirection());
    }
}