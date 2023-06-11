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
    private Integer minParticipants;
    private Integer maxParticipants;

    public static ParticipantRange of(Integer minParticipants, Integer maxParticipants) {
        return new ParticipantRange(minParticipants, maxParticipants);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantRange that = (ParticipantRange) o;
        return Objects.equals(minParticipants, that.minParticipants) && Objects.equals(maxParticipants, that.maxParticipants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minParticipants, maxParticipants);
    }
}