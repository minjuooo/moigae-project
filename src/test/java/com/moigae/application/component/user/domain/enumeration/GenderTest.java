package com.moigae.application.component.user.domain.enumeration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GenderTest {
    @Test
    @DisplayName("유저 성별 테스트")
    void 유저_성별() {
        //given
        Gender gender = Gender.MAN;

        //when & then
        assertThat(gender.getValue()).isEqualTo("남자");
    }
}