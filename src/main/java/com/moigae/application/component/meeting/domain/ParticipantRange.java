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