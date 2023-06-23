package com.moigae.application.component.user.api;

import com.moigae.application.component.user.api.request.UserLoginForm;
import com.moigae.application.component.user.application.UserService;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.UserDto;
import com.moigae.application.component.user.repository.UserRepository;
import com.moigae.application.core.annotation.MockMvcTest;
import com.moigae.application.core.config.PrimaryGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class UserControllerTest {
    @Mock
    UserService userService;
    @Mock
    PrimaryGenerator primaryGenerator;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("로그인_폼 - 컨트롤러")
    void 로그인_폼_컨트롤러_테스트() throws Exception {
        //given & when & then
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입_폼 - 컨트롤러")
    void 회원가입_폼_컨트롤러_테스트() throws Exception {
        //given & when & then
        mockMvc.perform(get("/users/signup"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 - 컨트롤러")
    @WithMockUser
    void 로그인_컨트롤러_테스트() throws Exception {
        //given
        UserLoginForm userLoginForm = new UserLoginForm();
        userLoginForm.setEmail("홍정완@test.com");
        userLoginForm.setPassword("홍");

        //when & then
        when(userService.login(userLoginForm)).thenReturn(new UserDto());

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "홍정완@test.com")
                        .param("password", "홍"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("회원가입 테스트 - 컨트롤러")
    @WithMockUser
    void 회원가입_컨트롤러_테스트() throws Exception {
        //given
        User user = new User();
        user.setEmail("test@example.com");
        user.setUserName("testUser");
        user.setPhone("1234567890");
        user.setPassword("testPassword");

        //when & then
        when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "test@example.com")
                        .param("password", "testPassword")
                        .param("name", "testUser")
                        .param("gender", "man")
                        .param("phone", "1234567890"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 찾기 테스트 - 컨트롤러")
    @WithMockUser
    void 비밀번호_찾기_테스트_컨트롤러_테스트() throws Exception {
        //given
        User user = new User();
        user.setEmail("test@example.com");
        user.setUserName("testUser");
        user.setPhone("1234567890");
        user.setPassword("testPassword");

        //when & then
        when(userRepository.findByUserNameAndPhone(anyString(), anyString())).thenReturn(user);

        mockMvc.perform(post("/users/findId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"testUser\", \"phone\":\"1234567890\"}"))
                .andDo(print());
    }
}