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
public class MeetingAddress {
    private String address;
    private String detailAddress;
    private String locationDescription;

    public static MeetingAddress of(String address, String detailAddress, String locationDescription) {
        return new MeetingAddress(address, detailAddress, locationDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingAddress that = (MeetingAddress) o;
        return Objects.equals(address, that.address) && Objects.equals(detailAddress, that.detailAddress) && Objects.equals(locationDescription, that.locationDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, detailAddress, locationDescription);
    }
}