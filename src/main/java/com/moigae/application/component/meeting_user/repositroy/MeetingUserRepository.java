package com.moigae.application.component.meeting_user.repositroy;

import com.moigae.application.component.meeting_user.domain.MeetingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long> {
    List<MeetingUser> findByHostId(String hostId);
}