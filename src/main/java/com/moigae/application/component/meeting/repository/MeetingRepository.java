package com.moigae.application.component.meeting.repository;

import com.moigae.application.component.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, String>, QuerydslPredicateExecutor<Meeting> {
}