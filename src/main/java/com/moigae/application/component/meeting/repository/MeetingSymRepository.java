package com.moigae.application.component.meeting.repository;

import com.moigae.application.component.meeting.domain.MeetingSym;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.MeetingSymDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetingSymRepository extends JpaRepository<MeetingSym, String> {
    MeetingSym findByUserIdAndMeetingId(String userId, String meetingId);

    @Query("SELECT NEW com.moigae.application.component.user.dto.MeetingSymDto(" +
            "m.id, mi.path, m.meetingTitle, m.meetingAmount, m.meetingPrice) " +
            "FROM MeetingSym ms " +
            "JOIN ms.meeting m " +
            "JOIN m.meetingImage mi " +
            "WHERE ms.user.id = :userId " +
            "AND ms.sym = true " +
            "AND (m.meetingTitle LIKE CONCAT('%', :searchTerm, '%') " +
            "OR m.meetingDescription LIKE CONCAT('%', :searchTerm, '%'))")
    Page<MeetingSymDto> findMeetingSyms(Pageable pageable, @Param("userId") String userId, @Param("searchTerm") String searchTerm);


}
