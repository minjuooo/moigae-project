package com.moigae.application.component.user.domain.enumeration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.moigae.application.component.user.domain.enumeration.UserRole.USER;
import static org.assertj.core.api.Assertions.assertThat;

class UserRoleTest {
    @Test
    @DisplayName("유저 역할 테스트")
    void 유저_역할() {
        //given
        UserRole userRole = USER;

        //when & then
        assertThat(userRole).isEqualTo(USER);
    }
}