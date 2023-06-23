package com.moigae.application.component.answer.domain;

import com.moigae.application.component.question.domain.Question;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.domain.enumeration.Gender;
import com.moigae.application.component.user.domain.enumeration.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnswerTest {
    private static final String PHONE = "010";
    private static final String EMAIL = "email";
    private static final String ANSWER_CONTENT = "답변 질문";
    private static final String QUESTION_TITLE = "질문 제목";
    private static final String QUESTION_CONTENT = "질문 내용";
    private static final Long VIEW_COUNT = 10L;
    private static final boolean SYM = true;

    private User user;
    private Question question;

    @BeforeEach
    void setUp() {
        user = createUser();
        question = new Question(user, QUESTION_TITLE, QUESTION_CONTENT, VIEW_COUNT);
    }

    @Test
    @DisplayName("답변 도메인 생성 테스트")
    void 답변_도메인_생성_테스트() {
        //given & when
        Answer answer = new Answer(question, user, ANSWER_CONTENT, SYM);
        answer.setId("answerId");
        //then
        답변_도메인_검증(answer);
    }

    @Test
    @DisplayName("답변 도메인 setters 테스트")
    void 답변_도메인_setters_테스트() {
        //given
        User newUser = createUser();
        Question newQuestion = new Question(newUser, "새 질문 제목", "새 질문 내용", 20L);
        String newAnswerContent = "새 답변 내용";
        boolean newSym = false;

        //when
        Answer answer = createSetterAnswer(newUser, newQuestion, newAnswerContent, newSym);

        //then
        답변_도메인_setters_검증(newAnswerContent, newSym, answer);
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

    private static void 답변_도메인_검증(Answer answer) {
        assertThat(answer.getId()).isEqualTo("answerId");
        assertThat(answer.getQuestion().getQuestionTitle()).isEqualTo(QUESTION_TITLE);
        assertThat(answer.getUser().getEmail()).isEqualTo(EMAIL);
        assertThat(answer.getAnswerContent()).isEqualTo(ANSWER_CONTENT);
        assertThat(answer.isSym()).isEqualTo(true);
    }

    private static void 답변_도메인_setters_검증(String newAnswerContent, boolean newSym, Answer answer) {
        assertThat(answer.getUser().getUserName()).isEqualTo("홍정완");
        assertThat(answer.getQuestion().getQuestionTitle()).isEqualTo("새 질문 제목");
        assertThat(answer.getAnswerContent()).isEqualTo(newAnswerContent);
        assertThat(answer.isSym()).isEqualTo(newSym);
    }

    private Answer createSetterAnswer(User newUser, Question newQuestion, String newAnswerContent, boolean newSym) {
        Answer answer = new Answer(question, user, ANSWER_CONTENT, SYM);
        answer.setUser(newUser);
        answer.setQuestion(newQuestion);
        answer.setAnswerContent(newAnswerContent);
        answer.setSym(newSym);
        return answer;
    }
}