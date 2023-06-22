package com.moigae.application.component.host.domain;

import com.moigae.application.component.meeting.domain.ParticipantRange;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingPrice;
import com.moigae.application.component.meeting.domain.enumeraion.MeetingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    @DisplayName("Board_도메인_생성_테스트")
    void Board_도메인_생성_테스트() {
        //given &  when
        Board board = createBoard();

        //then
        assertThat(board.getId()).isEqualTo("BoardId");
        assertThat(board.getDate()).isNotNull();
        assertThat(board.getMeetingTitle()).isEqualTo("모임 제목");
        assertThat(board.getPrice()).isEqualTo(MeetingPrice.FREE);
        assertThat(board.getParticipantRange()).isNotNull();
        assertThat(board.getMeetingStatus()).isEqualTo(MeetingStatus.AVAILABLE);
    }

    private static Board createBoard() {
        return Board.builder()
                .id("BoardId")
                .date(LocalDateTime.of(2023, 5, 1, 1, 1))
                .meetingTitle("모임 제목")
                .price(MeetingPrice.FREE)
                .participantRange(ParticipantRange.of(1, 10))
                .meetingStatus(MeetingStatus.AVAILABLE)
                .build();
    }

}