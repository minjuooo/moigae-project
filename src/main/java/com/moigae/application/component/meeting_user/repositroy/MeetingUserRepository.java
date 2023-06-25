package com.moigae.application.component.meeting_user.repositroy;

import com.moigae.application.component.meeting_user.domain.MeetingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long> {
    @Query("SELECT DISTINCT m.meetingTitle, m.meetingEndDateTime, m.meetingAmount, m.participantRange.currentParticipants, " +
            "mp.calculateAmount " +
            "FROM MeetingUser mu " +
            "JOIN mu.meeting m " +
            "JOIN MeetingPayment mp ON m = mp.meeting " +
            "WHERE m.meetingAmount > 0")
    List<Object[]> findCalculations();

    List<MeetingUser> findByHostId(String hostId);
}