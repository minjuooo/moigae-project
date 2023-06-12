package com.moigae.application.component.meeting_image.repository;

import com.moigae.application.component.meeting_image.domain.MeetingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingImageRepository extends JpaRepository<MeetingImage, Long> {
}