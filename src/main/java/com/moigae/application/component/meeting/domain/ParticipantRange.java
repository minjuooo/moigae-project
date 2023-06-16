package com.moigae.application.component.meeting.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantRange {
    private Integer currentParticipants = 0;
    private Integer maxParticipants;

    public static ParticipantRange of(Integer currentParticipants, Integer maxParticipants) {
        return new ParticipantRange(currentParticipants, maxParticipants);
    }

    //현재 참여 인원 + 1
    public void addCurrentParticipants() {
        this.currentParticipants++;
    }

    //현재 참여 인원 >= 최대 참여 인원 시, 모임 신청 불가 체크
    public void meetingApplyCondition() {
        if (this.currentParticipants >= maxParticipants) {
            throw new IllegalStateException("참여 인원 마감으로 인한 신청 제한");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantRange that = (ParticipantRange) o;
        return Objects.equals(currentParticipants, that.currentParticipants) && Objects.equals(maxParticipants, that.maxParticipants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentParticipants, maxParticipants);
    }
}