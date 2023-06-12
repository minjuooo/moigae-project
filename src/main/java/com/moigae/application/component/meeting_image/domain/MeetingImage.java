package com.moigae.application.component.meeting_image.domain;

import com.moigae.application.core.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String s3Key;

    @Column(nullable = false)
    private String path;

    @Builder
    public MeetingImage(String originName, String name, String s3Key, String path) {
        this.originName = originName;
        this.name = name;
        this.s3Key = s3Key;
        this.path = path;
    }

    public void update(String originName, String name, String s3Key, String path) {
        this.originName = originName;
        this.name = name;
        this.s3Key = s3Key;
        this.path = path;
    }
}