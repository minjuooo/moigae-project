package com.moigae.application.component.meeting.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class ParticipantRangeTest {
    private static final int CURRENT_PARTICIPANTS = 0;
    private static final int MAX_PARTICIPANTS = 10;
    private ParticipantRange participantRange = new ParticipantRange();

    @Test
    @DisplayName("모임 참여-인원 객체 생성 테스트")
    void 모임_참여_인원_객체_생성_테스트() {
        //given & when
        participantRange = ParticipantRange.of(CURRENT_PARTICIPANTS, MAX_PARTICIPANTS);
        //then
        모임_참여_인원_객체_검증();
    }

    @Test
    @DisplayName("현재 참여 인원 증가 로직")
    void 현재_참여_인원_증가_로직() {
        //given
        participantRange = ParticipantRange.of(CURRENT_PARTICIPANTS, MAX_PARTICIPANTS);
        //when
        participantRange.addCurrentParticipants();
        //then
        assertThat(participantRange.getCurrentParticipants()).isEqualTo(1);
    }

    @Test
    @DisplayName("현재 참여 인원 >= 최대 참여 인원 시, 모임 신청 불가 체크")
    void meetingApplyCondition() {
        //given
        participantRange = ParticipantRange.of(MAX_PARTICIPANTS, MAX_PARTICIPANTS);

        //when & then
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(participantRange::meetingApplyCondition)
                .withMessage("참여 인원 마감으로 인한 신청 제한");
    }

    @Test
    @DisplayName("모임 참여-인원 equals 테스트")
    void 모임_참여_인원_equals_테스트() {
        //given
        ParticipantRange participantRangeOne = ParticipantRange.of(CURRENT_PARTICIPANTS, MAX_PARTICIPANTS);
        ParticipantRange participantRangeTwo = ParticipantRange.of(CURRENT_PARTICIPANTS, MAX_PARTICIPANTS);
        //when & then
        assertThat(participantRangeOne).isEqualTo(participantRangeTwo);
        assertThat(participantRangeOne.equals(participantRangeTwo)).isTrue();
    }

    @Test
    @DisplayName("모임 참여-인원 hashCode 테스트")
    void 모임_참여_인원_hashCode_테스트() {
        //given
        ParticipantRange participantRangeOne = ParticipantRange.of(CURRENT_PARTICIPANTS, MAX_PARTICIPANTS);
        ParticipantRange participantRangeTwo = ParticipantRange.of(CURRENT_PARTICIPANTS, MAX_PARTICIPANTS);
        //when & then
        assertThat(participantRangeOne.hashCode()).isEqualTo(participantRangeTwo.hashCode());
    }

    private void 모임_참여_인원_객체_검증() {
        assertThat(participantRange.getCurrentParticipants()).isEqualTo(0);
        assertThat(participantRange.getMaxParticipants()).isEqualTo(10);
    }
}