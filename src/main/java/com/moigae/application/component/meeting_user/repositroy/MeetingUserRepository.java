package com.moigae.application.component.meeting_user.repositroy;

import com.moigae.application.component.meeting_user.domain.MeetingUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long> {
}