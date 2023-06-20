package com.moigae.application.component.question.domain;

import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.domain.enumeration.Gender;
import com.moigae.application.component.user.domain.enumeration.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTest {
    private static final String PHONE = "010";
    private static final String EMAIL = "email";
    private static final String QUESTION_TITLE = "질문 제목";
    private static final String QUESTION_CONTENT = "질문 내용";
    private static final Long VIEW_COUNT = 10L;
    private User user;

    @BeforeEach
    void setUp() {
        user = createUser();
    }

    @Test
    @DisplayName("질문 도메인 생성 테스트")
    void 질문_도메인_생성_테스트() {
        //given & when
        Question question = new Question(user, QUESTION_TITLE, QUESTION_CONTENT, VIEW_COUNT);
        //then
        질문_도메인_검증(question);
    }

    private User createUser() {
        return User.builder()
                .userName("홍정완")
                .password("패스워드")
                .gender(Gender.MAN)
                .phone(PHONE)
                .account("계좌")
                .hostIntroduction("호스트 자기소개")
                .email(EMAIL)
                .userRole(UserRole.USER)
                .flag(true)
                .deactivateAt(null)
                .build();
    }

    private static void 질문_도메인_검증(Question question) {
        assertThat(question.getQuestionTitle()).isEqualTo("질문 제목");
        assertThat(question.getUser().getEmail()).isEqualTo("email");
        assertThat(question.getQuestionContent()).isEqualTo("질문 내용");
        assertThat(question.getViewCount()).isEqualTo(VIEW_COUNT);
    }
}