package com.moigae.application.component.article.domain;

import com.moigae.application.core.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_title")
    private String articleTitle;

    @Column(name = "content")
    private String content;

    private Article(String articleTitle, String content) {
        this.articleTitle = articleTitle;
        this.content = content;
    }

    public static Article of(String articleTitle, String content) {
        return new Article(articleTitle, content);
    }
}