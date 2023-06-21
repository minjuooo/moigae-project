package com.moigae.application.component.user.application;

import com.moigae.application.component.user.api.request.UserLoginForm;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.dto.UserDto;
import com.moigae.application.component.user.repository.UserRepository;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@MockMvcTest
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private User testUser;
    private CustomUser testCustomUser;
    private UserLoginForm userLoginForm;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testCustomUser = new CustomUser();
        userLoginForm = new UserLoginForm();

        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser.setId("testUser");
        testUser.setUserName("testUser");

        testCustomUser.setUsername(testUser.getEmail());
        testCustomUser.setPassword(testUser.getPassword());
        testCustomUser.setId(testUser.getId());
        testCustomUser.setName(testUser.getUserName());

        userLoginForm.setEmail(testUser.getEmail());
        userLoginForm.setPassword(testUser.getPassword());
    }

    @Test
    @DisplayName("유저 이메일 찾기 성공 테스트")
    public void loadUserByUsername() {
        //when
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(testUser);
        UserDetails userDetails = userService.loadUserByUsername(testUser.getEmail());

        //then
        assertThat(userDetails.getUsername()).isEqualTo(testUser.getEmail());
    }

    @Test
    @DisplayName("유저 이메일 찾기 실패 테스트")
    public void loadUserByUsernameNotFound() {
        //when
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(null);

        //then
        assertThatThrownBy(() -> userService.loadUserByUsername(testUser.getEmail()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 email 입니다.");
    }

    @Test
    @DisplayName("커스텀 유저 이름 찾기 성공 테스트")
    public void customUserFindBy() {
        //when
        when(userRepository.findByUserName(testCustomUser.getName())).thenReturn(testUser);
        UserDto userDto = userService.customUserFindBy(testCustomUser);

        //then
        assertThat(userDto.getUserName()).isEqualTo(testUser.getUserName());
    }
}