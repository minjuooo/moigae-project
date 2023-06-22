package com.moigae.application.component.qna.domain;

import com.moigae.application.component.question.domain.Question;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.domain.enumeration.Gender;
import com.moigae.application.component.user.domain.enumeration.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SymTest {
    private static final String PHONE = "010";
    private static final String EMAIL = "email";
    private static final String QUESTION_TITLE = "질문 제목";
    private static final String QUESTION_CONTENT = "질문 내용";
    private static final Long VIEW_COUNT = 10L;

    private User user;
    private Question question;

    @BeforeEach
    void setUp() {
        user = createUser();
        question = new Question(user, QUESTION_TITLE, QUESTION_CONTENT, VIEW_COUNT);
    }

    @Test
    @DisplayName("공감 도메인 생성 테스트")
    void 공감_도메인_생성_테스트() {
        //given & when
        Sym sym = new Sym(question, user, true);
        //then
        공감_도메인_검증(sym);
    }

    @Test
    @DisplayName("공감 도메인 setter 테스트")
    void 공감_도메인_setter_테스트() {
        // given
        Sym sym = new Sym(question, user, true);

        // when
        User newUser = createUser();
        Question newQuestion = new Question(newUser, "새로운 질문 제목", "새로운 질문 내용", VIEW_COUNT);
        sym.setId(1L);
        sym.setUser(newUser);
        sym.setQuestion(newQuestion);
        sym.setSym(false);

        // then
        공감_도메인_setter_검증(sym, newQuestion);
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

    private void 공감_도메인_검증(Sym sym) {
        assertThat(sym.getQuestion()).isEqualTo(question);
        assertThat(sym.getUser().getUserName()).isEqualTo("홍정완");
        assertThat(sym.isSym()).isTrue();
    }

    private static void 공감_도메인_setter_검증(Sym sym, Question newQuestion) {
        assertThat(sym.getId()).isEqualTo(1L);
        assertThat(sym.getQuestion()).isEqualTo(newQuestion);
        assertThat(sym.getUser().getUserName()).isEqualTo("홍정완");
        assertThat(sym.isSym()).isFalse();
    }
}